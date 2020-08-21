package com.uinetworks.ecar;

import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonSample {
	public void jsonSample() {
		HashMap<String, Object> myHashMap1 = new HashMap<String, Object>();
        JSONObject jsonObject1 = new JSONObject(); // 중괄호에 들어갈 속성 정의 { "a" : "1", "b" : "2" }
        JSONArray jsonArray1 = new JSONArray(); // 대괄호 정의 [{ "a" : "1", "b" : "2" }]
        JSONObject finalJsonObject1 = new JSONObject(); // 중괄호로 감싸 대괄호의 이름을 정의함 { "c" : [{  "a" : "1", "b" : "2" }] }
        
        myHashMap1.put("이름", "잠자리");
        myHashMap1.put("다리갯수", "6");
        
        jsonObject1 = new JSONObject(myHashMap1); 
        jsonArray1.add(jsonObject1);
        
        myHashMap1 = new HashMap<String, Object>();
        myHashMap1.put("이름", "사슴벌레");
        myHashMap1.put("다리갯수", "6");
        
        jsonObject1 = new JSONObject(myHashMap1);
        jsonArray1.add(jsonObject1);
        
        finalJsonObject1.put("곤충", jsonArray1);
        finalJsonObject1.put("동물", "코끼리");
        finalJsonObject1.put("식물", "무궁화");
        
        System.out.println(finalJsonObject1);
        
        /*
         {  "식물":"무궁화", "곤충":[{"이름":"잠자리","다리갯수":"6"},{"이름":"사슴벌레","다리갯수":"6"}],"동물":"코끼리"}
         */
	}
	
	public void stringJSONToJSONParsing() {
		String jsonString1 = "{\"식물\":\"무궁화\",\"곤충\":[{\"이름\":\"잠자리\",\"다리갯수\":\"6\"},{\"이름\":\"사슴벌레\",\"다리갯수\":\"6\"}],\"동물\":\"코끼리\"}";
        JSONParser jsonParser1 = new JSONParser();
        JSONObject jsonObject1;
 
        try {
            
            jsonObject1 = (JSONObject) jsonParser1.parse(jsonString1);
            JSONArray jsonArray1 = (JSONArray) jsonObject1.get("곤충");
      
            for(int i=0; i<jsonArray1.size(); i++){
                System.out.println("곤충"+ i +" : " +jsonArray1.get(i));            
                JSONObject objectInArray = (JSONObject) jsonArray1.get(i);
                System.out.println("Key값은 "+objectInArray.get("이름"));
                System.out.println("Value값은 "+objectInArray.get("다리갯수"));
            }
            /*
                곤충0 : {"이름":"잠자리","다리갯수":"6"}
                Key값은 잠자리
                Value값은 6
                
                곤충1 : {"이름":"사슴벌레","다리갯수":"6"}
                Key값은 사슴벌레
                Value값은 6
             */
            
        } catch (ParseException e) {
            e.printStackTrace();
        }
	}
}
