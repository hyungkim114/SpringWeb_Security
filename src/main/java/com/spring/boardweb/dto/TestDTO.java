package com.spring.boardweb.dto;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class TestDTO {
	private int idx;
	private String testStr;
	private List<String> testList;
	private Map<String, String> testMap;
}
