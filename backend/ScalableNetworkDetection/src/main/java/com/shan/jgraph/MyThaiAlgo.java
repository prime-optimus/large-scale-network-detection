package com.shan.jgraph;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

import com.sun.jna.platform.win32.WinBase.SYSTEMTIME;

import sun.text.normalizer.UBiDiProps;
import jxl.read.biff.BiffException;

public class MyThaiAlgo {

	public static List<Node> subtractList(List<Node> tempList, ArrayList<Node> members){
		ListIterator<Node> litr = tempList.listIterator();
		int i = 0;
		while(litr.hasNext()){
			if(members.contains(litr.next())){
				tempList.set(i, new Node(String.valueOf(-1)));
				//litr.remove();
			}
			i++;
		}
		return tempList;
	}
	
	public static List<Edge> subtractListOfEdges(List<Edge> tempList, ArrayList<Node> members){
		ListIterator<Edge> litr = tempList.listIterator();
		List<Edge> subList = new ArrayList<Edge>();
		int i = 0;
		Edge nextEdge;
		while(litr.hasNext()){
			nextEdge = litr.next();
			if(!containsNodeinNodesList(members,nextEdge.getEndingNode())){
				subList.add(nextEdge);
			}
			i++;
		}
		return subList;
	}
	/**
	 * @param args
	 * @throws IOException 
	 * @throws BiffException 
	 */
	public static void main(String[] args) throws BiffException, IOException {
		
		SolveGraphByAdjacencyList();
		//SolveGraphAsAdjacencyMatrix();
		//SolveGraphAsHashMap();
		// TODO Auto-generated method stub
		
	}
	private static void SolveGraphAsAdjacencyMatrix() throws BiffException, IOException {
		// TODO Auto-generated method stub
		GraphByAdjacencyMatrix g1 = new GraphByAdjacencyMatrix();
		int d = 20;
		ArrayList<Node> leaders = new ArrayList<Node>();
		ArrayList<Node> members = new ArrayList<Node>();
		ArrayList<Node> orbiters = new ArrayList<Node>();
		Map<Node,ArrayList<Node>> leadersAndMembers = new HashMap<Node,ArrayList<Node>>();
		Map<Node,ArrayList<Node>> communities = new HashMap<Node,ArrayList<Node>>();
		Node endingNode;
		Node startingNode;
		Edge tempEdge;
		List<Edge> tempList;
		List<Edge> reducedTempList;
		ListIterator<List<Edge>> litr1 = g1.getGraphAsListOfNodes().listIterator();
		while(litr1.hasNext()){
			tempList = returnTempList(litr1.next());
			ListIterator<Edge> litr2 = tempList.listIterator();
			if(litr2.hasNext()) {
				tempEdge = litr2.next();
				endingNode = tempEdge.getEndingNode();
				startingNode = tempEdge.getStartingNode();
				if((!containsNodeinNodesList(members,startingNode) && !containsNodeinNodesList(leaders,startingNode))){
					reducedTempList = subtractListOfEdges(tempList,members);
					endingNode = reducedTempList.get(0).getEndingNode();
					if(!reducedTempList.isEmpty()){
						members.add(startingNode);
						communities = containsNodeinCommunity(communities,endingNode,startingNode);
						//if(!containsNode(leaders,endingNode)){
							//leaders.add(endingNode);
						//}							
					} else {
						if(!containsNodeinNodesList(orbiters,startingNode))
							orbiters.add(startingNode);
						if(!containsNodeinNodesList(members,endingNode))
							members.add(endingNode);
					}
					
				}
			}
			
		
		}
		Iterator<Node> mItr = members.iterator();
		Iterator<Node> lItr = leaders.iterator();
		Iterator<Node> oItr = orbiters.iterator();
		
		for(Entry<Node,ArrayList<Node>> entry:communities.entrySet()){
			System.out.println(entry.getKey().getLabel() + " adjacent to ");
			Iterator<Node> adjacentNodes = entry.getValue().iterator();
			while(adjacentNodes.hasNext()){
				System.out.println(adjacentNodes.next().getLabel());
			}
		}
		System.out.println("Members");
		while(mItr.hasNext()){
			System.out.println(mItr.next().getLabel());
		}
		System.out.println("Leaders");
		while(lItr.hasNext()){
			System.out.println(lItr.next().getLabel());
		}
		System.out.println("Orbiters");
		while(oItr.hasNext()){
			System.out.println(oItr.next().getLabel());
		}

		
	}
	
	private static boolean containsNodeinNodesList(ArrayList<Node> members,
			Node startingNode) {
		// TODO Auto-generated method stub
		return false;
	}

	private static void SolveGraphByAdjacencyList() throws BiffException, IOException{

		// TODO Auto-generated method stub
		//GraphByAdjacencyMatrix g1 = new GraphByAdjacencyMatrix();
		long startTime = System.currentTimeMillis();
		GraphByAdjacencyList g1 = new GraphByAdjacencyList();
		LinkedHashMap<String, ArrayList<String>> mapOfNodes = g1.getMapOfNodes();
		LinkedHashMap<String,String> parents = new LinkedHashMap<String,String>();
		//System.out.println(mapOfNodes.size());
		int d = 20;
		ArrayList<String> leaders = new ArrayList<String>();
		ArrayList<String> members = new ArrayList<String>();
		ArrayList<String> orbiters = new ArrayList<String>();
		ArrayList<Edge> listOfEdges = g1.getListOfEdges();
		ArrayList<String> tempList;
		for(Entry<String, ArrayList<String>> entry: mapOfNodes.entrySet()){
			tempList = entry.getValue();
			ListIterator<String> litr2 = tempList.listIterator();
			if(litr2.hasNext()) {
				String startingNode = entry.getKey();
				if(!leaders.contains(startingNode) && !members.contains(startingNode)) {
					String endingNode = subtractListNodes(tempList, members);
					if(!endingNode.isEmpty()){
						members.add(startingNode);
						if(!leaders.contains(endingNode))
						leaders.add(endingNode);						
					} else {
						endingNode = tempList.get(0);
						orbiters.add(startingNode);
					}
					parents.put(startingNode, endingNode);
				}
			}
			
		
		}
		
		ArrayList<String> communities = new ArrayList<String>();
		//System.out.println("Leaders");
		int i = 0;
		ListIterator<String> leadItr = leaders.listIterator();
		ListIterator<String> memItr = members.listIterator();
		ListIterator<String> orbItr = orbiters.listIterator();
		while(leadItr.hasNext()){			
			String leader = leadItr.next();
			communities.add("\t\t{\"name\":\""+leader+"\",\"group\":"+i+"},");
			//System.out.println(leader);
			i++;
		}
		//System.out.println("Members");
		while(memItr.hasNext()){
			String mNode = memItr.next();
				String group = String.valueOf(leaders.indexOf(parents.get(mNode)));
					communities.add("\t\t{\"name\":\""+mNode+"\",\"group\":"+group+"},");
					//System.out.println(mNode);
		}
		//System.out.println("Orbiters");
		FileWriter fw = new FileWriter(new File("myfbnet.json"));
		BufferedWriter bw = new BufferedWriter(fw);
		while(orbItr.hasNext()){
			String oNode = orbItr.next();
			String group = String.valueOf(leaders.indexOf(parents.get(parents.get(oNode))));
			communities.add("\t\t{\"name\":\""+oNode+"\",\"group\":"+group+"},");
			//System.out.println(oNode);
		}
		ListIterator<String> commItr = communities.listIterator();
		//System.out.println("{");
		//System.out.println("\t\"nodes\":[");
		bw.write("{"+"\n");
		bw.write("\t\"nodes\":["+"\n");
		while(commItr.hasNext()){
			//System.out.println("\t"+commItr.next());
			bw.write("\t"+commItr.next()+"\n");
		}
		//System.out.println("\t],");
		//System.out.println("\t\"links\":[");
		bw.write("\t],"+"\n");
		bw.write("\t\"links\":["+"\n");
		ListIterator<Edge> edgeItr = listOfEdges.listIterator();
		while(edgeItr.hasNext()){
			Edge e = edgeItr.next();
			//System.out.println("\t\t{\"source\":"+e.getStartingNode().getId()+",\"target\":"+e.getEndingNode().getId()+",\"value\":"+e.getEdgeWeight()+"},");
			bw.write("\t\t{\"source\":"+e.getStartingNode().getId()+",\"target\":"+e.getEndingNode().getId()+",\"value\":"+e.getEdgeWeight()+"},"+"\n");
		}
		//System.out.println("\t],");
		//System.out.println("}");
		bw.write("\t],"+"\n");
		bw.write("}"+"\n");
		bw.close();
		System.out.println(System.currentTimeMillis() - startTime);
	}
	private static Node getNodeFromList(ArrayList<Node> listOfNodes,
			String endingNode) {
		// TODO Auto-generated method stub
		ListIterator<Node> nodeLitr = listOfNodes.listIterator();
		Node tempNode = null;
		while(nodeLitr.hasNext() && !(tempNode = nodeLitr.next()).getId().equals(endingNode)){}
		return tempNode;
	}

	private static int indexOfNode(ArrayList<Node> leaders, Node node) {
		// TODO Auto-generated method stub
		Iterator<Node> memIterator = leaders.iterator();
		int i = 0;
		while(memIterator.hasNext()){
			Node mNode = memIterator.next();
			if(node.getId().equals(mNode.getId())){
				return i;
			}
			i++;
		}
		return -1;
	}

	private static String subtractListNodes(ArrayList<String> tempList,
			ArrayList<String> members) {
		String reduced = "";
		ListIterator<String> litr = tempList.listIterator();
		ArrayList<String> reducedTempList = new ArrayList<String>();
		int i = 0;
		String nextNode;
		while(litr.hasNext()){
			nextNode = litr.next();
			if(!members.contains(nextNode)){
				reduced = nextNode;
				break;
			}
			i++;
		}
		return reduced;
	}

	private static ArrayList<Integer> subtractListIntegers(
			ArrayList<Integer> tempList, LinkedHashMap<Integer,Integer> members) {
		// TODO Auto-generated method stub
		ListIterator<Integer> litr = tempList.listIterator();
		ArrayList<Integer> reducedTempList = new ArrayList<Integer>();
		int i = 0;
		int nextNode;
		while(litr.hasNext()){
			nextNode = litr.next();
			if(!members.containsKey(nextNode)){
				reducedTempList.add(nextNode);
			}
			i++;
		}
		return reducedTempList;
	}

	private static Map<Node, ArrayList<Node>> containsNodeinCommunity(Map<Node,ArrayList<Node>> communities,
			Node endingNode,Node startingNode) {
		// TODO Auto-generated method stub
		ArrayList<Node> adjacentNodesToLeader = communities.get(endingNode);
		if(null != adjacentNodesToLeader){
			adjacentNodesToLeader.add(startingNode);
		} else {
			ArrayList<Node> newAdjacentNodesToLeader = new ArrayList<Node>();
			newAdjacentNodesToLeader.add(startingNode);
			communities.put(endingNode, newAdjacentNodesToLeader);
		}	
		return communities;
	}

	private static boolean containsNode(ArrayList<Node> members,
			String startingNode) {
		// TODO Auto-generated method stub
		Iterator<Node> memIterator = members.iterator();
		while(memIterator.hasNext()){
			Node mNode = memIterator.next();
			if(startingNode.equals(mNode.getId())){
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("null")
	private static List<Edge> returnTempList(List<Edge> tempList) {
		// TODO Auto-generated method stub
		int i = 0;
		List<Edge> usableTempList = new ArrayList<Edge>();
		ListIterator<Edge> litr = tempList.listIterator();
		while(litr.hasNext()){
			Edge e = litr.next();
			if(String.valueOf(0) != e.getEdgeWeight()){
				usableTempList.add(e);
				i++;
			}
				
		}
		return usableTempList;
	}

	private static void InputGraphAsHashMap(Graph g1) {
		// TODO Auto-generated method stub
		ArrayList<Node> listOfNodes = new ArrayList<Node>();
		for(int i = 0 ;i < 6;i++){
			listOfNodes.add(new Node());
		}
		g1.fillAdjacencyList(listOfNodes.get(0), Arrays.asList(listOfNodes.get(1),listOfNodes.get(2),listOfNodes.get(3),listOfNodes.get(5)));
		g1.fillAdjacencyList(listOfNodes.get(1), Arrays.asList(listOfNodes.get(0)));
		g1.fillAdjacencyList(listOfNodes.get(2), Arrays.asList(listOfNodes.get(0)));
		g1.fillAdjacencyList(listOfNodes.get(3), Arrays.asList(listOfNodes.get(0),listOfNodes.get(4)));
		g1.fillAdjacencyList(listOfNodes.get(4), Arrays.asList(listOfNodes.get(3)));
		g1.fillAdjacencyList(listOfNodes.get(5), Arrays.asList(listOfNodes.get(0)));
	}
	private static void SolveGraphAsHashMap() {
		// TODO Auto-generated method stub
		Graph g1 = new Graph();
		int d = 2;
		InputGraphAsHashMap(g1);
		ArrayList<Node> leaders = new ArrayList<Node>();
		ArrayList<Node> members = new ArrayList<Node>();
		ArrayList<Node> orbiters = new ArrayList<Node>();
		Node tempNode;
		List<Node> tempList;
		List<Node> reducedTempList;
		for(Entry <Node,List<Node>> entry : g1.entrySet()){
			tempNode = entry.getKey();
			tempList = entry.getValue();
			if(tempList.size() <= d && (!leaders.contains(tempNode) || !members.contains(tempNode))){
				reducedTempList = subtractList(tempList,members);
				if(String.valueOf(-1) != reducedTempList.get(reducedTempList.size()-1).getLabel()){
					members.add(tempNode);
					leaders.add(tempList.get(0));
				} else {
					orbiters.add(tempNode);
					members.add(tempList.get(0));
				}
				
			}
		}
		Iterator<Node> mItr = members.iterator();
		Iterator<Node> lItr = leaders.iterator();
		Iterator<Node> oItr = orbiters.iterator();
		System.out.println("Members");
		while(mItr.hasNext()){
			System.out.println(mItr.next().getLabel());
		}
		System.out.println("Leaders");
		while(lItr.hasNext()){
			System.out.println(lItr.next().getLabel());
		}
		System.out.println("Orbiters");
		while(oItr.hasNext()){
			System.out.println(oItr.next().getLabel());
		}
	}

}
