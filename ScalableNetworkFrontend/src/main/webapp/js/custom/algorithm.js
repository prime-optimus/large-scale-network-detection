function executeAlgorithm(element){
	var algo = $(element).data("algo");	
	$.ajax({
	  url: '/network/api/execute.json',
	  type: 'GET',
	  data: {'algo':algo,'fileName': $("#fileName").val(), 'totalNodes': $("#totalNodes").val()},
	  success: function(response) {
		var graph = $.parseJSON(response);	
		$("#graph_placeholder").show();
		
		var container = document.getElementById('graph_placeholder_content');
		var options = {
		autoResize: true,
		width: '100%',
		height: '100%',
		nodes: {
			shape: 'dot',
			scaling: {
			  min: 10,
			  max: 30
			},
			font: {
			  size: 12,
			  face: 'Tahoma'
			}
		  },
		  edges: {
			color:{inherit:true},
			width: 0.15,
			smooth: {
			  type: 'continuous'
			}
		  },
		  physics: false
		};

		var data = {nodes:graph.nodes, edges:graph.links};
		var network = new vis.Network(container, data, options);
	  },
	  error: function(e) {
		alert("Sorry, Something went wrong!")
	  }
	});
}	