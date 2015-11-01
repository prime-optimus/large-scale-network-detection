package com.shan.jgraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jxl.read.biff.BiffException;

public class GraphByAdjacencyList {

	Map<Integer,ArrayList<Integer>> mapOfStrings = new LinkedHashMap<Integer,ArrayList<Integer>>();
	Map<Integer, ArrayList<Integer>> mapOfNodes = new LinkedHashMap<Integer, ArrayList<Integer>>();
	List<Integer> listOfNodes = new ArrayList<Integer>();
	List<Edge> listOfEdges = new ArrayList<Edge>();
	
	public GraphByAdjacencyList(String fileName) throws BiffException, IOException{
		graphAsListOfPairOfStrings(fileName);
	}	
	
	private void graphAsListOfPairOfStrings(String fileName) throws BiffException, IOException {

		String filepath = "f:\\temp\\"+fileName+".tmp";
		
		
		System.out.println(filepath);
		  FileReader f = new FileReader(filepath);
		  BufferedReader br = new BufferedReader(f);
		  String line;
		  List<String> listOfStrings;
		  while((line = br.readLine())!= null){
			  listOfStrings = Arrays.asList(line.split(" "));
			  int startingNode = Integer.parseInt(listOfStrings.get(0));
			  int endingNode = Integer.parseInt(listOfStrings.get(1));
			  Edge e = new Edge(new Node(startingNode),new Node(endingNode),1);
			  listOfEdges.add(e);
			  /*if(!mapOfNodes.containsKey(startingNode)){
				  adjacentNodes = new ArrayList<Integer>();
				  adjacentNodes.add(endingNode);
				  mapOfNodes.put(startingNode, adjacentNodes);
			  } else {
				  adjacentNodes = mapOfNodes.get(startingNode);
				  adjacentNodes.add(endingNode);
				  mapOfNodes.put(startingNode, adjacentNodes);
			  }*/
			  if(!listOfNodes.contains(startingNode)){
				  listOfNodes.add(startingNode);
			  }
			  if(!listOfNodes.contains(endingNode)){
				  listOfNodes.add(endingNode);
			  }
		  }
	
	}

	private void graphAsNodesAndEdges() throws IOException {
		// TODO Auto-generated method stub
		String filepath = "C:\\Users\\SHANDEMETZ\\Downloads\\Compressed\\facebook_combined.txt\\facebook_combined.txt";
		GMLReader g1 = new GMLReader(filepath);
		setListOfNodes(g1.getListOfNodesInInteger());
		setListOfEdges(g1.getListOfEdges());
	}

	public Map<Integer, ArrayList<Integer>> getMapOfNodes() {
		return mapOfNodes;
	}

	public void setMapOfNodes(LinkedHashMap<Integer, ArrayList<Integer>> mapOfNodes) {
		this.mapOfNodes = mapOfNodes;
	}

	public List<Edge> getListOfEdges() {
		return listOfEdges;
	}

	public void setListOfEdges(ArrayList<Edge> listOfEdges) {
		this.listOfEdges = listOfEdges;
	}

	public Map<Integer, ArrayList<Integer>> getMapOfStrings() {
		return mapOfStrings;
	}

	public void setMapOfStrings(
			LinkedHashMap<Integer, ArrayList<Integer>> mapOfStrings) {
		this.mapOfStrings = mapOfStrings;
	}

	public List<Integer> getListOfNodes() {
		return listOfNodes;
	}

	public void setListOfNodes(List<Integer> list) {
		this.listOfNodes = list;
	}

}
