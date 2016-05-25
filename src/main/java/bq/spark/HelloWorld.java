package bq.spark;

import java.util.HashMap;
import java.util.Map;

import bq.freemarker.BQFreeMarker;
import spark.Spark;

public class HelloWorld {

	public static void main(String[] args) {
		// http://localhost:4567/helloworld
		Spark.get("/helloworld", (request, response) -> {
				return "Hello World!";
		});
		
		// with path params
		// example: http://localhost:4567/echo/Jonathan
		Spark.get("/echo/:who", (request, response) -> {
				String name = request.params(":who");
				return "Hello World, " + name;
		});
		
		// using freemarker
		Spark.get("/freemarker/hello/:who", (request, response) -> {
				String name = request.params(":who");

				Map<String, String> model = new HashMap<>();
				model.put("name", name);
				
				return BQFreeMarker.build("helloworld.ftl", model);
		});
		
	}
	
}
