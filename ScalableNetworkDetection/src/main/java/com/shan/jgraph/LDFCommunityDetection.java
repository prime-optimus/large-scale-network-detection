package com.shan.jgraph;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import jxl.read.biff.BiffException;

public class LDFCommunityDetection {

	static Map<Integer, Integer> parents = new LinkedHashMap<Integer, Integer>();
	static int runningJSONVar = 0;

	/**
	 * @param args
	 * @throws IOException
	 * @throws BiffException
	 */
	public static void main(String[] args) throws BiffException, IOException {

		//SolveGraphByAdjacencyList();
		// TODO Auto-generated method stub

	}

	public static String SolveGraphByAdjacencyList(String fileName) throws BiffException,
			IOException {

		// TODO Auto-generated method stub
		//long startTime = System.currentTimeMillis();
		GraphByAdjacencyList g1 = new GraphByAdjacencyList(fileName);
		List<Integer> listOfNodes = g1.getListOfNodes();

		List<Edge> listOfEdges = g1.getListOfEdges();
		return LDFLabelNodes(listOfEdges, listOfNodes);
		//System.out.println(System.currentTimeMillis() - startTime);
	}

	private static String LDFLabelNodes(List<Edge> listOfEdges,
			List<Integer> listOfNodes) throws IOException {
		// TODO Auto-generated method stub

		List<Integer> leaders = new ArrayList<Integer>();
		List<Integer> members = new ArrayList<Integer>();
		List<Integer> orbiters = new ArrayList<Integer>();
		
		List<Integer> tempList;
		ListIterator<Integer> nodeItr = listOfNodes.listIterator();
		int d = 20;
		while (nodeItr.hasNext()) {
			int consideredNode = nodeItr.next();
			tempList = getListOfNodesConnected(consideredNode, listOfEdges);
			ListIterator<Integer> litr2 = tempList.listIterator();
			if (litr2.hasNext()) {
				int startingNode = consideredNode;
				if (!leaders.contains(startingNode)
						&& !members.contains(startingNode)) {
					int endingNode = subtractListNodes(tempList, members);
					if (!(endingNode == 0)) {
						members.add(startingNode);
						if (!leaders.contains(endingNode))
							leaders.add(endingNode);
					} else {
						endingNode = tempList.get(0);
						orbiters.add(startingNode);
					}
					parents.put(startingNode, endingNode);
				}
			}

		}
		return CreateCommunities(listOfEdges, leaders, members, orbiters);
	}

	private static String CreateCommunities(List<Edge> listOfEdges,
			List<Integer> leaders, List<Integer> members, List<Integer> orbiters)
			throws IOException {
		// TODO Auto-generated method stub
		Map<Integer, Integer> communities = new HashMap<Integer, Integer>();
		int i = 0;
		ListIterator<Integer> leadItr = leaders.listIterator();
		ListIterator<Integer> memItr = members.listIterator();
		ListIterator<Integer> orbItr = orbiters.listIterator();

		ArrayList<Edge> compressedEdgeList = new ArrayList<Edge>();
		List<Integer> compressedNodesList = new ArrayList<Integer>();
		while (leadItr.hasNext()) {
			Integer leader = leadItr.next();
			communities.put(leader, i);
			i++;
		}
		while (memItr.hasNext()) {
			Integer mNode = memItr.next();
			int group = leaders.indexOf(parents.get(mNode));
			communities.put(mNode, group);
		}
		while (orbItr.hasNext()) {
			Integer oNode = orbItr.next();
			int group = leaders.indexOf(parents.get(parents.get(oNode)));
			communities.put(oNode, group);
		}
		return WriteCommunitiesInJSON(listOfEdges, communities, runningJSONVar);
		
		/*if (i > 100) {
			for (Entry<Integer, Integer> entry : communities.entrySet()) {
				ListIterator<Integer> connectedNodesItr = getListOfNodesConnected(
						entry.getKey(), listOfEdges).listIterator();
				while (connectedNodesItr.hasNext()) {
					int connected = connectedNodesItr.next();
					int node1 = entry.getValue();
					int node2 = 0;
					if (communities.containsKey(connected)) {
						node2 = communities.get(connected);
						Edge e1 = containsEdge(compressedEdgeList, node1, node2);
						if (null != e1) {
							e1.setEdgeWeight(e1.getEdgeWeight() + 1);
						} else {
							e1 = new Edge(new Node(entry.getValue()), new Node(
									communities.get(connected)), 1);
							ArrayList<Integer> listOfConnectedComms = new ArrayList<Integer>();
							listOfConnectedComms
									.add(communities.get(connected));
							compressedEdgeList.add(e1);
						}
					}
					if (!compressedNodesList.contains(node1)) {
						compressedNodesList.add(node1);
					}
					if (!compressedNodesList.contains(node2)) {
						compressedNodesList.add(node2);
					}

				}
			}
			runningJSONVar++;
			LDFLabelNodes(compressedEdgeList, compressedNodesList);
		}*/

	}

	private static String WriteCommunitiesInJSON(List<Edge> listOfEdges,
			Map<Integer, Integer> communities, int i) throws IOException {
		// TODO Auto-generated method stub

		StringWriter fw = new StringWriter();
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write("{" + "\n");
		bw.write("\t\"nodes\":[" + "\n");

		Set<Entry<Integer, Integer>> set = communities.entrySet();
		List<Entry<Integer, Integer>> list = new ArrayList<Entry<Integer, Integer>>(
				set);
		/*Collections.sort(list, new Comparator<Map.Entry<Integer, Integer>>() {
			public int compare(Map.Entry<Integer, Integer> o1,
					Map.Entry<Integer, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});*/
		int mapItr = 0;
		for (Entry<Integer, Integer> entry : list) {
			if (mapItr < list.size() - 1) {
				bw.write("\t\t{\"id\":" + (entry.getKey()-1) + ",\"name\":"
						+ (entry.getKey()-1) + ",\"group\":" + entry.getValue()
						+ "}," + "\n");
			} else {
				bw.write("\t\t{\"id\":" + (entry.getKey()-1) + ",\"name\":"
						+ (entry.getKey()-1) + ",\"group\":" + entry.getValue()
						+ "}" + "\n");
			}

			mapItr++;
		}
		bw.write("\t]," + "\n");
		bw.write("\t\"links\":[" + "\n");

		ListIterator<Edge> edgeItr = listOfEdges.listIterator();
		while (edgeItr.hasNext()) {
			Edge e = edgeItr.next();
			if (edgeItr.nextIndex() < listOfEdges.size()) {
				bw.write("\t\t{\"source\":" + (e.getStartingNode().getId()-1)
						+ ",\"target\":" + (e.getEndingNode().getId()-1) + "},"
						+ "\n");
			} else {
				bw.write("\t\t{\"source\":" + (e.getStartingNode().getId()-1)
						+ ",\"target\":" + (e.getEndingNode().getId()-1) + "}"
						+ "\n");
			}

		}
		bw.write("\t]" + "\n");
		bw.write("}" + "\n");
		bw.write("");
		bw.close();
		return fw.toString();
	}

	private static Edge containsEdge(ArrayList<Edge> compressedEdgeList,
			Integer value, Integer integer) {
		// TODO Auto-generated method stub
		ListIterator<Edge> compressedEdgeListItr = compressedEdgeList
				.listIterator();
		while (compressedEdgeListItr.hasNext()) {
			Edge e1 = compressedEdgeListItr.next();
			if (e1.representativeFunction(value, integer)) {
				return e1;
			}
		}
		return null;
	}

	private static int subtractListNodes(List<Integer> tempList,
			List<Integer> members2) {
		int reduced = 0;
		ListIterator<Integer> litr = tempList.listIterator();
		int i = 0;
		int nextNode;
		while (litr.hasNext()) {
			nextNode = litr.next();
			if (!members2.contains(nextNode)) {
				reduced = nextNode;
				break;
			}
			i++;
		}
		return reduced;
	}

	private static List<Integer> getListOfNodesConnected(int consideredNode,
			List<Edge> listOfEdges) {
		// TODO Auto-generated method stub
		List<Integer> connectedNodes = new ArrayList<Integer>();
		ListIterator<Edge> edgeItr = listOfEdges.listIterator();
		while (edgeItr.hasNext()) {
			Edge e1 = edgeItr.next();
			if (e1.getEndingNode().getId() == consideredNode) {
				connectedNodes.add(e1.getStartingNode().getId());
			} else if (e1.getStartingNode().getId() == consideredNode) {
				connectedNodes.add(e1.getEndingNode().getId());
			}
		}
		return connectedNodes;
	}

}

class ValueComparator implements Comparator {

	Map map;

	public ValueComparator(Map map) {
		this.map = map;
	}

	public int compare(Object keyA, Object keyB) {
		Comparable valueA = (Comparable) map.get(keyA);
		Comparable valueB = (Comparable) map.get(keyB);
		return valueB.compareTo(valueA);
	}

	public Comparator reversed() {
		// TODO Auto-generated method stub
		return null;
	}

	public Comparator thenComparing(Comparator other) {
		// TODO Auto-generated method stub
		return null;
	}

	public Comparator thenComparing(Function keyExtractor,
			Comparator keyComparator) {
		// TODO Auto-generated method stub
		return null;
	}

	public Comparator thenComparing(Function keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

	public Comparator thenComparingInt(ToIntFunction keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

	public Comparator thenComparingLong(ToLongFunction keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

	public Comparator thenComparingDouble(ToDoubleFunction keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

	public static <T extends Comparable<? super T>> Comparator<T> reverseOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	public static <T extends Comparable<? super T>> Comparator<T> naturalOrder() {
		// TODO Auto-generated method stub
		return null;
	}

	public static <T> Comparator<T> nullsFirst(Comparator<? super T> comparator) {
		// TODO Auto-generated method stub
		return null;
	}

	public static <T> Comparator<T> nullsLast(Comparator<? super T> comparator) {
		// TODO Auto-generated method stub
		return null;
	}

	public static <T, U> Comparator<T> comparing(
			Function<? super T, ? extends U> keyExtractor,
			Comparator<? super U> keyComparator) {
		// TODO Auto-generated method stub
		return null;
	}

	public static <T, U extends Comparable<? super U>> Comparator<T> comparing(
			Function<? super T, ? extends U> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

	public static <T> Comparator<T> comparingInt(
			ToIntFunction<? super T> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

	public static <T> Comparator<T> comparingLong(
			ToLongFunction<? super T> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}

	public static <T> Comparator<T> comparingDouble(
			ToDoubleFunction<? super T> keyExtractor) {
		// TODO Auto-generated method stub
		return null;
	}
}
