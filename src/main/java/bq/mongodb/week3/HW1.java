package bq.mongodb.week3;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.orderBy;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;

public class HW1 {

	public static void main(String[] args) {
		try(MongoClient client = new MongoClient()){
			MongoDatabase db = client.getDatabase("school");
			MongoCollection<Document> students = db.getCollection("students");
			
			// way 1, loop all results
			try(MongoCursor<Document> iterator = students.find().iterator()){
				while(iterator.hasNext()){
					Document next = iterator.next();
					Integer id = next.getInteger("_id");
					removeLowestHomework(next);
					
					// replace one
					students.replaceOne(eq("_id", id), next);
					// or update score array
					//students.updateOne(Filters.eq("_id", student.getInteger("_id")), new Document("$set", new Document("scores", scores)));
				}
				
			}
			
			// way 2, loop all results
//			FindIterable<Document> results = grades.find(eq("type", "homework")).sort(orderBy(ascending("student_id"),ascending("score")));
//			results.forEach((Document v) -> System.out.println(v));
			
		}
	}
	
	private static void removeLowestHomework(Document doc) {
		List<Document> scores = (List<Document>) doc.get("scores");
		Document min = scores.stream()
				.filter( s -> s.get("type").equals("homework"))
				.min( (s1, s2) -> {
					Double v1 = s1.getDouble("score");
					Double v2 = s2.getDouble("score");
					return v1.compareTo(v2);
				}).get();
		
		scores.remove(min);
		
		doc.put("scores", scores);
	}
	
}
