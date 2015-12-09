package com.social.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

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
			int e1= in.nextInt(), e2= in.nextInt();
			adjacencyList.get(e1).addNeighbor(adjacencyList.get(e2));
			adjacencyList.get(e2).addNeighbor(adjacencyList.get(e1));
		}
		in.close();
		return adjacencyList;
	}
	
	
	
	public static void writeCommunityList(JsonWriter writer, List<Node> communities) throws IOException {
		writer.name("nodes");
		writer.beginArray();
		
		communities.forEach(node -> {
			writeCommunityNodeObject(writer, node.getStringId());
		});
		writer.endArray();
	}
	
	public static void writeEdgeList(JsonWriter writer,	List<Node> communities) throws IOException {
		writer.name("links");
		writer.beginArray();
		
		communities.forEach(node -> {
			node.getNeighbors().forEach(neighbor -> {
				writeCommunityEdgeObject(writer, node.getStringId(), neighbor.getOtherEnd().getStringId());
			});
		});
		
		writer.endArray();
	}
	
	private static void writeCommunityNodeObject(JsonWriter writer, String id){
		try {
			writer.beginObject();
			writer.name("id");
			writer.value(id);
			
			writer.name("label");
			writer.value(id);
			
			writer.name("group");
			writer.value(id);
			
			writer.endObject();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void writeCommunityEdgeObject(JsonWriter writer, String from, String to){
		try {
			writer.beginObject();
			
			writer.name("from");
			writer.value(from);
			
			writer.name("to");
			writer.value(to);
			
			writer.endObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			int e1= in.nextInt(), e2= in.nextInt();
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

	public static void main(String[] args) throws IOException {	
		long startTime = System.currentTimeMillis();
		String filePath = "F:\\temp\\1449437294103.tmp";
		List<Node> adjacencyList = getAdjacencyListForGraphFile(filePath, 34);
		
		adjacencyList.forEach(node -> {
			System.out.print(node.getId() + " :: ");
			node.getNeighbors().forEach(edge -> {
				System.out.print(edge.getOtherEnd().getId() + " ");
			});
			System.out.println();
		});
		
		LowDegreeFolloingAlgorithm ldf = new LowDegreeFolloingAlgorithm(adjacencyList);
		Map<Node, List<Node>> communities = ldf.detectCommunities();
		
		System.out.println(communities.size());
		
		communities.forEach((key, value) -> {
			System.out.println(key.getId() + " " + value.size());
		});
		
		List<Node> openCommunity = GraphCompressionUtils.openCommunity(communities, adjacencyList, 33);
		
		System.out.println(openCommunity.size());
		openCommunity.forEach(node -> {
			System.out.print(node.getId() + " :: ");
			node.getNeighbors().forEach(edge -> {
				System.out.print(edge.getOtherEnd().getId() + " ");
			});
			System.out.println();
		});
		
		
		/*List<Node> compressedCommunity = compressCommunityRepresentation(adjacencyList, communities.size());
		JsonWriter writer = new JsonWriter(new PrintWriter(System.out));
		//writer.setIndent(" ");
		writer.beginObject();
		writeCommunityList(writer, compressedCommunity);
		writeEdgeList(writer, compressedCommunity);
		writer.endObject();
		writer.close();*/
		
		System.out.println(System.currentTimeMillis() - startTime);
	}

	
}
