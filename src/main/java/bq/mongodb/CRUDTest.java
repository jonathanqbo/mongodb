package bq.mongodb;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Sorts.descending;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.junit.Test;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;

public class CRUDTest {

	@Test
	public void test(){
		try(BQAdminAPI admin = new BQAdminAPI()){
			String dbName = "testdb";
			String collectionName = "testcollection";

			admin.createDB(dbName);
			MongoCollection<Document> collection = admin.createCollection(dbName, collectionName);
			
			doCrud(collection);
			
			admin.dropCollection(dbName, collectionName);
			admin.dropDB(dbName);
			
		}
	}
	
	private void doCrud(MongoCollection<Document> collection){
		//
		System.out.println("\n Insert One");
		Document document = new Document("name","jonathan")
				.append("value", 10000);
		collection.insertOne(document );
		
		//
		System.out.println("\n Insert Many");
		List<Document> documents = new ArrayList<>();
		for(int i = 0 ;i < 10; i++){
			Document doc = new Document("name", "name" + i)
				.append("value", (i%5)*100);
			documents.add(doc);
		}
		collection.insertMany(documents);
		
		// 
		System.out.println("\n Query All");
		// This code is not good. It may cause cursor leak. See official document
//		for(Document doc : collection.find()){
//			System.out.println(doc.toJson());
//		}
		try(MongoCursor<Document> iterator = collection.find().iterator();){
			while(iterator.hasNext()){
				System.out.println(iterator.next().toJson());
			}
		}
		
		// 
		System.out.println("\n Filters: Query with One Condition (value=400)");
		try(MongoCursor<Document> iterator = collection.find(eq("value", 400)).iterator()){
			while(iterator.hasNext()){
				System.out.println(iterator.next().toJson());
			}
		}
		
		//
		System.out.println("\n Filters: Query with Two Condition with and relationship (name=name9 and value=400)");
		try(MongoCursor<Document> iterator = collection.find(and(eq("name", "name9"),eq("value",400))).iterator()){
			while(iterator.hasNext()){
				System.out.println(iterator.next().toJson());
			}
		}
		
		//
		System.out.println("\n Total count");
		System.out.println(collection.count());
		
		//
		System.out.println("\n Sorts. Sorting with value desc");
		try(MongoCursor<Document> iterator = collection.find().sort(descending("value")).iterator()){
			while(iterator.hasNext())
				System.out.println(iterator.next().toJson());
		}
		
		// 
		System.out.println("\n Projects. Don't show id field in result");
		try(MongoCursor<Document> iterator = collection.find().projection(excludeId()).iterator()){
			while(iterator.hasNext())
				System.out.println(iterator.next().toJson());
		}
		
		// 
		System.out.println("\n Update ");
		collection.updateMany(Filters.lt("value", 10000), Updates.set("value", 888));
		try(MongoCursor<Document> iterator = collection.find().iterator()){
			while(iterator.hasNext())
				System.out.println(iterator.next());
		}
		
		//
		System.out.println("\n Delete");
		collection.deleteMany(Filters.eq("value", 888));
		try(MongoCursor<Document> iterator = collection.find().iterator()){
			while(iterator.hasNext())
				System.out.println(iterator.next());
		}
		
	}
	
}
