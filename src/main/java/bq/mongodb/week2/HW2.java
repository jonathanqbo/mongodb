package bq.mongodb.week2;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.orderBy;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;

public class HW2 {

	public static void main(String[] args) {
		try(MongoClient client = new MongoClient()){
			MongoDatabase db = client.getDatabase("students");
			MongoCollection<Document> grades = db.getCollection("grades");
			
//			Document sort = new Document("student_id","1");
//			sort.append("score", "1");
			
			List<ObjectId> ids = new ArrayList<>();
			
			// way 1, loop all results
			try(MongoCursor<Document> iterator = grades.find(eq("type", "homework")).sort(orderBy(ascending("student_id"),Sorts.ascending("score"))).iterator()){
				Object preId = null;
				while(iterator.hasNext()){
					Document next = iterator.next();
					Object id = next.get("student_id");
					if(!id.equals(preId)){
						ids.add(next.getObjectId("_id"));
						preId = id;
					}
				}
				
			}
			
			// way 2, loop all results
//			FindIterable<Document> results = grades.find(eq("type", "homework")).sort(orderBy(ascending("student_id"),ascending("score")));
//			results.forEach((Document v) -> System.out.println(v));
			
			ids.forEach(v -> grades.deleteOne(eq("_id",v)));
			
		}
	}
	
}
