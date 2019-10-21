package com.framework.methods;

import java.util.HashMap;
import java.util.Map;

public class KeywordFactory {
private Map<String,String> keyMap=new HashMap<>();

public KeywordFactory(){
	this.loadKeywords();
}
public void loadKeywords(){
	for(Keyword keyword:Keyword.values()){
		keyMap.put(keyword.toString(), keyword.getMethodName());
	}
}
public String getMethodName(String key){
	if(keyMap.containsKey(key)){
		return keyMap.get(key);
	}
			
	return null;
}

}
