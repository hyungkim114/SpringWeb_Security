package com.spring.boardweb.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boardweb.dto.TestDTO;

@RestController
public class TestApi {
	@GetMapping("/testapi1")
	public Map<String, String> testapi1() {
		Map<String, String> returnMap = new HashMap<String, String>();
		
		returnMap.put("firstName", "gildong");
		returnMap.put("lastName", "hong");
		
		return returnMap;
	}
	
	@GetMapping("/testapi2")
	public List<Map<String, String>> testapi2() {
		List<Map<String, String>> returnList = new ArrayList<Map<String, String>>();

		
		for(int i=0; i < 10; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put(Integer.toString(i), Integer.toString(i));
			
			returnList.add(map);
		}
		
		return returnList;
	}
	
	@GetMapping("/testapi3")
	public TestDTO testapi3() {
		TestDTO testDTO = new TestDTO();
		testDTO.setIdx(1);
		testDTO.setTestStr("hello");
		
		List<String> list = new ArrayList<String>();
		list.add("gildong");
		list.add("hong");
		
		testDTO.setTestList(list);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		
		testDTO.setTestMap(map);
		
		return testDTO;
  	}
}
