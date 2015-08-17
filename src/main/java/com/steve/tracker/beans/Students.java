package com.steve.tracker.beans;

import java.util.List;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.steve.tracker.exception.TrackerException;

/**
 * @author Praveen Kumar
 *
 */
@XmlRootElement(name = "students")
public class Students {

	private List<Student> studentsList;

	/**
	 * @return the studentsList
	 */
	public List<Student> getStudentsList() {
		return studentsList;
	}

	/**
	 * @param studentsList the studentsList to set
	 */
	@XmlElement( name = "student" )
	public void setStudentsList(List<Student> studentsList) {
		this.studentsList = studentsList;
	}
	
	public Object toJsonArray() throws TrackerException {
		JsonArrayBuilder studentsArray = Json.createArrayBuilder();
		for (Student student : studentsList) {
			studentsArray.add((JsonObjectBuilder)student.toJson());
		}
		return studentsArray;
	}
}
