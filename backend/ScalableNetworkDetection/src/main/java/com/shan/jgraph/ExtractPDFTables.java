package com.shan.jgraph;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class ExtractPDFTables {

	/**
	 * @param args
	 * api key = y9ss381lkpmk
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public static void main(String[] args) throws ClientProtocolException, IOException {
		        /*if (args.length != 1)  {
		            System.out.println("File path of PDF not given");
		            System.exit(1);
		        }*/
		        CloseableHttpClient httpclient = HttpClients.createDefault();
		        try {
		            HttpPost httppost = new HttpPost("https://pdftables.com" +
		                    "/api?key=y9ss381lkpmk&format=xlsx-single");

		            FileBody bin = new FileBody(new File("D:\\cygwin\\home\\downloads\\2014333_tpd.pdf"));
		            File output = new File("D:\\cygwin\\home\\downloads\\2014333_tpd.xlsx");
		            FileWriter fw = new FileWriter(output.getAbsoluteFile());

                    BufferedWriter bw = new BufferedWriter(fw);
		            HttpEntity reqEntity = MultipartEntityBuilder.create()
		                    .addPart("f", bin)
		                    .build();
		            httppost.setEntity(reqEntity);

		            System.out.println("executing request " + httppost.getRequestLine());
		            CloseableHttpResponse response = httpclient.execute(httppost);
		            try {
		                System.out.println(response.getStatusLine());
		                HttpEntity resEntity = response.getEntity();
		                if (resEntity != null) {
		                    System.out.println(EntityUtils.toString(resEntity));
							bw.write(EntityUtils.toString(resEntity));
		                }
		                EntityUtils.consume(resEntity);
		            } finally {

						bw.close();
		                response.close();
		                
		            }
		        } finally {
		            httpclient.close();
		        }
		    }

	}


