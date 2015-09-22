package com.shan.jgraph;

import java.util.ArrayList;
import java.util.List;

public class Node {

	String id;
	String label;
	Node(String id,String label){
		this.id = id;
		this.label = label;
	}
	
	Node(String num){
		this.id = num;
	}
	
	Node(){
		
	}
	
	String getId(){
		return this.id;
	}
	
	String getLabel(){
		return this.label;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	
}
