package com.steve.tracker.utils;

import java.nio.file.Path;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.steve.tracker.beans.Students;
import com.steve.tracker.exception.TrackerException;

/**
 * @author Praveen Kumar
 *
 */
public class TrackerUtility {
	
	public static Students generateObjectFromXML(Path xmlFilePath) throws TrackerException {
		Students students = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance( Students.class );
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			students = (Students)jaxbUnmarshaller.unmarshal( xmlFilePath.toFile());
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return students;
	}
}
