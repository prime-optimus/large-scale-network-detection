package com.shan.jgraph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class GraphByAdjacencyList {

	LinkedHashMap<Integer,ArrayList<Integer>> mapOfStrings = new LinkedHashMap<Integer,ArrayList<Integer>>();
	LinkedHashMap<Integer, ArrayList<Integer>> mapOfNodes = new LinkedHashMap<Integer, ArrayList<Integer>>();
	ArrayList<Node> listOfNodes = new ArrayList<Node>();
	ArrayList<Edge> listOfEdges = new ArrayList<Edge>();
	GraphByAdjacencyList() throws BiffException, IOException{
		fillAdjacencyListByEdges();
	}	
	
	public void fillAdjacencyListByEdges() throws BiffException, IOException{
		
		//graphAsNodesAndEdges();
		graphAsListOfPairOfStrings();
	}

	private void graphAsListOfPairOfStrings() throws BiffException, IOException {
		// TODO Auto-generated method stub

		String filepath = "C:\\Users\\SHANDEMETZ\\Downloads\\Compressed\\facebook_combined.txt\\facebook_combined.txt";//new MakeAdjacencyListFromMatrix().getAdjacencyListFileName();
		
		
		System.out.println(filepath);
		  FileReader f = new FileReader(filepath);
		  BufferedReader br = new BufferedReader(f);
		  String line;
		  Node key;
		  List<String> listOfStrings;
		  while((line = br.readLine())!= null){
			  listOfStrings = Arrays.asList(line.split(" "));
			  Edge e = new Edge(new Node(Integer.parseInt(listOfStrings.get(0))),new Node(Integer.parseInt(listOfStrings.get(1))),1);
			  listOfEdges.add(e);
			  if(!mapOfNodes.containsKey(listOfStrings.get(0))){
				  ArrayList<Integer> adjacentNodes = new ArrayList<Integer>();
				  adjacentNodes.add(listOfStrings.get(1));
				  mapOfNodes.put(Integer.parseInt(listOfStrings.get(0)), adjacentNodes);
			  } else {
				  mapOfNodes.get(listOfStrings.get(0)).add(listOfStrings.get(1));
			  }
		  }
		  /*for(Entry<Integer,ArrayList<Integer>> entry:mapOfStrings.entrySet()){
			  System.out.println(entry.getKey());
			  ListIterator<Integer> litr = entry.getValue().listIterator();
			  while(litr.hasNext())
				  System.out.print(litr.next() + " ");
			  System.out.println();
			  System.out.println();
		  }*/
	
	}

	private void graphAsNodesAndEdges() throws IOException {
		// TODO Auto-generated method stub
		String filepath = "C:\\Users\\SHANDEMETZ\\Downloads\\Compressed\\lesmis\\lesmis.gml";
		GMLReader g1 = new GMLReader(filepath);
		setMapOfNodes(g1.getListOfConnectedNodes());
		setListOfEdges(g1.getListOfEdges());
	}

	public LinkedHashMap<Integer, ArrayList<Integer>> getMapOfNodes() {
		return mapOfNodes;
	}

	public void setMapOfNodes(LinkedHashMap<Integer, ArrayList<Integer>> mapOfNodes) {
		this.mapOfNodes = mapOfNodes;
	}

	public ArrayList<Edge> getListOfEdges() {
		return listOfEdges;
	}

	public void setListOfEdges(ArrayList<Edge> listOfEdges) {
		this.listOfEdges = listOfEdges;
	}

	public LinkedHashMap<Integer, ArrayList<Integer>> getMapOfStrings() {
		return mapOfStrings;
	}

	public void setMapOfStrings(
			LinkedHashMap<Integer, ArrayList<Integer>> mapOfStrings) {
		this.mapOfStrings = mapOfStrings;
	}

	public ArrayList<Node> getListOfNodes() {
		return listOfNodes;
	}

	public void setListOfNodes(ArrayList<Node> listOfNodes) {
		this.listOfNodes = listOfNodes;
	}

}
