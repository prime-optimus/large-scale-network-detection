package com.social.generic;

public class Edge {
	private Node otherEnd;
	//private int weight;

	public Edge(Node otherEnd) {
		this.otherEnd = otherEnd;
	}

	public Node getOtherEnd() {
		return otherEnd;
	}
}
