package com.shan.jgraph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String filepath = "D:\\Web Project\\JavaGraph\\AdjacencyList.txt";
		FileReader f = new FileReader(filepath);
		  BufferedReader br = new BufferedReader(f);
		  String line;
		  List<String> listOfStrings;
		  while((line = br.readLine()) != null){
			  listOfStrings = Arrays.asList(line.split(" "));
			  System.out.println("{'source':"+listOfStrings.get(0)+",'target':"+listOfStrings.get(1)+"}");
		  }
	}

}
