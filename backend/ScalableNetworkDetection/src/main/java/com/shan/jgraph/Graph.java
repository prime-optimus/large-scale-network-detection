package com.shan.jgraph;

import java.util.*;
import java.util.Map.Entry;
@SuppressWarnings("serial")
public class Graph extends HashMap<Node,List<Node>>{
	public static ArrayList<Node> listOfNodes;
	
	Graph(){
		
	}
	public void printPrimaryAndSecondaryNodes(){
		int i = 0;
		for(Entry <Node,List<Node>> entry : entrySet()){
			System.out.println(entry.getKey().returnNodeValue() + " is connected to " + getSecondaryNodes(entry));
		}
	}
	
	public String getSecondaryNodes(Entry <Node,List<Node>> entry){
		StringBuffer sb = new StringBuffer();
		ListIterator<Node> litr = entry.getValue().listIterator();
		while(litr.hasNext()){
			sb.append(litr.next().returnNodeValue());
			sb.append(", ");
		}
		return sb.toString();
	}
	
	public void fillAdjacencyList(Node parent,List<Node> children){
		put(parent,children);
		
	}
		
}
