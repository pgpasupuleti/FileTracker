package com.steve.tracker.eventhandlers;

import java.nio.file.Path;

import com.steve.tracker.exception.TrackerException;

/**
 * @author Praveen Kumar
 *
 */
public abstract class BaseEventHandler {
	protected String watchDirectory = null;
	
	public abstract void handle(Path filePath, Path logFilePath, long lastModified) throws TrackerException;

	/**
	 * @return the watchDirectory
	 */
	public String getWatchDirectory() {
		return watchDirectory;
	}

	/**
	 * @param watchDirectory the watchDirectory to set
	 */
	public void setWatchDirectory(String watchDirectory) {
		this.watchDirectory = watchDirectory;
	}
}