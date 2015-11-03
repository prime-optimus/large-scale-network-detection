package com.social.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Test {

	public static void main(String[] args) throws IOException {
		readIPFromNIO();

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
