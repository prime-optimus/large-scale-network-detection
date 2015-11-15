package com.social.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Node {
	private int id;
	private List<Edge> neighbors;

	public Node(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public String getStringId(){
		return String.valueOf(id);
	}

	public boolean addNeighbor(Node node) {
		if(neighbors == null){
			neighbors = new ArrayList<>();
		}
		Edge edge = new Edge(node);
		return neighbors.add(edge);
	}

	public List<Edge> getNeighbors() {
		return neighbors == null ? Collections.emptyList() : neighbors;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this.getId(), false);
	}

	@Override
	public boolean equals(Object obj) {
		boolean result = false;
		if (obj instanceof Node){
			Node node = (Node) obj;
			result = node.getId() == this.getId();
		}
		return result;
	}
}
