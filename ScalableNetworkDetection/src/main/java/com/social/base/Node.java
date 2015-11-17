package com.social.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Node {
	private int id;
	private List<Edge> neighbors;
	private int community;

	public Node(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public String getStringId(){
		return String.valueOf(id);
	}

	public int getCommunity() {
		return community;
	}

	public void setCommunity(int community) {
		this.community = community;
	}

	public boolean addNeighbor(Node node){
		boolean result = false;
		
		Edge edge = new Edge(node);
		if(neighbors == null){
			neighbors = new ArrayList<>();
		} else if(!neighbors.contains(edge)){
			result = neighbors.add(edge);
		}
		return result;
	}

	public List<Edge> getNeighbors() {
		return neighbors == null ? new ArrayList<>() : neighbors;
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
