package com.social.base;

import java.util.List;
import java.util.Map;

public abstract class CommunityAlgorithm {
	
	public Class<? extends Node> getNodeClass(){
		return Node.class;
	}
	
	public Class<? extends Edge> getEdgeClass(){
		return Edge.class;
	}
	
	public abstract Map<Node, List<Node>> detectCommunities(List<Node> adjacencyList);
	
}
