package com.social.servlets;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.google.gson.stream.JsonWriter;
import com.social.generic.Node;
import com.social.ldf.LowDegreeFolloingAlgorithm;
import com.social.utils.GraphUtils;

@SuppressWarnings("serial")
public class DispatcherServlet extends HttpServlet {
	private static final String BASE_DIRECTORY = "f:\\temp\\";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String fileName = request.getParameter("fileName");
		String totalVertices = "34";//request.getParameter("totalVertices");
		if (StringUtils.isNotBlank(fileName) && StringUtils.isNotBlank(totalVertices)){
			List<Node> adjacencyList = GraphUtils.getAdjacencyListForGraphFile(BASE_DIRECTORY + fileName + ".tmp", Integer.parseInt(totalVertices));
			LowDegreeFolloingAlgorithm ldf = new LowDegreeFolloingAlgorithm(adjacencyList);
			Map<Node, List<Node>> communities = ldf.detectCommunities();
			Iterator<Entry<Node, List<Node>>> communitiesEntrySet = communities.entrySet().iterator();
			
			JsonWriter writer = new JsonWriter(response.getWriter());
			writer.beginObject();
			writer.name("response").value("success");
						
			writer.name("nodes");
			writer.beginArray();
			
			while(communitiesEntrySet.hasNext()){
				Entry<Node, List<Node>> nextCommunity = communitiesEntrySet.next();
				
				String groupName = nextCommunity.getKey().getStringId();
				
				for (Node node : nextCommunity.getValue()){
					writer.beginObject();
					
					writer.name("name");
					writer.value(node.getStringId());
					
					writer.name("group");
					writer.value(groupName);
					
					writer.endObject();
				}
			}
			writer.endArray();
			writer.endObject();
			writer.close();
		}
	}
}
/** try {
	String list = LDFCommunityDetection.SolveGraphByAdjacencyList(request.getParameter("fileName"));
	response.getWriter().write(list);
} catch (BiffException e) {
	e.printStackTrace();
}*/