package com.social.ldf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.collections.CollectionUtils;

import com.social.generic.Edge;
import com.social.generic.Node;

public class LowDegreeFolloingAlgorithm {

	private List<Node> adjacencyList;
	private int leaders, orbiters, members;

	public LowDegreeFolloingAlgorithm(List<Node> adjacencyList) {
		this.adjacencyList = adjacencyList;
	}

	public Map<Node, List<Node>> detectCommunities() {
		Map<Node, List<Node>> communities = new HashMap<>();

		adjacencyList
				.stream()
				.filter(node -> !node.isLeaderOrMember())
				.forEach(
						node -> {
							List<Edge> neighbors = node.getNeighbors();
							Stream<Edge> nonMembers = neighbors.stream()
									.filter(edge -> edge.getOtherEnd()
											.isNotMemberOrOrbiter());
							Optional<Edge> firstEdge = nonMembers.findFirst();
							if (firstEdge.isPresent()) {
								Node otherEnd = firstEdge.get().getOtherEnd();

								if (otherEnd.isLeader()) {
									node.setCommunity(otherEnd.getCommunity());
									communities.get(otherEnd).add(node);
								} else {
									ArrayList<Node> list = new ArrayList<>();
									list.add(node);
									list.add(otherEnd);
									node.setCommunity(otherEnd.getCommunity());
									otherEnd.setCommunity(this.leaders);
									communities.put(otherEnd, list);
									this.leaders++;
								}

								this.members++;
								otherEnd.setLeader(true);
								node.setMember(true);
								node.setParent(otherEnd);
							} else if (CollectionUtils.isNotEmpty(neighbors)) {
								Node otherEnd = neighbors.get(0).getOtherEnd();
								
								communities.get(otherEnd.getParent()).add(node);
								node.setCommunity(otherEnd.getParent().getCommunity());
								
								this.orbiters++;
								node.setOrbiter(true);
								node.setParent(otherEnd);
							}
						});
		return communities;
	}

	public long getLeaders() {
		return leaders;
	}

	public long getOrbiters() {
		return orbiters;
	}

	public long getMembers() {
		return members;
	}

}
