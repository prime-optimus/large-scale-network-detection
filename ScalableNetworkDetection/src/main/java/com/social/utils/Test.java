package com.social.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

public class Test {

	public static void main(String[] args) throws IOException {
		FileInputStream inputStream = new FileInputStream("F:\\UFL\\DataSets\\SocialNetworkComputing\\football.txt");
		FileOutputStream outStream = new FileOutputStream("F:\\UFL\\DataSets\\SocialNetworkComputing\\football1.txt");
	
		PrintWriter pw = new PrintWriter(outStream);
		Scanner in = new Scanner(inputStream);
		while(in.hasNext()){
			int a=in.nextInt()-1, b=in.nextInt()-1;
			pw.println(a + " " + b);
		}
		pw.close();
		in.close();
	}
	
	public static void readIPFromNIO() throws IOException{
		  FileInputStream fis = new FileInputStream(new File("F:\\UFL\\DataSets\\SocialNetworkComputing\\soc-LiveJournal2.txt"));
		  FileChannel channel = fis.getChannel();
		  ByteBuffer bb = ByteBuffer.allocateDirect(64*1024);
		  bb.clear();
		  int[] ipArr = new int [(int)channel.size()/128];
		  System.out.println("File size: "+channel.size()/128);
		  long len = 0;
		  int offset = 0;
		  long startTime = System.currentTimeMillis();
		  while ((len = channel.read(bb))!= -1){
		    bb.flip();
		    //System.out.println("Offset: "+offset+"\tlen: "+len+"\tremaining:"+bb.hasRemaining());
		    bb.asIntBuffer().get(ipArr,offset,(int)len/128);
		    System.out.println(bb);
		    offset += (int)len/128;
		    bb.clear();
		  }
		  System.out.println("Time " + (System.currentTimeMillis() - startTime));
		}

}
