package bq.mongodb;

import java.io.Closeable;
import java.util.Optional;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Indexes;

public class BQAdminAPI implements Closeable{
	
	private MongoClient client = null;
	
	public BQAdminAPI() {
		init();
	}
	
	private void init(){
		client = new MongoClient();
	}
	
	@Override
	public void close(){
		client.close();
	}

	public Iterable<String> listDBNames(){
		return client.listDatabaseNames();
	}
	
	public Iterable<Document> listDBs(){
		return client.listDatabases();
	}
	
	public Iterable<String> listCollectionNames(String dbName){
		MongoDatabase db = client.getDatabase(dbName);
		return db.listCollectionNames();
	}
	
	public Iterable<Document> listCollections(String dbName){
		MongoDatabase db = client.getDatabase(dbName);
		return db.listCollections();
	}
	
	public MongoDatabase createDB(String dbName){
		return client.getDatabase(dbName);
	}
	
	public MongoDatabase getDB(String dbName){
		return client.getDatabase(dbName);
	}
	
	public void dropDB(String dbName){
		client.getDatabase(dbName).drop();
	}
	
	public MongoCollection<Document> createCollection(String dbName,String collectionName){
		MongoDatabase db = client.getDatabase(dbName);
		Optional.of(db.getCollection(collectionName))
			.orElseThrow(() -> new IllegalArgumentException("Collection is alrady existed"));
		
		db.createCollection(collectionName);
		return db.getCollection(collectionName);
	}
	
	public MongoCollection<Document> getCollection(String dbName, String collectionName){
		MongoDatabase db = client.getDatabase(dbName);
		
		return Optional.ofNullable(db)
					.orElseThrow(IllegalArgumentException::new)
					.getCollection(collectionName);
	}
	
	public void dropCollection(String dbName, String collectionName){
		MongoCollection<Document> collection = getCollection(dbName, collectionName);
		
		Optional.ofNullable(collection)
			.orElseThrow(IllegalArgumentException::new)
			.drop();
	}
	
	public void createIndex(String dbName, String collectionName, String field) {
		MongoDatabase db = client.getDatabase(dbName);
		MongoCollection<Document> collection = db.getCollection(collectionName);
		
		// create a ascending index for one field
		// it also supports to create index for multiple fields
		collection.createIndex(Indexes.ascending(field));
	}
	
	public Iterable<Document> listIndexs(String dbName, String collectionName) {
		MongoDatabase db = client.getDatabase(dbName);
		MongoCollection<Document> collection = db.getCollection(collectionName);
		return collection.listIndexes();
	}
	
	public Document runNativeCommand(String dbName, String key, Object value) {
		MongoDatabase db = client.getDatabase(dbName);
		return db.runCommand(new Document(key, value));
	}
	
	
}
