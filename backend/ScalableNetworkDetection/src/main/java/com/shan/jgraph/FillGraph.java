package com.shan.jgraph;

import java.util.ArrayList;
import java.util.Arrays;

public class FillGraph {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graph g1 = new Graph();

		ArrayList<Node> listOfNodes = new ArrayList<Node>();
		for(int i = 0 ;i < 6;i++){
			listOfNodes.add(new Node(i+1));
		}
		g1.fillAdjacencyList(listOfNodes.get(0), Arrays.asList(listOfNodes.get(1),listOfNodes.get(2),listOfNodes.get(3),listOfNodes.get(4),listOfNodes.get(5)));
		g1.fillAdjacencyList(listOfNodes.get(1), Arrays.asList(listOfNodes.get(0),listOfNodes.get(2),listOfNodes.get(3),listOfNodes.get(4)));
		g1.fillAdjacencyList(listOfNodes.get(2), Arrays.asList(listOfNodes.get(0),listOfNodes.get(1),listOfNodes.get(3),listOfNodes.get(4)));
		g1.fillAdjacencyList(listOfNodes.get(3), Arrays.asList(listOfNodes.get(0),listOfNodes.get(1),listOfNodes.get(2),listOfNodes.get(4)));
		g1.fillAdjacencyList(listOfNodes.get(4), Arrays.asList(listOfNodes.get(0),listOfNodes.get(1),listOfNodes.get(2),listOfNodes.get(3)));
		g1.fillAdjacencyList(listOfNodes.get(5), Arrays.asList(listOfNodes.get(0)));
		
		g1.printPrimaryAndSecondaryNodes();
	}

}
