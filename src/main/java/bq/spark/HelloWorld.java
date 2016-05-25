package bq.spark;

import java.util.HashMap;
import java.util.Map;

import bq.freemarker.BQFreeMarker;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

public class HelloWorld {

	public static void main(String[] args) {
		// http://localhost:4567/helloworld
		Spark.get("/helloworld", new Route() {
			
			public Object handle(Request request, Response response) throws Exception {
				return "Hello World!";
			}
		});
		
		// with path params
		// example: http://localhost:4567/echo/Jonathan
		Spark.get("/echo/:who", new Route() {
			
			public Object handle(Request request, Response response) throws Exception {
				String name = request.params(":who");
				return "Hello World, " + name;
			}
		});
		
		// using spark
		Spark.get("/freemarker/hello/:who", new Route() {
			
			@Override
			public Object handle(Request request, Response response) throws Exception {
				String name = request.params(":who");

				Map<String, String> model = new HashMap<>();
				model.put("name", name);
				
				return BQFreeMarker.build("helloworld.ftl", model);
			}
		});
	}
	
}
