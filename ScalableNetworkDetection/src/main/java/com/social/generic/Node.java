package com.social.generic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class Node {
	private int id;
	private List<Edge> neighbors;
	private boolean leader, member, orbiter;
	private Node parent;
	
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

	public boolean isLeader() {
		return leader;
	}

	public void setLeader(boolean leader) {
		this.leader = leader;
	}

	public boolean isMember() {
		return member;
	}

	public void setMember(boolean member) {
		this.member = member;
	}

	public boolean isOrbiter() {
		return orbiter;
	}

	public void setOrbiter(boolean orbiter) {
		this.orbiter = orbiter;
	}

	public boolean isLeaderOrMember() {
		return isLeader() || isMember();
	}
	
	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
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
