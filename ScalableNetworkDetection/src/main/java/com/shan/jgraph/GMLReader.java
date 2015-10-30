package com.shan.jgraph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;

public class GMLReader {
	ArrayList<Edge> listOfEdges = new ArrayList<Edge>();
	//LinkedHashMap<Integer,ArrayList<Integer>> listOfConnectedNodes = new LinkedHashMap<Integer,ArrayList<Integer>>();
	List<Integer> listOfNodesInInteger = new ArrayList<Integer>();
	List<Node> listOfNodes = new ArrayList<Node>();
	public GMLReader(String filepath) throws IOException {
		// TODO Auto-generated constructor stub
		readGMLToHashMap(filepath);
	}

	private void readGMLToHashMap(String filepath) throws IOException {
		// TODO Auto-generated method stub
		FileReader f = new FileReader(filepath);
		BufferedReader br = new BufferedReader(f);
		String line;
		
		
		while(!br.readLine().contains("edge")){
			while(!(line = br.readLine()).contains("id"));
			String[] splitArr = line.split(" ");
			Node n = new Node();
			n.setId(Integer.parseInt(splitArr[splitArr.length-1]));
			line = br.readLine();
			splitArr = line.split(" ");
			n.setLabel(Integer.parseInt(splitArr[splitArr.length-1]));
			listOfNodes.add(new Node(n.getId(),n.getLabel()));
				br.readLine();
			}
		while(!br.readLine().contains("]")){
			while(!(line = br.readLine()).contains("source"));
			String[] splitArr = line.split(" ");
			Edge e = new Edge();
			e.setStartingNode(getFromListOfNodes(listOfNodes,Integer.parseInt(splitArr[splitArr.length-1])));
			line = br.readLine();
			splitArr = line.split(" ");
			e.setEndingNode(getFromListOfNodes(listOfNodes,Integer.parseInt(splitArr[splitArr.length-1])));
			line = br.readLine();
			e.setEdgeWeight(Integer.parseInt(line.trim()));
			listOfEdges.add(e);
				br.readLine();
		}
		//System.out.println("end");
		
		ListIterator<Node> litr = listOfNodes.listIterator();
		while(litr.hasNext()){
			Node n = litr.next();
			//containsNode(listOfConnectedNodes, node1, node2);
			if(!listOfNodesInInteger.contains(n.getId())){
				listOfNodesInInteger.add(n.getId());
			}
		}
		/*for(Entry<Node,ArrayList<Node>> entry: listOfConnectedNodes.entrySet()){
			ListIterator<Node> lItr = entry.getValue().listIterator();
			System.out.println(entry.getKey().getId());
			while(lItr.hasNext()){
				System.out.print(lItr.next().getId());
			}
			System.out.println();
		}*/
		}

	private Node getFromListOfNodes(List<Node> listOfNodes2, int num) {
		// TODO Auto-generated method stub
		ListIterator<Node> litr5 = listOfNodes2.listIterator();
		Node temp = null;
		while(litr5.hasNext() && !((temp = litr5.next()).getId() == num)){}
		return temp;
	}

	/*private void containsNode(
			LinkedHashMap<Integer, ArrayList<Integer>> listOfConnectedNodes2,
			Node node1,Node node2) {
		// TODO Auto-generated method stub
		if(listOfConnectedNodes2.containsKey(node1.getId()) && listOfConnectedNodes2.containsKey(node2.getId())){
			listOfConnectedNodes2.get(node1.getId()).add(node2.getId());
			listOfConnectedNodes2.get(node2.getId()).add(node1.getId());
		}
		if(listOfConnectedNodes2.containsKey(node1.getId())){
			listOfConnectedNodes2.get(node1.getId()).add(node2.getId());
			ArrayList<Integer> newList2 = new ArrayList<Integer>();
			newList2.add(node1.getId());
			listOfConnectedNodes2.put(node2.getId(), newList2);
		}	
		else if(listOfConnectedNodes2.containsKey(node2.getId())){
			listOfConnectedNodes2.get(node2.getId()).add(node1.getId());
			ArrayList<Integer> newList1 = new ArrayList<Integer>();
			newList1.add(node2.getId());
			listOfConnectedNodes2.put(node1.getId(), newList1);
		}
		else{
			ArrayList<Integer> newList1 = new ArrayList<Integer>();
					newList1.add(node2.getId());
			ArrayList<Integer> newList2 = new ArrayList<Integer>();
					newList2.add(node1.getId());
			listOfConnectedNodes2.put(node1.getId(), newList1);
			listOfConnectedNodes2.put(node2.getId(), newList2);
		}
	}*/

	public ArrayList<Edge> getListOfEdges() {
		return listOfEdges;
	}

	public void setListOfEdges(ArrayList<Edge> listOfEdges) {
		this.listOfEdges = listOfEdges;
	}

	public List<Node> getListOfNodes() {
		return listOfNodes;
	}

	public void setListOfNodes(List<Node> listOfNodes) {
		this.listOfNodes = listOfNodes;
	}

	public List<Integer> getListOfNodesInInteger() {
		return listOfNodesInInteger;
	}

	public void setListOfNodesInInteger(List<Integer> listOfNodesInInteger) {
		this.listOfNodesInInteger = listOfNodesInInteger;
	}
		
		
	}

