package com.social.base;

import org.apache.commons.lang3.builder.HashCodeBuilder;

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
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this.otherEnd.getId(), false);
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		
		if (obj instanceof Edge){
			Edge edge = (Edge) obj;
			result = edge.getOtherEnd().getId() == this.otherEnd.getId();
		}
		return result;
	}
	
}