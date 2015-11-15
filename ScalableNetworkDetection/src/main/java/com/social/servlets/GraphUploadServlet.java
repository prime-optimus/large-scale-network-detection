package com.social.servlets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

@SuppressWarnings("serial")
public class GraphUploadServlet  extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		DiskFileItemFactory factory = createDiskFileItemFactory();
		
		FileItem fileItem = processUploadRequest(request, factory);
		String fileName = writeToDisk(fileItem);
		
		if (StringUtils.isNotEmpty(fileName)){
			Map<String, String> returnValues = parseGraphFile(fileItem);
			Gson gson = new Gson(); 
			
			JsonObject jsonObject = new JsonObject();
			jsonObject.add("fileName",  gson.toJsonTree(fileName));
			jsonObject.add("displayData",  gson.toJsonTree(returnValues));
			response.getWriter().print(jsonObject.toString());
		} else {
			response.getWriter().print("No File found");
		}
	}

	private String writeToDisk(FileItem fileItem) {
		String fileName = StringUtils.EMPTY;
		
		try {
			fileName = String.valueOf(System.currentTimeMillis());
			InputStream inputStream = fileItem.getInputStream();
			OutputStream outputStream = new FileOutputStream("f:\\temp\\" + fileName + ".tmp");
			IOUtils.copy(inputStream, outputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileName;
	}

	private FileItem processUploadRequest(HttpServletRequest request,
			DiskFileItemFactory factory) {
		FileItem fileItem = null;
		
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		
		// Parse the request
		try {
			Iterator<FileItem> iterator = upload.parseRequest(request).iterator();
			
			fileItem = iterator.next();
			while(iterator.hasNext() && fileItem.isFormField()){
				fileItem = iterator.next();
			}
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
		totalNodes++; // since nodes are zero indexed
		double graphDensity = (totalEdges * 1d) / (totalNodes *(totalNodes -1));
		
		Map<String, String> returnValues = new LinkedHashMap<String, String>();
		returnValues.put("totalNodes", String.valueOf(totalNodes));
		returnValues.put("totalEdges", String.valueOf(totalEdges));
		returnValues.put("graphDensity", String.format("%.3f", graphDensity));
		return returnValues;
	}
	
}
