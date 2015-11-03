package com.social.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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

	public static void main(String[] args) throws FileNotFoundException {	
		long startTime = System.currentTimeMillis();
		String filePath = "F:\\temp\\soc-Epinions1.txt";
		List<Node> adjacencyList = getAdjacencyListForGraphFile(filePath, 75888);
		LowDegreeFolloingAlgorithm ldf = new LowDegreeFolloingAlgorithm(adjacencyList);
		Map<Node, List<Node>> communities = ldf.detectCommunities();
		System.out.println(communities.size());
		communities.forEach((key, value) -> {
			System.out.println(key.getId() + " " + value.size());
		});
		System.out.println(System.currentTimeMillis() - startTime);

	}

}
