Dropzone.options.graphUploadForm = {
	maxFilesize : 100,
	init : function() {
		this.on("success", function(file, message) {
			var response = $.parseJSON(message);	
			_.templateSettings.variable = "response";
			var temp = $("#graphData").html();
			var template = _.template(temp);
			var html = template(response.displayData);
			$(".top_tiles").html(html);
		});
	}
};