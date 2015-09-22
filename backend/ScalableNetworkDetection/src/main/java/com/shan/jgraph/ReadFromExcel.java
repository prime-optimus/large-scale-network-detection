package com.shan.jgraph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import jxl.read.biff.BiffException;

import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ReadFromExcel {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws WriteException 
	 * @throws RowsExceededException 
	 * @throws BiffException 
	 */
	public static void main(String[] args) throws IOException, RowsExceededException, WriteException, BiffException {
		// TODO Auto-generated method stub
		//WritableWorkbook wworkbook;
	      /*wworkbook = Workbook.createWorkbook(new File("output.xls"));
	      WritableSheet wsheet = wworkbook.createSheet("First Sheet", 0);
	      Label label = new Label(0, 2, "A label record");
	      wsheet.addCell(label);
	      Number number = new Number(3, 4, 3.1459);
	      wsheet.addCell(number);
	      wworkbook.write();
	      wworkbook.close();*/
		  int startNodeY = 6;
		  int startNodeX = 3;
		  int totalPlayersPlayed = 13;
		  Edge e1;
		  List<List<Edge>> listOfNodes = new ArrayList<List<Edge>>();
		  ArrayList<Edge> passes;
	      Workbook workbook = Workbook.getWorkbook(new File("D:\\cygwin\\home\\downloads\\2014309_tpd.xls"));
	      Sheet sheet = workbook.getSheet(1);
	      for(int j = 1;j < 15;j++){

    		  passes = new ArrayList<Edge>();
	    	  for (int i = 1;i < 15;i++){
	    		  int NodeA = Integer.parseInt(sheet.getCell(i, 0).getContents());
	    		  int NodeB = Integer.parseInt(sheet.getCell(0,j).getContents());
	    		  String weight = sheet.getCell(i,j).getContents();
	    		  if (!weight.isEmpty() && !weight.contains("-")){
	    			  passes.add(new Edge(new Node(NodeA), new Node(NodeB),Integer.parseInt(weight)));
	    		  } else {
	    			  passes.add(new Edge(new Node(NodeA), new Node(NodeB),0));
	    		  }
	    	  }
	    	  listOfNodes.add(passes);
	      }
	      
	      for(int i = 0;i<14;i++){
	    	  for(int j = 0;j < 14;j++){
	    		System.out.println(listOfNodes.get(i).get(j).getEdgeWeight());  
	    	  }
	      }
	      workbook.close();
	}

}
