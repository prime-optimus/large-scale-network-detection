package com.social.base;

public class Edge {
	private Node otherEnd;
	private int weight;

	public Edge(Node otherEnd) {
		this.otherEnd = otherEnd;
	}

	public Node getOtherEnd() {
		return otherEnd;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public void setOtherEnd(Node otherEnd) {
		this.otherEnd = otherEnd;
	}
	
	
}