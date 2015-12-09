package com.social.utils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.social.generic.Edge;
import com.social.generic.Node;

public class GraphCompressionUtils {
	public static List<Node> compressCommunityRepresentation(List<Node> adjacencyList, int size) {
		List<Node> communities = new ArrayList<Node>(size);
		
		for (int i=0; i<size; i++){
			communities.add(new Node(i));
		}
		
		for(Node node: adjacencyList){
			Node communityNode = communities.get(node.getCommunity());
			
			List<Edge> currentNeighbors = node.getNeighbors();
			for(Edge neighbor: currentNeighbors){
				Node otherEndCommunity = communities.get(neighbor.getOtherEnd().getCommunity());
				if (otherEndCommunity.getId() != communityNode.getId()){
					communityNode.addNeighbor(otherEndCommunity);
				}
			}
		}
		return communities;
	}
	
	public static List<Node> openCommunity(Map<Node, List<Node>> communities,
			List<Node> adjacencyList, int communityToOpen) {
		List<Node> membersList = new ArrayList<>();
		
		int baseNumber = adjacencyList.size();
		
		List<Node> nodes = new ArrayList<>();
		for(Node node: adjacencyList){
			if(node.getCommunity() == communityToOpen){
				nodes.add(node);
			}
		}
		
		Set<Integer> discoveredSet = new HashSet<>();
		
		Queue<Node> queue = new LinkedList<>();
		queue.addAll(nodes);
		
		while(!queue.isEmpty()){
			Node currentNode = queue.poll();
			System.out.println("currentNode Id: " + currentNode.getId());
			
			if(!discoveredSet.contains(currentNode.getId())){
				for(Edge edge: currentNode.getNeighbors()){
					Node otherNode = edge.getOtherEnd();
					
					System.out.println("currentNode: " + otherNode.getCommunity());
					
					Node copyNode = new Node(otherNode.getId());
					copyNode.setCommunity(otherNode.getCommunity());
					
					if(otherNode.getCommunity() != communityToOpen){
						Node tempNode = new Node(otherNode.getCommunity());
						tempNode.setCommunity(otherNode.getCommunity());
						copyNode.addNeighbor(tempNode);
					} else {
						Node tempNode = new Node(baseNumber + otherNode.getId());
						tempNode.setCommunity(otherNode.getCommunity());
						copyNode.addNeighbor(tempNode);
						//tempNode.getNeighbors().addAll(otherNode.getNeighbors());
						
						/*otherNode.getNeighbors().forEach(currentEdge -> {
							if(currentEdge.getOtherEnd().getId() != otherNode.getId()){
								queue.add(currentEdge.getOtherEnd());
							}
						});*/
						
						System.out.println("Id: " + tempNode.getId());
					}
					membersList.add(copyNode);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
			System.out.println("discovered");
			discoveredSet.add(currentNode.getId());
			
		}
		return membersList;
	}

}
