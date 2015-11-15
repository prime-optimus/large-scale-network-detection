package com.social.generic;

import com.social.base.Node;

public class LDFNode extends Node{
	public LDFNode(int id) {
		super(id);
	}

	private boolean leader, member, orbiter;
	private LDFNode parent;

	public boolean isLeader() {
		return leader;
	}

	public void setLeader(boolean leader) {
		this.leader = leader;
	}

	public boolean isMember() {
		return member;
	}

	public void setMember(boolean member) {
		this.member = member;
	}

	public boolean isOrbiter() {
		return orbiter;
	}

	public void setOrbiter(boolean orbiter) {
		this.orbiter = orbiter;
	}

	public boolean isLeaderOrMember() {
		return isLeader() || isMember();
	}
	
	public LDFNode getParent() {
		return parent;
	}

	public void setParent(LDFNode parent) {
		this.parent = parent;
	}
	
}
