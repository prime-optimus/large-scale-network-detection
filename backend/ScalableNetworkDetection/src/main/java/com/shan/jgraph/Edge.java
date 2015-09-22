package com.shan.jgraph;

public class Edge {
	
	Edge(){}
	Node startingNode;
	public Node getStartingNode() {
		return startingNode;
	}
	public void setStartingNode(Node startingNode) {
		this.startingNode = startingNode;
	}
	public Node getEndingNode() {
		return endingNode;
	}
	public void setEndingNode(Node endingNode) {
		this.endingNode = endingNode;
	}
	public String getEdgeWeight() {
		return edgeWeight;
	}
	public void setEdgeWeight(String edgeWeight) {
		this.edgeWeight = edgeWeight;
	}
	Node endingNode;
	String edgeWeight;
	Edge(Node NodeA,Node NodeB, String weight){
		this.startingNode = NodeA;
		this.endingNode = NodeB;
		this.edgeWeight = weight;
	}

}
