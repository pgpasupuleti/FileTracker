package com.steve.tracker.exception;

import com.steve.tracker.common.TrackerNoticeCodeBase;

/**
 * @author Praveen Kumar
 *
 */
public class TrackerException extends Exception {
	
	private static final long serialVersionUID = 6947826152250727131L;

	protected TrackerNoticeCodeBase trackerNoticeCodeBase;
	
	protected String[] msgVars = null;
	
	public TrackerException(TrackerNoticeCodeBase trackerNoticeCodeBase){
		super(trackerNoticeCodeBase.toString());
		this.trackerNoticeCodeBase = trackerNoticeCodeBase;
	}
	
	public TrackerException(TrackerNoticeCodeBase trackerNoticeCodeBase, Throwable t){
		super(trackerNoticeCodeBase.toString(), t);
		this.trackerNoticeCodeBase = trackerNoticeCodeBase;
	}
	
	public TrackerException(TrackerNoticeCodeBase trackerNoticeCodeBase, String... msgVars){
		super(trackerNoticeCodeBase.toString((Object[]) msgVars));
		this.trackerNoticeCodeBase = trackerNoticeCodeBase;
		this.msgVars = msgVars;
	}
	
	public TrackerException(TrackerNoticeCodeBase trackerNoticeCodeBase, Throwable t, String... msgVars){
		super(trackerNoticeCodeBase.toString((Object[]) msgVars),t);
		this.trackerNoticeCodeBase = trackerNoticeCodeBase;
		this.msgVars = msgVars;
	}
	
	public String getErrorCode(){
		return trackerNoticeCodeBase.getCode();
	}
}
