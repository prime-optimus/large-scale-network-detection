package com.social.servlets;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class GraphUploadServlet  extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		DiskFileItemFactory factory = createDiskFileItemFactory();
		
		FileItem fileItem = processUpload(request, factory);
		if (fileItem != null){
			response.getWriter().print(fileItem.getName());
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
	
}
