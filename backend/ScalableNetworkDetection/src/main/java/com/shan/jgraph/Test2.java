package com.shan.jgraph;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.ListIterator;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

public class Test2 {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) throws MalformedURLException, IOException {
		// TODO Auto-generated method stub
		String accessToken = "CAACEdEose0cBADHc2h6FEgvHe6ZBE95okJkcSMgtxEZCw5U5CrZAZARHMeu2HzpQR02F8LTELDtGBZBH2qT7WgQdPzb5FEUUdCT9R8X8lzZAoIvwvKa8mG9mi12gQ3yQSwxgWIf8BKHrEGCiEPI8MlG9OQYJBMs76njiOtQOZBnDYy5ww35bYhLiptq2lzrClTpLPucQqS42mwJbL6Ol62T";
		String url = "https://graph.facebook.com/v2.4/me?fields=friends{id}&access_token="+accessToken;
		URLConnection connection = new URL(url).openConnection();
		connection.setRequestProperty("Accept-Charset", "UTF-32");
		connection.connect();
		HttpURLConnection httpConn = (HttpURLConnection) connection;
		InputStream response =  connection.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(response));
		String resp = "";
		String currentLine;
		while ( (currentLine = br.readLine()) != null){
			resp += currentLine + "\n";
		}
		
		System.out.println(resp);
		String jsonPath = "$.friends.data[0].id";
		
		jsonPath = JsonPath.read(resp, jsonPath);
		
		ArrayList<String> friendsList = JsonPath.read(resp, "$.friends.data[*].id");
		/*url = "https://graph.facebook.com/v2.4/"+jsonPath+"?fields=friends&access_token=CAACEdEose0cBAFBnesK8SmG38YTZA1h8e8J0b964YQNnfwhSVzBMyXZAdwqeKg5DkL51ut8y8SLKVxfJcSRwXg9xU4e4yJA3vtOsSk5c1VZCjXVpv0L0XNCWPHcwKAaOQFZCuCFsOHfykRaYzDxh2GWurU9C2xBYaMTT1ItR0GxKnb8LupWsBjhJs1DkCCYffnOBeIxgQxjJY81HNQPM";
		URLConnection connection2 = new URL(url).openConnection();
		connection2.setRequestProperty("Accept-Charset", "UTF-32");
		connection2.connect();
		HttpURLConnection httpConn2 = (HttpURLConnection) connection2;
		InputStream response2 =  connection2.getInputStream();
		BufferedReader br2 = new BufferedReader(new InputStreamReader(response));
		String resp2 = "";
		String currentLine2;
		while ( (currentLine = br.readLine()) != null){
			resp += currentLine + "\n";
		}
		
		System.out.println(resp);
		*/
		ListIterator<String> lItr = friendsList.listIterator();
		while(lItr.hasNext()){
			System.out.println(lItr.next());
		}
		System.out.println();
	}

}
