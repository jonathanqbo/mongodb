package bq.spark;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import bq.freemarker.BQFreeMarker;
import spark.Spark;

public class HelloWorldPost {

	public static void main(String[] args) {
		
		// return html page for next post operation
		Spark.get("/freemarker/get/helloworld", (request, response) -> {
			Map<String, List<String>> model = new HashMap<>();
			model.put("names", Arrays.asList("Jonathan","Qi", "Bo"));
			
			String page = BQFreeMarker.build("namelist.ftl", model);
			return page;
		});
		
		// POST
		Spark.post("/post/helloworld", (request, response) -> {
			String json = request.body();
			ObjectMapper om = new ObjectMapper();
			JsonNode node = om.readTree(json);
			return node.get("name");
		});
	}

}
