package com.social.ldf;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.collections.CollectionUtils;

import com.social.base.CommunityAlgorithm;
import com.social.base.Edge;
import com.social.base.Node;
import com.social.generic.LDFNode;

public class LowDegreeFollowingAlgorithm extends CommunityAlgorithm {
	private int leaders, orbiters, members;

	@Override
	public int detectCommunities(List<Node> adjacencyList) {
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
									node.setCommunity(otherEnd.getCommunity());	
									//communities.get(otherEnd).add(node);
								} else {
									ArrayList<Node> list = new ArrayList<>();
									list.add(node);
									list.add(otherEnd);
									
									node.setCommunity(otherEnd.getCommunity());
									otherEnd.setCommunity(this.leaders);
									
									//communities.put(otherEnd, list);
									this.leaders++;
								}

								this.members++;
								otherEnd.setLeader(true);
								node.setMember(true);
								node.setParent(otherEnd);
							} else if (CollectionUtils.isNotEmpty(neighbors)) {
								LDFNode otherEnd = (LDFNode) neighbors.get(0).getOtherEnd();
								//communities.get(otherEnd.getParent()).add(node);
								node.setCommunity(otherEnd.getParent().getCommunity());
								
								this.orbiters++;
								node.setOrbiter(true);
								node.setParent(otherEnd);
							}
						});
		return leaders;
	}

	@Override
	public Class<? extends Node> getNodeClass() {
		return LDFNode.class;
	}

	private boolean isOtherEndMember(Edge edge) {
		return ((LDFNode)edge.getOtherEnd()).isMember();
	}

	public int getLeaders() {
		return leaders;
	}

	public int getOrbiters() {
		return orbiters;
	}

	public int getMembers() {
		return members;
	}

}
