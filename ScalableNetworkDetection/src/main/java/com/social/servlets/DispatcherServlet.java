package com.social.servlets;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.google.gson.stream.JsonWriter;
import com.social.base.CommunityAlgorithm;
import com.social.base.Node;
import com.social.ldf.LowDegreeFollowingAlgorithm;
import com.social.utils.GraphUtils;

@SuppressWarnings("serial")
public class DispatcherServlet extends HttpServlet {
	private static final String BASE_DIRECTORY = "f:\\temp\\";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String fileName = request.getParameter("fileName");
		String totalNodes = request.getParameter("totalNodes");
		String algo = request.getParameter("algo");
		
		if (StringUtils.isNotBlank(fileName) && NumberUtils.isNumber(totalNodes) && algorithmMap.containsKey(algo)){
			String filePath = BASE_DIRECTORY + fileName + ".tmp";
			List<Node> adjacencyList = GraphUtils.getAdjacencyListForGraphFile(filePath, Integer.parseInt(totalNodes));
			
			Class<? extends CommunityAlgorithm> communityAlgorithm = algorithmMap.get(algo);
			CommunityAlgorithm algorithm = getCommunityAlgotihmInstance(communityAlgorithm);
			
			Map<Node, List<Node>> communities = algorithm.detectCommunities(adjacencyList);
			writeCommunityResponseJson(response, communities, filePath);
		}
	}

	private void writeCommunityResponseJson(HttpServletResponse response,
			Map<Node, List<Node>> communities, String filePath) throws IOException {
		JsonWriter writer = new JsonWriter(response.getWriter());
		writer.beginObject();
		
		writer.name("total");
		writer.value(String.valueOf(communities.size()));
		
		GraphUtils.writeCommunityListToJson(writer, communities);
		GraphUtils.writeEdgeListJsonForGraphFile(writer, filePath);
		writer.endObject();
		writer.close();
	}
	 
	private CommunityAlgorithm getCommunityAlgotihmInstance(
			Class<? extends CommunityAlgorithm> communityAlgorithm) {
		CommunityAlgorithm algorithm = null;
		
		try {
			algorithm = communityAlgorithm.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return algorithm;
	}

	 
	
	private static Map<String, Class<? extends CommunityAlgorithm>> algorithmMap;
	static {
		Map<String, Class<? extends CommunityAlgorithm>> tempMap = new HashMap<>();
		tempMap.put("1", LowDegreeFollowingAlgorithm.class);
		algorithmMap = Collections.unmodifiableMap(tempMap);
	}
}