package com.social.ldf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.collections.CollectionUtils;

import com.social.base.CommunityAlgorithm;
import com.social.base.Edge;
import com.social.base.Node;
import com.social.generic.LDFNode;

public class LowDegreeFollowingAlgorithm extends CommunityAlgorithm {
	private long leaders, orbiters, members;

	@Override
	public Map<Node, List<Node>> detectCommunities(List<Node> adjacencyList) {
		Map<Node, List<Node>> communities = new HashMap<>();
		
		adjacencyList
				.stream()
				.map(node -> (LDFNode)node)
				.filter(node -> !node.isLeaderOrMember())
				.forEach(
						node -> {
							List<Edge> neighbors = node.getNeighbors();
							Stream<Edge> nonMembers = neighbors.stream()
									.filter(edge -> !isOtherEndMember(edge));
							Optional<Edge> firstEdge = nonMembers.findFirst();
							if (firstEdge.isPresent()) {
								LDFNode otherEnd = (LDFNode) firstEdge.get().getOtherEnd();

								if (otherEnd.isLeader()) {
									communities.get(otherEnd).add(node);
								} else {
									this.leaders++;
									ArrayList<Node> list = new ArrayList<>();
									list.add(node);
									list.add(otherEnd);
									communities.put(otherEnd, list);
								}

								this.members++;
								otherEnd.setLeader(true);
								node.setMember(true);
								node.setParent(otherEnd);
							} else if (CollectionUtils.isNotEmpty(neighbors)) {
								LDFNode otherEnd = (LDFNode) neighbors.get(0).getOtherEnd();
								communities.get(otherEnd.getParent()).add(node);

								this.orbiters++;
								node.setOrbiter(true);
								node.setParent(otherEnd);
							}
						});
		return communities;
	}

	@Override
	public Class<? extends Node> getNodeClass() {
		return LDFNode.class;
	}

	private boolean isOtherEndMember(Edge edge) {
		return ((LDFNode)edge.getOtherEnd()).isMember();
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
