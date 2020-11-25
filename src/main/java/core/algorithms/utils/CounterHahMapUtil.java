package core.algorithms.utils;

import java.util.HashMap;

import core.entities.SpecialConnectionElement;

public class CounterHahMapUtil 
{
	/**
	 * The function get list of items that represent by separator key
	 * and update the hashmap counter
	 * @param mapToUpdate - The hashmap that we update
	 * @param listOfObjects - The string that represent list of objects
	 * @param separatorKey - The separator key for the string list
	 */
	public static void createHashmapFromString(HashMap<String, Integer> mapToUpdate, String listOfObjects, String separatorKey)
	{
		String[] allObjects = listOfObjects.split(separatorKey);
		
		for (String currObject : allObjects) 
		{
			insertOrUpdateNewCounterMape(mapToUpdate, currObject.trim());
		}
	}
	
	/**
	 * The function get the counter hashmap to update and the key
	 * that we want to update and update its counter
	 * @param mapToUpdate - The hashmap we want to update
	 * @param key - The key that of the counter value
	 */
	public static void insertOrUpdateNewCounterMape(HashMap<String, Integer> mapToUpdate, String key)
	{
		if(mapToUpdate.containsKey(key))
		{
			mapToUpdate.put(key, mapToUpdate.get(key).intValue() + 1);
			return;
		}
		
		// If new
		mapToUpdate.put(key, 1);
	}
	
	public static void insertOrUpdateJsonEntityCounterMape(HashMap<SpecialConnectionElement, Integer> mapToUpdate, SpecialConnectionElement key)
	{
		if(mapToUpdate.containsKey(key))
		{
			mapToUpdate.put(key, mapToUpdate.get(key).intValue() + 1);
			return;
		}
		
		// If new
		mapToUpdate.put(key, 1);
	}
}
