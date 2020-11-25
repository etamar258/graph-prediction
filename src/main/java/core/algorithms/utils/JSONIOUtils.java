package core.algorithms.utils;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import com.google.common.io.Files;
import com.google.gson.Gson;

import core.entities.GroupStatistics;
import core.entities.JsonEntity;
import core.entities.SpecialConnectionElement;

public class JSONIOUtils 
{
	
	/**
	 * The function parse json object to the output file
	 * @param objectToWrite - The object to parse
	 * @param filePath - The file path of the output file
	 */
	public static void writeJsonObjectFile(JsonEntity objectToWrite, String filePath) 
	{
		Gson gsonParser = new Gson();
		
		try 
		{
			Files.write(gsonParser.toJson(objectToWrite).getBytes(), new File(filePath));
		}
		catch (IOException e) 
		{
			System.err.println("Can't write to the json file" + e.getMessage());
		}
	}
	
	public static void writeSetOfObjectsFile(Set<SpecialConnectionElement> objectsToWrite, String filePath) 
	{
		Gson gsonParser = new Gson();
		
		try 
		{
			Files.write(gsonParser.toJson(objectsToWrite).getBytes(), new File(filePath));
		}
		catch (IOException e) 
		{
			System.err.println("Can't write to the json file" + e.getMessage());
		}
	}
}
