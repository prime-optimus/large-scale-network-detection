package com.social.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.social.generic.Edge;
import com.social.generic.Node;

public class GraphCompressionUtils {
	public static List<Node> compressCommunityRepresentation(List<Node> adjacencyList, int size) {
		List<Node> communities = new ArrayList<Node>(size);
		
		for (int i=0; i<size; i++){
			communities.add(new Node(i));
		}
		
		adjacencyList.forEach(node -> {
			Node communityNode = communities.get(node.getCommunity());
			
			List<Edge> currentNeighbors = node.getNeighbors();
			currentNeighbors.forEach(neighbor -> {
				Node otherEndCommunity = communities.get(neighbor.getOtherEnd().getCommunity());
				if (otherEndCommunity.getId() != communityNode.getId()){
					communityNode.addNeighbor(otherEndCommunity);
				}
			});
		});
		return communities;
	}
	
	public static List<Node> openCommunity(Map<Node, List<Node>> communities,
			List<Node> adjacencyList, int communityToOpen) {
		List<Node> membersList = new ArrayList<>();
		
		int baseNumber = adjacencyList.size();
		List<Node> nodes = communities.get(new Node(communityToOpen));
		nodes.forEach(currentNode -> {
			Node currentTempNode = new Node(baseNumber + currentNode.getId());
			int currentCommunity = currentNode.getCommunity();
			
			currentNode.getNeighbors().forEach(otherEdge -> {
				
				if (otherEdge.getOtherEnd().getCommunity() == currentCommunity){
					Node communityNodeToAdd = new Node(otherEdge.getOtherEnd().getCommunity());
					//System.out.println("Community -- " + currentNode.getId() + " : " + otherEdge.getOtherEnd().getCommunity());
					currentTempNode.addNeighbor(communityNodeToAdd);
				} else {
					Node communityNodeToAdd = new Node(baseNumber + otherEdge.getOtherEnd().getId());
					//System.out.println("Member -- " + (currentNode.getId() + " : " + otherEdge.getOtherEnd().getId()));
					currentTempNode.addNeighbor(communityNodeToAdd);
				}
				//System.out.println("Current Node inner -- " + membersList);
			});
			//System.out.println("Current Node -- " + membersList);
			membersList.add(currentTempNode);
		});
		
		return membersList;
	}


}
