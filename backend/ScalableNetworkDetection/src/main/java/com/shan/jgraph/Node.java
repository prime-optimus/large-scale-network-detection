package com.shan.jgraph;

import java.util.ArrayList;
import java.util.List;

public class Node {

	int id;
	int label;
	Node(int id,int label){
		this.id = id;
		this.label = label;
	}
	
	Node(int num){
		this.id = num;
	}
	
	Node(){
		
	}
	
	int getId(){
		return this.id;
	}
	
	int getLabel(){
		return this.label;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setLabel(int label) {
		this.label = label;
	}
	
	
}
