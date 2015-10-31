package com.social.servlets;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@SuppressWarnings("serial")
public class GraphUploadServlet  extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		DiskFileItemFactory factory = createDiskFileItemFactory();
		
		FileItem fileItem = processUpload(request, factory);
		if (fileItem != null){
			Map<String, String> returnValues = parseGraphFile(fileItem);
			Gson gson = new Gson(); 
			
			JsonObject jsonObject = new JsonObject();
			jsonObject.add("fileName",  gson.toJsonTree(fileItem.getName()));
			jsonObject.add("displayData",  gson.toJsonTree(returnValues));
			response.getWriter().print(jsonObject.toString());
		} else {
			response.getWriter().print("No File found");
		}
	}

	private FileItem processUpload(HttpServletRequest request,
			DiskFileItemFactory factory) {
		FileItem fileItem = null;
		
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		// Parse the request
		try {
			List<FileItem> items = upload.parseRequest(request);
			fileItem = items.get(0);
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
		return fileItem;
	}

	private DiskFileItemFactory createDiskFileItemFactory() {
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Configure a repository (to ensure a secure temp location is used)
		//ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = new File("f:\\temp");
		factory.setRepository(repository);
		return factory;
	}

	@SuppressWarnings("resource")
	private Map<String, String> parseGraphFile(FileItem fileItem) throws IOException {
		Scanner in = new Scanner(fileItem.getInputStream());
		
		int totalNodes=0, totalEdges=0;
		while(in.hasNext()){
			int fromNode = in.nextInt(), toNode=in.nextInt();
			totalNodes = toNode > fromNode ? 
					toNode > totalNodes ? toNode : totalNodes :   
						fromNode > totalNodes ? fromNode : totalNodes;
			totalEdges++;
		}
		double graphDensity = (totalEdges * 1d) / (totalNodes *(totalNodes -1));
		
		Map<String, String> returnValues = new LinkedHashMap<String, String>();
		returnValues.put("totalNodes", String.valueOf(totalNodes));
		returnValues.put("totalEdges", String.valueOf(totalEdges));
		returnValues.put("graphDensity", String.format("%.3f", graphDensity));
		return returnValues;
	}
	
}
