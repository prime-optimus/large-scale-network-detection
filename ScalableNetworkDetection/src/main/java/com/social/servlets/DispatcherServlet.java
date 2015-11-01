package com.social.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.read.biff.BiffException;

import com.shan.jgraph.LDFCommunityDetection;

@SuppressWarnings("serial")
public class DispatcherServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String list = LDFCommunityDetection.SolveGraphByAdjacencyList(request.getParameter("fileName"));
			response.getWriter().write(list);
		} catch (BiffException e) {
			e.printStackTrace();
		}
		
	}
	
}
