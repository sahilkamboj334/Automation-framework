package com.framework.utill;

import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonReader {
public static HashMap<String, Object> getMapFromNestedJson(String filepath) throws Exception{
	HashMap<String, Object> map=new HashMap<>();
	JsonParser jsonParser=new JsonParser();
	JsonObject object=(JsonObject)jsonParser.parse(new FileReader(filepath));
	Iterator<Entry<String, JsonElement>> itr=object.entrySet().iterator();
	while(itr.hasNext()){
		Map.Entry<String, JsonElement> entry=itr.next();
		JsonElement element=entry.getValue();
		map.put(entry.getKey(), (Object)element);
	}
	return 	map;
}
public static HashMap<String, String> getMapFromJsonList(String filepath){
	HashMap<String, String>  map=new HashMap<>();
	JsonParser jsonParser=new JsonParser();
	JsonArray array=(JsonArray)jsonParser.parse(filepath);
	for(int i=0;i<array.size();i++){
		JsonObject jsonObject=(JsonObject)array.get(i);
		Iterator<Entry<String, JsonElement>> itr=jsonObject.entrySet().iterator();
		while(itr.hasNext()){
			Map.Entry<String, JsonElement> temp=itr.next();
			JsonElement element=temp.getValue();
			map.put(temp.getKey(),element.toString());
					
		}
	}
	
	return map;
}

}
