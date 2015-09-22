package com.shan.jgraph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class Test3 {

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
		  br.readLine();
		  HashMap<String,String> mapOfEdges = new HashMap<String,String>();
		  HashMap<String,ArrayList<String>> mapOfGeneNodesRespForDis = new HashMap<String,ArrayList<String>>();
		  while((line = br.readLine()) != null){
			  listOfStrings = Arrays.asList(line.split("\t"));
			  //Matcher m = p.matcher(line);
			  //System.out.println(m.group(0));
			  String geneId = listOfStrings.get(0);
			  String diseaseId = Arrays.asList(listOfStrings.get(3).split(":")).get(1);
			  //System.out.println("{'source':"+geneId+",'target':"++"}");
			  if(!mapOfGeneNodesRespForDis.containsKey(diseaseId)){
				  ArrayList<String> listOfGenesResp = new ArrayList<String>();
				  listOfGenesResp.add(geneId);
				  mapOfGeneNodesRespForDis.put(diseaseId, listOfGenesResp);
			  }	else {
				  mapOfGeneNodesRespForDis.get(diseaseId).add(geneId);
			  }
			  /*if(!mapOfGeneNodes.containsKey(geneId)){
				  mapOfGeneNodes.put(geneId, arg1)
			  }*/
			  
		  }
		  int i = 0;
		  for(Entry<String,ArrayList<String>> entry:mapOfGeneNodesRespForDis.entrySet()){
			  //System.out.print(entry.getKey() + " ");
			  //bw.write(entry.getKey() + " ");
			  for(int j = 0;i < entry.getValue().size();i++){
				  for(int k = j+1;k < entry.getValue().size())
			  }
			  //System.out.println();
			  bw.write("\n");
			  i++;
		  }
		  bw.close();
		  System.out.println(i);
	}

}
