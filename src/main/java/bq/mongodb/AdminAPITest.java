package bq.mongodb;

import org.bson.Document;
import org.junit.Test;

public class AdminAPITest {
	
	@Test
	public void testAllDB(){
		String dbName = "test";
		
		System.out.println("\n List all Database names");
		
		try(BQAdminAPI admin = new BQAdminAPI()){
			for (String adbName : admin.listDBNames()) {
				System.out.println(adbName);
			}
		}
	
		System.out.println("\n List all Database details");
		
		try(BQAdminAPI admin = new BQAdminAPI()){
			for (Document doc : admin.listDBs()) {
				System.out.println(doc.toJson());
			}
		}

		System.out.println("\n List all Collection details");
		
		try(BQAdminAPI admin = new BQAdminAPI()){
			for (Document doc : admin.listCollections(dbName)) {
				System.out.println(doc.toJson());
			}
		}
		
		System.out.println("\n Create a Databas");
		
		String testDb = "temp_db";
		try(BQAdminAPI admin = new BQAdminAPI()){
			admin.createDB(testDb);
		}
		
		System.out.println("\n Create a Collection");
		
		String testCollection = "temp_collection";
		try(BQAdminAPI admin = new BQAdminAPI()){
			admin.createCollection(testDb, testCollection);
		}
		
		// insert a record to make db/collection really created
		try(BQAdminAPI admin = new BQAdminAPI()){
			Document doc = new Document("value","test data")
					.append("id", 123);
			
			admin.getDB(testDb).getCollection(testCollection).insertOne(doc);
		}
		
		System.out.println("\n Show all Database after creation");
		
		try(BQAdminAPI admin = new BQAdminAPI()){
			for (String adbName : admin.listDBNames()) {
				System.out.println(adbName);
			}
		}
		
		System.out.println("\n Show all Collections after creation");
		
		try(BQAdminAPI admin = new BQAdminAPI()){
			for (String adbName : admin.listCollectionNames(testDb)) {
				System.out.println(adbName);
			}
		}
		
		System.out.println("\n Show all Indexs");
		
		try(BQAdminAPI admin = new BQAdminAPI()){
			for(Document doc : admin.listIndexs(testDb, testCollection)){
				System.out.println(doc.toJson());
			}
		}
		
		System.out.println("\n Create an Index");
		
		try(BQAdminAPI admin = new BQAdminAPI()){
			admin.createIndex(testDb, testCollection, "id");
		}
		
		System.out.println("\n Show all Indexs after creation");
		
		try(BQAdminAPI admin = new BQAdminAPI()){
			for(Document doc : admin.listIndexs(testDb, testCollection)){
				System.out.println(doc.toJson());
			}
		}
		
		System.out.println("\n Drop newly created Collection");
		
		try(BQAdminAPI admin = new BQAdminAPI()){
			admin.dropCollection(testDb, testCollection);
		}
		
		System.out.println("\n Show all Collections after drop");
		
		try(BQAdminAPI admin = new BQAdminAPI()){
			for (String adbName : admin.listCollectionNames(testDb)) {
				System.out.println(adbName);
			}
		}
		
		System.out.println("\n Drop newly created Database");
		
		try(BQAdminAPI admin = new BQAdminAPI()){
			admin.dropDB(testDb);
		}
		
		System.out.println("\n Show all Database after drop");
		
		try(BQAdminAPI admin = new BQAdminAPI()){
			for (String adbName : admin.listDBNames()) {
				System.out.println(adbName);
			}
		}
		
	}
	
}
