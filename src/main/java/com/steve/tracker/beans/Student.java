package com.steve.tracker.beans;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.steve.tracker.exception.TrackerException;
import com.steve.tracker.utils.JsonUtil;

/**
 * @author Praveen Kumar
 *
 */
@XmlRootElement( name = "student" )
public class Student {
	
	private String stdName;
	private String stdEducation;
	private String stdMajor;
	private String stdGrade;

	/**
	 * @return the stdName
	 */
	public String getStdName() {
		return stdName;
	}

	/**
	 * @param stdName the stdName to set
	 */
	@XmlElement(name = "name")
	public void setStdName(String stdName) {
		this.stdName = stdName;
	}

	/**
	 * @return the stdEducation
	 */
	public String getStdEducation() {
		return stdEducation;
	}

	/**
	 * @param stdEducation the stdEducation to set
	 */
	@XmlElement(name = "education")
	public void setStdEducation(String stdEducation) {
		this.stdEducation = stdEducation;
	}

	/**
	 * @return the stdMajor
	 */
	public String getStdMajor() {
		return stdMajor;
	}

	/**
	 * @param stdMajor the stdMajor to set
	 */
	@XmlElement(name = "major")
	public void setStdMajor(String stdMajor) {
		this.stdMajor = stdMajor;
	}

	/**
	 * @return the stdGrade
	 */
	public String getStdGrade() {
		return stdGrade;
	}

	/**
	 * @param stdGrade the stdGrade to set
	 */
	@XmlElement(name = "grade")
	public void setStdGrade(String stdGrade) {
		this.stdGrade = stdGrade;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		Student student = (Student) obj;
		if (this.stdName.equals(student.getStdName()) && this.stdGrade.equals(student.getStdGrade())
				&& this.stdEducation.equals(student.getStdEducation()) && this.stdMajor.equals(student.getStdMajor())){
			return true;
		}
		return false;
	}
	
	public Object toJson() throws TrackerException {
		JsonObjectBuilder result =  Json.createObjectBuilder();
		JsonUtil.putSafe(result, "name", this.stdName);
		JsonUtil.putSafe(result, "grade", this.stdGrade);
		JsonUtil.putSafe(result, "major", this.stdMajor);
		JsonUtil.putSafe(result, "education", this.stdEducation);
		return result;
	}
}