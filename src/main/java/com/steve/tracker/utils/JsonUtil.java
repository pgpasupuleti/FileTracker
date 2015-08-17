package com.steve.tracker.utils;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;

import org.apache.commons.collections4.CollectionUtils;

/**
 * @author Praveen Kumar
 *
 */
public class JsonUtil {
	public static final JsonObject EMPTY_OBJECT = Json.createObjectBuilder().build();
	public static final JsonArray EMPTY_ARRAY = Json.createArrayBuilder().build();

	public static JsonObject parseObject(String json) {
		JsonReader r = Json.createReader(new StringReader(json));
		return r.readObject();
	}

	public static JsonArray parseArray(String json) {
		JsonReader r = Json.createReader(new StringReader(json));
		return r.readArray();
	}

	public static JsonObject readFileObject(Path filePath) throws IOException {
		JsonReader r = null;
		try
		{
			r = Json.createReader(Files.newBufferedReader(filePath, Charset.forName("UTF-8")));
			return r.readObject();
		}
		finally
		{
			if(r != null)
			{
				r.close();
			}
		}
	}

	public static JsonArray readFileArray(Path filePath) throws IOException {
		JsonReader r = null;
		try
		{
			r = Json.createReader(Files.newBufferedReader(filePath, Charset.forName("UTF-8")));
			return r.readArray();
		}
		finally
		{
			if(r != null)
			{
				r.close();
			}
		}
	}

	public static void writeFile(Path filePath, JsonObject json) throws IOException {
		JsonWriter w = null;
		try
		{
			w = Json.createWriter(Files.newBufferedWriter(filePath, Charset.forName("UTF-8")));
			w.writeObject(json);
		}
		finally
		{
			if(w != null)
			{
				w.close();
			}
		}
	}

	public static void writeFile(Path filePath, JsonArray json) throws IOException {
		JsonWriter w = null;
		try
		{
			w = Json.createWriter(Files.newBufferedWriter(filePath, Charset.forName("UTF-8")));
			w.writeArray(json);
		}
		finally
		{
			if(w != null)
			{
				w.close();
			}
		}
	}

	/**
	 * JSON-P (JSR-353) don't seem to provide a way to build JsonObjectBuilder from
	 * a JsonObject. This method provides that functionality.
	 *
	 * @param initialObject
	 * @return
	 */
	public static JsonObjectBuilder createObjectBuilder(JsonObject initialObject) {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		for (Map.Entry<String, JsonValue> entry : initialObject.entrySet()) {
			builder.add(entry.getKey(), entry.getValue());
		}
		return builder;
	}

	public static JsonObjectBuilder createObjectBuilderWithExcludes(JsonObject initialObject, final String[] excludes) {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		List<String> theExcludesList = Arrays.asList(excludes);
		for (Map.Entry<String, JsonValue> entry : initialObject.entrySet()) {
			if (!theExcludesList.contains(entry.getKey())) {
				builder.add(entry.getKey(), entry.getValue());
			}
		}
		return builder;
	}

	public static JsonObjectBuilder createObjectBuilderWithIncludes(JsonObject initialObject, final String[] includes) {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		List<String> theIncludesList = Arrays.asList(includes);
		for (Map.Entry<String, JsonValue> entry : initialObject.entrySet()) {
			if (theIncludesList.contains(entry.getKey())) {
				builder.add(entry.getKey(), entry.getValue());
			}
		}
		return builder;
	}
	
	/**
	 * JSON-P (JSR-353) don't seem to provide a way to build JsonArrayBuilder from
	 * a JsonArray. This method provides that functionality.
	 *
	 * @param initialArray
	 * @return
	 */
	public static JsonArrayBuilder createArrayBuilder(JsonArray initialArray) {
		JsonArrayBuilder builder = Json.createArrayBuilder();
		for (JsonValue value : initialArray) {
			builder.add(value);
		}
		return builder;
	}

	public static JsonObjectBuilder putSafe(JsonObjectBuilder builder, String name, String value) {
		if (value != null) {
			builder.add(name, value);
		}
		return builder;
	}
	
	public static JsonObjectBuilder putSafe(JsonObjectBuilder builder, String name, Short value) {
		if (value != null) {
			builder.add(name, Long.valueOf(value.toString()));
		}
		return builder;
	}

	public static JsonObjectBuilder putSafe(JsonObjectBuilder builder, String name, Integer value) {
		if (value != null) {
			builder.add(name, value);
		}
		return builder;
	}

	public static JsonObjectBuilder putSafe(JsonObjectBuilder builder, String name, Long value) {
		if (value != null) {
			builder.add(name, value);
		}
		return builder;
	}
	
	public static JsonObjectBuilder putSafe(JsonObjectBuilder builder, String name, BigDecimal value) {
		if (value != null) {
			builder.add(name, value);
		}
		return builder;
	}

	public static JsonObjectBuilder putSafe(JsonObjectBuilder builder, String name, BigInteger value) {
		if (value != null) {
			builder.add(name, value);
		}
		return builder;
	}

	public static JsonObjectBuilder putSafe(JsonObjectBuilder builder, String name, Boolean value) {
		if (value != null) {
			builder.add(name, value);
		}
		return builder;
	}

	public static JsonObjectBuilder putSafe(JsonObjectBuilder builder, String name, Date value) {
		if (value != null) {
			builder.add(name, ISO8601.format(value));
		}
		return builder;
	}

	public static JsonObjectBuilder putSafe(JsonObjectBuilder builder, String name, JsonObject value) {
		if (value != null) {
			builder.add(name, value);
		}
		return builder;
	}

	public static JsonObjectBuilder putSafe(JsonObjectBuilder builder, String name, JsonArray value) {
		if (value != null) {
			builder.add(name, value);
		}
		return builder;
	}

	public static JsonObjectBuilder putSafe(JsonObjectBuilder builder, String name, JsonObjectBuilder value) {
		if (value != null) {
			builder.add(name, value);
		}
		return builder;
	}

	public static JsonObjectBuilder putSafe(JsonObjectBuilder builder, String name, JsonArrayBuilder value) {
		if (value != null) {
			builder.add(name, value);
		}
		return builder;
	}

	public static JsonObjectBuilder putSafeId(JsonObjectBuilder builder, String name, BigDecimal value) {
		if (value != null) {
			builder.add(name, value.toBigInteger().toString());
		}
		return builder;
	}
	
	public static JsonObjectBuilder putSafeId(JsonObjectBuilder builder, String name, BigInteger value) {
		if (value != null) {
			builder.add(name, value.toString());
		}
		return builder;
	}
	
	/**
	 * 
	 * @param theJson1
	 * @param theJson2
	 * @return
	 */
	public static JsonObjectBuilder merge(JsonObject theSourceJson, JsonObject theJsonToBeMerged)
	{
		JsonObjectBuilder theBuilder = Json.createObjectBuilder();
		
		//Add the new fields
		if(theJsonToBeMerged != null)
		{
			for(String theKey : theJsonToBeMerged.keySet())
			{
				theBuilder.add(theKey, theJsonToBeMerged.get(theKey));
			}
		}
		
		//Add the existing fields
		if(theSourceJson != null)
		{
			for(String theKey : theSourceJson.keySet())
			{
				if(theJsonToBeMerged == null || ! theJsonToBeMerged.containsKey(theKey))
				{
					JsonValue theValue = theSourceJson.get(theKey);
					if(theValue == null)
					{
						theBuilder.addNull(theKey);
					}
					else
					{
						theBuilder.add(theKey, theValue);
					}
				}
			}
		}
		
		return theBuilder;
	}

	public static JsonArrayBuilder checkAndAddObject(JsonArrayBuilder finalArrayBuilder, JsonObject jsonObject){
		boolean isExist = false;
		JsonArray jsonArray = finalArrayBuilder.build();
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonObject jObject = jsonArray.getJsonObject(i);
			for(String key : jObject.keySet()){
				if (jsonObject.containsKey(key) && jsonObject.get(key) == jObject.get(key)) {
					isExist = true;
					break;
				}
			}
		}
		if (!isExist) {
			finalArrayBuilder.add(jsonObject);
		}
		return finalArrayBuilder;
	}
	public static JsonObjectBuilder updateSafe(JsonObject jsonObject, String name, String value) {
		JsonObjectBuilder jBuilder = Json.createObjectBuilder();
		if (jsonObject != null) {
			for (String key : jsonObject.keySet()) {
				if (name.equals(key)) {
					jBuilder.add(key, value);
				}else{
					jBuilder.add(key,jsonObject.get(key));
				}
			}
		}
		return jBuilder;
	}
	
	public static JsonObjectBuilder addRupdateSafe(JsonObject jsonObject, String name, String value) {
		JsonObjectBuilder jBuilder = Json.createObjectBuilder();
		if (jsonObject != null) {
			if(CollectionUtils.isNotEmpty(jsonObject.keySet())){
				for (String key : jsonObject.keySet()) {
					if (name.equals(key)) {
						jBuilder.add(key, value);
					}else{
						jBuilder.add(key,jsonObject.get(key));
					}
				}
			} else {
				jBuilder.add(name, value);
			}
		}
		return jBuilder;
	}
	
	public static JsonObjectBuilder addSafe(JsonObject jsonObject, String name, JsonArray newArray) {
		JsonObjectBuilder jBuilder = Json.createObjectBuilder();
		boolean keyFound = false;
		if (jsonObject != null) {
			for (String key : jsonObject.keySet()) {
				//Name found
				if (name.equals(key)) {
					keyFound = true;
					JsonArrayBuilder finalArrayBuilder = Json.createArrayBuilder();

					JsonArray jsonArray = jsonObject.getJsonArray(key);
					for (int i = 0; i < jsonArray.size(); i++) {
						finalArrayBuilder.add(jsonArray.getJsonObject(i));
					}
					for (int i = 0; i < newArray.size(); i++) {
						finalArrayBuilder = checkAndAddObject(finalArrayBuilder,newArray.getJsonObject(i));
					}
					jBuilder.add(key,finalArrayBuilder.build());
				} else {
					jBuilder.add(key, jsonObject.get(key));
				}
			}
			
			if(!keyFound){
				jBuilder.add(name, newArray);
			}
		}
		return jBuilder;
	}
}
