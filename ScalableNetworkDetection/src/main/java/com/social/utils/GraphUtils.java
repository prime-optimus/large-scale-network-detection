package com.social.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;

import com.google.gson.stream.JsonWriter;
import com.social.generic.Node;
import com.social.ldf.LowDegreeFolloingAlgorithm;

public class GraphUtils {
	
	public static List<Node> getAdjacencyListForGraphFile(String filePath, int totalNodes) 
			throws FileNotFoundException{
		List<Node> adjacencyList = new ArrayList<Node>(totalNodes);
		for (int i=0; i<totalNodes; i++){
			adjacencyList.add(new Node(i));
		}
		
		Scanner in  = new Scanner(new FileInputStream(filePath));
		while(in.hasNext()){
			int e1= in.nextInt()-1, e2= in.nextInt()-1;
			adjacencyList.get(e1).addNeighbor(adjacencyList.get(e2));
			adjacencyList.get(e2).addNeighbor(adjacencyList.get(e1));
		}
		in.close();
		return adjacencyList;
	}
	
	public static void writeCommunityListToJson(JsonWriter writer,
			Map<Node, List<Node>> communities) throws IOException {
		writer.name("nodes");
		writer.beginArray();
		
		Iterator<Entry<Node, List<Node>>> communitiesEntrySet = communities.entrySet().iterator();
		while(communitiesEntrySet.hasNext()){
			Entry<Node, List<Node>> nextCommunity = communitiesEntrySet.next();
			
			String groupName = nextCommunity.getKey().getStringId();
			
			for (Node node : nextCommunity.getValue()){
				writer.beginObject();
				
				writer.name("id");
				writer.value(node.getStringId());
				
				writer.name("label");
				writer.value(node.getStringId());
				
				writer.name("group");
				writer.value(groupName);
				
				writer.endObject();
			}
		}
		writer.endArray();
	}
	
	public static void writeEdgeListJsonForGraphFile(JsonWriter writer, String filePath) throws IOException{
		writer.name("links");
		writer.beginArray();
		
		Scanner in  = new Scanner(new FileInputStream(filePath));
		while(in.hasNext()){
			int e1= in.nextInt()-1, e2= in.nextInt()-1;
			writer.beginObject();
			
			writer.name("from");
			writer.value(e1);
			
			writer.name("to");
			writer.value(e2);
			
			writer.endObject();
		}
		writer.endArray();
		in.close();
	}

	public static void main(String[] args) throws FileNotFoundException {	
		long startTime = System.currentTimeMillis();
		String filePath = "F:\\temp\\1446410972095.tmp";
		List<Node> adjacencyList = getAdjacencyListForGraphFile(filePath, 34);
		LowDegreeFolloingAlgorithm ldf = new LowDegreeFolloingAlgorithm(adjacencyList);
		Map<Node, List<Node>> communities = ldf.detectCommunities();
		System.out.println(communities.size());
		communities.forEach((key, value) -> {
			System.out.println(key.getId() + " " + value.size());
		});
		System.out.println(System.currentTimeMillis() - startTime);
	}

}
