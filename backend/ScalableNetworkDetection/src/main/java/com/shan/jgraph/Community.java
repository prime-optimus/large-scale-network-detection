package com.shan.jgraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Community implements Map{
	
	Node leader;
	ArrayList<Node> membersAndOrbiters = new ArrayList<Node>();
	public Node getLeader() {
		return leader;
	}
	public void setLeader(Node leader) {
		this.leader = leader;
	}
	public ArrayList<Node> getMembersAndOrbiters() {
		return membersAndOrbiters;
	}
	public void setMembersAndOrbiters(ArrayList<Node> membersAndOrbiters) {
		this.membersAndOrbiters = membersAndOrbiters;
	}
	Community(Node leader){
		this.leader = leader;
	};
	
	Community(){};
	public Community addMembersAndOrbiters(Node m){
		membersAndOrbiters.add(m);
		return this;
	}
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Object compute(Object arg0, BiFunction arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object computeIfAbsent(Object arg0, Function arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object computeIfPresent(Object arg0, BiFunction arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean containsKey(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean containsValue(Object arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Set entrySet() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void forEach(BiConsumer arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Object get(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object getOrDefault(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Set keySet() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object merge(Object arg0, Object arg1, BiFunction arg2) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object put(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		
		return this;
	}
	@Override
	public void putAll(Map arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Object putIfAbsent(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Object remove(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean remove(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Object replace(Object arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean replace(Object arg0, Object arg1, Object arg2) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void replaceAll(BiFunction arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Collection values() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
