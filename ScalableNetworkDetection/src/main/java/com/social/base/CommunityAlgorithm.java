package com.social.base;

import java.util.List;

public abstract class CommunityAlgorithm {
	
	public Class<? extends Node> getNodeClass(){
		return Node.class;
	}
	
	public Class<? extends Edge> getEdgeClass(){
		return Edge.class;
	}
	
	public abstract int detectCommunities(List<Node> adjacencyList);
	
}
