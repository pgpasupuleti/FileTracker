package com.steve.tracker.common;

import org.springframework.stereotype.Service;

/**
 * @author Praveen Kumar
 *
 */
@Service("trackerConfig")
public class TrackerConfig {
	private String trackerFilePath;
	private String jsonLogFilePath;
	/**
	 * @return the trackerFilePath
	 */
	public String getTrackerFilePath() {
		return trackerFilePath;
	}
	/**
	 * @param trackerFilePath the trackerFilePath to set
	 */
	public void setTrackerFilePath(String trackerFilePath) {
		this.trackerFilePath = trackerFilePath;
	}
	/**
	 * @return the jsonLogFilePath
	 */
	public String getJsonLogFilePath() {
		return jsonLogFilePath;
	}
	/**
	 * @param jsonLogFilePath the jsonLogFilePath to set
	 */
	public void setJsonLogFilePath(String jsonLogFilePath) {
		this.jsonLogFilePath = jsonLogFilePath;
	}
}
