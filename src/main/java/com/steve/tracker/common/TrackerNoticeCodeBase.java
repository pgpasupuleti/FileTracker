package com.steve.tracker.common;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.lang.StringUtils;

/**
 * @author Praveen Kumar
 *
 */
public class TrackerNoticeCodeBase {

	private String code;

	private String description;

	public TrackerNoticeCodeBase(String code, String description) {
		this.code = code;
		if (StringUtils.isNotEmpty(getResourceBundleName())) {
			try {
				this.description = ResourceBundle.getBundle(getResourceBundleName()).getString(code);
				return;
			} catch (MissingResourceException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.description = description;
	}

	public TrackerNoticeCodeBase() {

	}

	// ----------Notice Codes ----------
	public static final TrackerNoticeCodeBase INVALID_CONFIGURATION = new TrackerNoticeCodeBase("TK1001", "Invalid Configuration.");
	public static final TrackerNoticeCodeBase FILE_NOT_FOUND = new TrackerNoticeCodeBase("TK1002", "File ''{0}'' Not Found.");
	public static final TrackerNoticeCodeBase WATCHER_INTRUPPTED = new TrackerNoticeCodeBase("TK1003", "Watcher Service Interrupted.");
	public static final TrackerNoticeCodeBase INITIALIZATION_FAILED = new TrackerNoticeCodeBase("TK1004", "Tracker Initialization Failed. Reason:{0}");
	public static final TrackerNoticeCodeBase TRACK_FILE_READING_FAILED = new TrackerNoticeCodeBase("TK1005", "Problem with Tracker File Reading.");
	

	// ----------Getters------
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the description
	 */
	public String getDescription(Object... args) {

		if (args == null) {
			return getDescription();
		}
		return MessageFormat.format(getDescription(), args);
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	public String toString() {
		return code + " : " + description;
	}

	public String toString(Object... args) {
		if (args == null) {
			return toString();
		}
		return code + ": " + MessageFormat.format(getDescription(), args);
	}

	// -------------Get Properties Bundle------------
	public String getResourceBundleName() {
		return "properties.messages";
	}
}
