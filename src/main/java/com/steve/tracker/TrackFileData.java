package com.steve.tracker;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;

import org.springframework.stereotype.Component;

import com.steve.tracker.beans.Student;
import com.steve.tracker.beans.Students;
import com.steve.tracker.exception.TrackerException;
import com.steve.tracker.utils.JsonUtil;

/**
 * @author Praveen Kumar
 *
 */
@Component
public class TrackFileData {
	
	private static TrackFileData trackFileData;
	
	private static Map<String, Student> studentMap = new ConcurrentHashMap<String, Student>();
	
	private TrackFileData(){
		
	}
	public static TrackFileData getInstance(){
		if(trackFileData == null){
			synchronized (TrackFileData.class) {
				trackFileData = new TrackFileData();
			}
		}
		return trackFileData;
	}
	
	/**
	 * This method process the latest data and identifies the changes in the original data and updates it.
	 * @param students
	 * @return
	 */
	public Map<String, Students> getUpdates(List<Student> students){
		Map<String, Students> latestChanges = new HashMap<String, Students>();
		List<Student> newList = new ArrayList<Student>();
		List<Student> updateList = new ArrayList<Student>();
		List<Student> deletedList = new ArrayList<Student>();
		Students newStudents = new Students();
		Students updateStudents = new Students();
		Students deletedStudents = new Students();
		
		for (Student student : students) {
			if (studentMap.get(student.getStdName()) != null) {
				Student existStudent = studentMap.get(student.getStdName());
				if(!student.equals(existStudent)){
					//student updated. Update internal Map and track the list.
					studentMap.put(student.getStdName(),student);
					updateList.add(student);
				}
			} else {
				// new Student added.
				studentMap.put(student.getStdName(),student);
				newList.add(student);
			}
		}
		
		for(Map.Entry<String, Student> entry: studentMap.entrySet()){
			if(!students.contains(entry.getValue())){
				deletedList.add(entry.getValue());
				studentMap.remove(entry.getKey());
			}
		}
		newStudents.setStudentsList(newList);
		updateStudents.setStudentsList(updateList);
		deletedStudents.setStudentsList(deletedList);
		
		latestChanges.put("add", newStudents);
		latestChanges.put("update", updateStudents);
		latestChanges.put("delete", deletedStudents);
		
		// update studentMap with latest changes.
		return latestChanges;
	}
	
	/**
	 * It loads the students data
	 * @param students
	 * @throws TrackerException
	 */
	public void loadStudentsData(List<Student> students) throws TrackerException{
		for (Student student : students) {
			studentMap.put(student.getStdName(), student);
		}
	}
	
	/**
	 * Return the log data in json format
	 * @param updateData
	 * @param lastModified
	 * @return
	 * @throws TrackerException
	 */
	public JsonObjectBuilder getJsonData(Map<String, Students> updateData, long lastModified) throws TrackerException {
		JsonObjectBuilder logEntryJson = Json.createObjectBuilder();
		Date modified = new Date(lastModified);
		JsonUtil.putSafe(logEntryJson, "trackTime", modified);
		JsonObjectBuilder trackDataJson = Json.createObjectBuilder();
		
		for(Map.Entry<String, Students> entry: updateData.entrySet()){
			JsonUtil.putSafe(trackDataJson, entry.getKey(), (JsonArrayBuilder)entry.getValue().toJsonArray());
		}
		JsonUtil.putSafe(logEntryJson, "trackData", trackDataJson);
		return logEntryJson;
	}
}
