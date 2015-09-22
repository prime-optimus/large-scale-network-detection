package com.shan.jgraph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class GraphByAdjacencyMatrix {

	List<List<Edge>> graphAsListOfNodes = new ArrayList<List<Edge>>();
	GraphByAdjacencyMatrix() throws BiffException, IOException{
		fillAdjacencyMatrixByEdges();
	}	
	
	public void fillAdjacencyMatrixByNodes() throws BiffException, IOException{
		/*int totalPlayersPlayed = 13;
		  Edge e1;
		  List<List<Node>> listOfNodes = new ArrayList<List<Node>>();
		  ArrayList<Node> passes;
	      Workbook workbook = Workbook.getWorkbook(new File("D:\\cygwin\\home\\downloads\\2014309_tpd.xls"));
	      Sheet sheet = workbook.getSheet(1);
	      for(int j = 1;j < 15;j++){

		  passes = new ArrayList<Node>();
	    	  for (int i = 1;i < 15;i++){
	    		  int NodeA = Integer.parseInt(sheet.getCell(i, 0).getContents());
	    		  int NodeB = Integer.parseInt(sheet.getCell(0,j).getContents());
	    		  String weight = sheet.getCell(i,j).getContents();
	    		  if (!weight.isEmpty() && !weight.contains("-")){
	    			  passes.add(new Edge(new Node(NodeA), new Node(NodeB),Integer.parseInt(weight)));
	    		  } else {
	    			  passes.add(new Edge(new Node(NodeA), new Node(NodeB),0));
	    		  }
	    	  }
	    	  listOfNodes.add(passes);
	      }
	      
	      for(int i = 0;i<14;i++){
	    	  for(int j = 0;j < 14;j++){
	    		System.out.println(listOfNodes.get(i).get(j).getEdgeWeight());  
	    	  }
	      }
	      workbook.close();
	      setGraphAsListOfNodes(listOfNodes);*/

	}
	public void fillAdjacencyMatrixByEdges() throws BiffException, IOException{
		
		  int totalPlayersPlayed = 13;
		  Edge e1;
		  List<List<Edge>> listOfNodes = new ArrayList<List<Edge>>();
		  ArrayList<Edge> passes;
	      Workbook workbook = Workbook.getWorkbook(new File("D:\\cygwin\\home\\downloads\\2014309_tpd.xls"));
	      Sheet sheet = workbook.getSheet(1);
	      for(int j = 1;j < 15;j++){

  		  passes = new ArrayList<Edge>();
	    	  for (int i = 1;i < 15;i++){
	    		  int NodeA = Integer.parseInt(sheet.getCell(j, 0).getContents());
	    		  int NodeB = Integer.parseInt(sheet.getCell(0,i).getContents());
	    		  String weight = sheet.getCell(i,j).getContents();
	    		  if (!weight.isEmpty() && !weight.contains("-")){
	    			  passes.add(new Edge(new Node(NodeA), new Node(NodeB),Integer.parseInt(weight)));
	    		  } else {
	    			  passes.add(new Edge(new Node(NodeA), new Node(NodeB),0));
	    		  }
	    	  }
	    	  listOfNodes.add(passes);
	      }
	      
	      for(int i = 0;i<14;i++){
	    	  for(int j = 0;j < 14;j++){
	    		System.out.println(listOfNodes.get(i).get(j).getEdgeWeight());  
	    	  }
	      }
	      workbook.close();
	      setGraphAsListOfNodes(listOfNodes);
	}

	public List<List<Edge>> getGraphAsListOfNodes() {
		return graphAsListOfNodes;
	}

	public void setGraphAsListOfNodes(List<List<Edge>> graphAsListOfNodes) {
		this.graphAsListOfNodes = graphAsListOfNodes;
	}

}
