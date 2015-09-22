package com.shan.jgraph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class CliqueConnect {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String filepath = "C:\\Users\\SHANDEMETZ\\Downloads\\Compressed\\all_gene_disease_associations.txt\\all_gene_disease_associations.txt";
		FileReader f = new FileReader(filepath);
		  BufferedReader br = new BufferedReader(f);
		  FileWriter fw = new FileWriter(new File("gene_disease_network.txt"));
		  BufferedWriter bw = new BufferedWriter(fw);
		  String line;
		  List<String> listOfStrings;
		  line = br.readLine();
		  String[] nodes = line.split(" ");
		  HashMap<String,ArrayList<String>> mapOfNodes = new HashMap<String,ArrayList<String>>();
		  ArrayList<String> connectedNodes = new ArrayList<String>();
		  for(int i = 1;i < nodes.length;i++){
			  
		  }
		  //br.readLine();
	}

}
