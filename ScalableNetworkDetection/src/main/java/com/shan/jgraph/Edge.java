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
	public int getEdgeWeight() {
		return edgeWeight;
	}
	public void setEdgeWeight(int edgeWeight) {
		this.edgeWeight = edgeWeight;
	}
	public boolean representativeFunction(int checkNode1,int checkNode2){
		if((checkNode1 == getStartingNode().getId() && checkNode2 == getEndingNode().getId()) || (checkNode2 == getStartingNode().getId() && checkNode1 == getEndingNode().getId())){
			return true;
		}	else{
			return false;
		}
	}
	Node endingNode;
	int edgeWeight;
	Edge(Node NodeA,Node NodeB, int weight){
		this.startingNode = NodeA;
		this.endingNode = NodeB;
		this.edgeWeight = weight;
	}

}
