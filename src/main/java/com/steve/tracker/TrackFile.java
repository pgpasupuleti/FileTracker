package com.steve.tracker;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.steve.tracker.beans.Students;
import com.steve.tracker.common.TrackerConfig;
import com.steve.tracker.common.TrackerNoticeCodeBase;
import com.steve.tracker.eventhandlers.BaseEventHandler;
import com.steve.tracker.eventhandlers.UpdateEventHandler;
import com.steve.tracker.exception.TrackerException;
import com.steve.tracker.utils.TrackerUtility;

/**
 * @author Praveen Kumar
 *
 */
public class TrackFile {
	private Logger log = Logger.getLogger(TrackFile.class);
	
	private WatchService watchService;
	private WatchKey watchKey;
	private Path watchFilePath;
	private Path logJsonFilePath;
	private Students orgStudentsData;
	private long lastModified = 0;
	
	private TrackFileData trackFileData;
	
	public TrackFile(Path watchDirPath, Path watchFilePath, Path logJsonFilePath) throws TrackerException{
		try {
			watchService = FileSystems.getDefault().newWatchService();
			watchDirPath.register(watchService,  ENTRY_CREATE , ENTRY_DELETE, ENTRY_MODIFY);
			this.watchFilePath = watchFilePath;
			this.logJsonFilePath = logJsonFilePath;
			loadData();
		} catch (TrackerException e) {
			log.error(TrackerNoticeCodeBase.INITIALIZATION_FAILED.toString());
		} catch (IOException e) {
			throw new TrackerException(TrackerNoticeCodeBase.INITIALIZATION_FAILED, e ,e.getMessage());
		} 
	}
	
	/**
	 * This method loads the trackfile data.
	 * 
	 * @throws TrackerException
	 */
	private void loadData() throws TrackerException{
		try {
			trackFileData = TrackFileData.getInstance();
			orgStudentsData = TrackerUtility.generateObjectFromXML(watchFilePath);
			if (orgStudentsData != null) {
				trackFileData.loadStudentsData(orgStudentsData.getStudentsList());
			} else {
				throw new TrackerException(TrackerNoticeCodeBase.TRACK_FILE_READING_FAILED);
			}
		} catch (TrackerException e) {
			throw e;
		}
	}
	
	/**
	 * This method actually track the file and call the respective event handler based on the trackfile changes.
	 * @throws TrackerException
	 */
	private void track() throws TrackerException{
		boolean continueWatch = true;
		BaseEventHandler handler = null;
		log.debug("Tracking Initiated....");
		while(continueWatch){
			try {
				watchKey = watchService.poll(60, TimeUnit.SECONDS);
				if (watchKey != null) {
					for (WatchEvent<?> watchEvent : watchKey.pollEvents()) {
						WatchEvent<Path> wEvent = cast(watchEvent);
						Path name = wEvent.context();
						Path child = ((Path) watchKey.watchable()).resolve(name);
						if (watchEvent.kind() == ENTRY_CREATE) {
							//Create Event
							log.debug("Create Event Triggered...");
						} else if (watchEvent.kind() == ENTRY_MODIFY) {
							// modify event
							// WatchService notifies for the file meta data change as well. 
							// Added following if block to avoid these events. 
							if(child.toFile().lastModified() - lastModified > 1000){
								if(child.equals(watchFilePath)){ // We are interested in XML file only
									handler = new UpdateEventHandler();
									handler.handle(child, logJsonFilePath, child.toFile().lastModified());
								}
								lastModified  = child.toFile().lastModified();
							}
						} else if (watchEvent.kind() == ENTRY_DELETE) {
							// Delete Event
							log.debug("Delete Event Triggered...");
						}
						watchKey.reset();
					}
				}
			} catch (InterruptedException e) {
				continueWatch = false;
				throw new TrackerException(TrackerNoticeCodeBase.WATCHER_INTRUPPTED,e, e.getMessage());
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private static <T> WatchEvent<T> cast(WatchEvent<?> event) {
		return (WatchEvent<T>) event;
	}
	
	//------------MAIN-----------
	public static void main(String[] args) {
		try {
			String[] path = {"classpath*:META-INF/spring/applicationContext.xml"};
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext(path);
			TrackerConfig trackerConfig = (TrackerConfig)applicationContext.getBean(TrackerConfig.class);
			System.out.println("Tracker File Path:" + trackerConfig.getTrackerFilePath());
			System.out.println("Json File Path:" + trackerConfig.getJsonLogFilePath());
			if(StringUtils.isNotEmpty(trackerConfig.getTrackerFilePath())){
				Path trackerFilePath = Paths.get(trackerConfig.getTrackerFilePath());
				Path logJsonFilePath = Paths.get(trackerConfig.getJsonLogFilePath());
				Path parentDirPath = trackerFilePath.getParent();
				TrackFile trackFile = new TrackFile(parentDirPath,trackerFilePath,logJsonFilePath);
				trackFile.track();
			} else {
				throw new TrackerException(TrackerNoticeCodeBase.INVALID_CONFIGURATION);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
