package bq.freemarker;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.Version;

public class BQFreeMarker {

	private static Configuration config = null;
	
	static{
		config = new Configuration(new Version("2.3.24"));
		config.setClassForTemplateLoading(BQFreeMarker.class, "/");
	}
		
	public static String build(String template, Map<?, ?> model){
		try (StringWriter writer = new StringWriter()){
			Template t = config.getTemplate(template);
			t.process(model, writer);
			
			return writer.toString();
		} catch (IOException | TemplateException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
