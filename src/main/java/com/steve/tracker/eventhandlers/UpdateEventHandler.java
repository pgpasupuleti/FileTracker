package com.steve.tracker.eventhandlers;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import com.steve.tracker.TrackFileData;
import com.steve.tracker.beans.Student;
import com.steve.tracker.beans.Students;
import com.steve.tracker.exception.TrackerException;
import com.steve.tracker.utils.JsonUtil;
import com.steve.tracker.utils.TrackerUtility;

/**
 * @author Praveen Kumar
 *
 */
public class UpdateEventHandler extends BaseEventHandler {

	private TrackFileData trackFileData;
	
	/* (non-Javadoc)
	 * @see com.steve.tracker.eventhandlers.BaseEventHandler#handle(java.nio.file.Path, java.nio.file.Path)
	 */
	@Override
	public void handle(Path filePath,Path logFilePath,long lastModified) throws TrackerException {
		try {
			Students currentStudents = TrackerUtility.generateObjectFromXML(filePath);
			if (currentStudents != null) {
				List<Student> currentStudentsList = currentStudents.getStudentsList();
				if (currentStudentsList != null && currentStudentsList.size() != 0) {
					trackFileData = TrackFileData.getInstance();
					Map<String, Students> updateData = trackFileData.getUpdates(currentStudentsList);
					if (updateData != null && updateData.size() != 0) {
						JsonObjectBuilder logJsonData = trackFileData.getJsonData(updateData, lastModified);
						System.out.println(logJsonData.build());
						JsonArrayBuilder logArrayDataBuilder = null;
						File logJsonFile = logFilePath.toFile();
						if(logJsonFile.exists()){
							JsonArray logArrayData = JsonUtil.readFileArray(logFilePath);
							logArrayDataBuilder = JsonUtil.createArrayBuilder(logArrayData);
							logArrayDataBuilder.add(logJsonData.build());
						} else {
							logArrayDataBuilder = Json.createArrayBuilder();
							logArrayDataBuilder.add(logJsonData.build());
						}
						JsonUtil.writeFile(logFilePath, logArrayDataBuilder.build());
					}
				}
			}
		} catch (TrackerException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
