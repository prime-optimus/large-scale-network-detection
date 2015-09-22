package com.shan.jgraph;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class MakeAdjacencyListFromMatrix {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws BiffException 
	 */
	MakeAdjacencyListFromMatrix(){
	}
	public String getAdjacencyListFileName() throws IOException, BiffException {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		int totalPlayersPlayed = 13;
		File f = new File("AdjacencyList.txt");
		FileWriter fw = new FileWriter(f);
		BufferedWriter bw = new BufferedWriter(fw);
		  Edge e1;
		  List<List<Edge>> listOfNodes = new ArrayList<List<Edge>>();
		  Map<String,String> edgesAdjacencyList = new HashMap<String,String>(130);
		  ArrayList<Edge> passes;
	      Workbook workbook = Workbook.getWorkbook(new File("D:\\cygwin\\home\\downloads\\2014309_tpd.xls"));
	      Sheet sheet = workbook.getSheet(1);
	      for(int j = 1;j < 15;j++){

		  passes = new ArrayList<Edge>();
	    	  for (int i = 1;i < 15;i++){
	    		  String NodeA = sheet.getCell(j, 0).getContents();
	    		  String NodeB = sheet.getCell(0,i).getContents();
	    		  String weight = sheet.getCell(i,j).getContents();
	    		  if (!weight.isEmpty() && !weight.contains("-")){
	    			  passes.add(new Edge(new Node(NodeA), new Node(NodeB),weight));
	    			  //edgesAdjacencyList.put(String.valueOf(NodeA), String.valueOf(NodeB));
	    			  bw.write(String.valueOf(NodeA) + " " + String.valueOf(NodeB)+"\n");
	    		  } else {
	    			  passes.add(new Edge(new Node(NodeA), new Node(NodeB),"0"));
	    		  }
	    	  }
	    	  listOfNodes.add(passes);
	      }
	      
	      for(int i = 0;i<14;i++){
	    	  for(int j = 0;j < 14;j++){
	    		System.out.println(listOfNodes.get(i).get(j).getEdgeWeight());  
	    	  }
	      }
	      bw.close();
	      workbook.close();
	      
	      return f.getAbsolutePath();

	
	}

}
