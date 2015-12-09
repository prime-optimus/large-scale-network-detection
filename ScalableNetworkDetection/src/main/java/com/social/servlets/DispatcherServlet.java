package com.social.servlets;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.google.gson.stream.JsonWriter;
import com.social.generic.Node;
import com.social.ldf.LowDegreeFolloingAlgorithm;
import com.social.utils.GraphCompressionUtils;
import com.social.utils.GraphUtils;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

@SuppressWarnings("serial")
public class DispatcherServlet extends HttpServlet {
	private static final String BASE_DIRECTORY = "f:\\temp\\";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String fileName = request.getParameter("fileName");
		String totalNodes = request.getParameter("totalNodes");
		String requestType = request.getParameter("type");
		
		if (StringUtils.isNotBlank(fileName) && NumberUtils.isNumber(totalNodes)){
			if (!StringUtils.equals(requestType, "open")){
				handleCommunityDetectionRequest(response, fileName, totalNodes);
			} else {
				handleCommunityOpenRequest(response, fileName, totalNodes); 
			}
			
			
		}
	}

	private void handleCommunityDetectionRequest(HttpServletResponse response, String fileName,
			String totalNodes) throws FileNotFoundException, IOException {
		String filePath = BASE_DIRECTORY + fileName + ".tmp";
		List<Node> adjacencyList = GraphUtils.getAdjacencyListForGraphFile(filePath, Integer.parseInt(totalNodes) + 1);
		LowDegreeFolloingAlgorithm ldf = new LowDegreeFolloingAlgorithm(adjacencyList);
		Map<Node, List<Node>> communities = ldf.detectCommunities();
		List<Node> compressedCommunity = GraphCompressionUtils.compressCommunityRepresentation(adjacencyList, communities.size());
		writeCommunityResponseJson(response, compressedCommunity);
	}
	
	private void handleCommunityOpenRequest(HttpServletResponse response,
			String fileName, String totalNodes) throws NumberFormatException, IOException {
		String filePath = BASE_DIRECTORY + fileName + ".tmp";
		List<Node> adjacencyList = GraphUtils.getAdjacencyListForGraphFile(filePath, Integer.parseInt(totalNodes) + 1);
		LowDegreeFolloingAlgorithm ldf = new LowDegreeFolloingAlgorithm(adjacencyList);
		Map<Node, List<Node>> communities = ldf.detectCommunities();
		
		List<Node> community = GraphCompressionUtils.openCommunity(communities, adjacencyList, 33);
		writeCommunityResponseJson(response, community);
	}

	private void writeCommunityResponseJson(HttpServletResponse response,
			List<Node> communities) throws IOException {
		JsonWriter writer = new JsonWriter(response.getWriter());
		
		Graph<Integer, String> communityGraph = new UndirectedSparseGraph<>();
		IntStream.range(0, communities.size()).forEach(index -> {
			communityGraph.addVertex(index);
		});
		
		communities.forEach(node -> {
			int startVertex = node.getId();
			
			node.getNeighbors().forEach(edge -> {
				int endVertex = edge.getOtherEnd().getId();
				String edgeLabel = node.getStringId() + ":" + edge.getOtherEnd().getStringId();
				communityGraph.addEdge(edgeLabel, startVertex, endVertex);
			});
		});
		
		writer.beginObject();
		GraphUtils.writeCommunityList(writer, communities);
		GraphUtils.writeEdgeList(writer, communities);
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