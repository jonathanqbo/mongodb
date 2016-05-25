package bq.freemarker;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

public class HelloWorld {

	public String generate(){
		Configuration config = new Configuration(new Version("2.3.24"));
		config.setClassForTemplateLoading(HelloWorld.class, "/");
		
		Map<String, String> model = new HashMap<>();
		model.put("name", "Jonathan");
		
		try (StringWriter writer = new StringWriter()){
			Template template = config.getTemplate("helloworld.ftl");
			template.process(model, writer);
			
			return writer.toString();
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		HelloWorld hw = new HelloWorld();
		System.out.println(hw.generate());
		
		Map<String, String> model = new HashMap<>();
		model.put("name", "Qi Bo");
		System.out.println(BQFreeMarker.build("helloworld.ftl", model));
	}
	
}
