package com.social.servlets;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

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
		String totalNodes = request.getParameter("totalNodes");
		
		if (StringUtils.isNotBlank(fileName) && NumberUtils.isNumber(totalNodes)){
			String filePath = BASE_DIRECTORY + fileName + ".tmp";
			List<Node> adjacencyList = GraphUtils.getAdjacencyListForGraphFile(filePath, Integer.parseInt(totalNodes));
			LowDegreeFolloingAlgorithm ldf = new LowDegreeFolloingAlgorithm(adjacencyList);
			Map<Node, List<Node>> communities = ldf.detectCommunities();
			writeCommunityResponseJson(response, communities, filePath);
		}
	}

	private void writeCommunityResponseJson(HttpServletResponse response,
			Map<Node, List<Node>> communities, String filePath) throws IOException {
		JsonWriter writer = new JsonWriter(response.getWriter());
		writer.beginObject();
		GraphUtils.writeCommunityListToJson(writer, communities);
		GraphUtils.writeEdgeListJsonForGraphFile(writer, filePath);
		writer.endObject();
		writer.close();
	}

	
}
/** try {
	String list = LDFCommunityDetection.SolveGraphByAdjacencyList(request.getParameter("fileName"));
	response.getWriter().write(list);
} catch (BiffException e) {
	e.printStackTrace();
}*/