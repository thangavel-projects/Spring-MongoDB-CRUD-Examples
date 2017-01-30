package com.mongodb.crud.samples;

import static com.mongodb.client.model.Filters.eq;

import java.net.UnknownHostException;
import java.util.Arrays;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class SampleCRUDOps {
    
	public static void main(String[] args) throws UnknownHostException {
		MongoDatabase mongoDbConnection = MongoDbConnection.getMongoDbConnection();
		MongoCollection<Document> collection = mongoDbConnection.getCollection("moviesnew1");
		SampleCRUDOps sampleCRUDOps = new SampleCRUDOps();
		
		sampleCRUDOps.findAll(collection);
		sampleCRUDOps.insert(collection);
		sampleCRUDOps.update(collection);
		sampleCRUDOps.delete(collection);
		
		// Insert Multiple Records
		CRUDHelper.insertMany(collection);
		
	}

	private void findAll(MongoCollection<Document> collection) {
		FindIterable<Document> cursor = collection.find();
		for (Document document : cursor) {
			String jsonObject = document.toJson();
			System.out.println(jsonObject);
		}
	}
	
	private void insert(MongoCollection<Document> collection){
		Document document = new Document("name","Thangavel")
				.append("country", "India")
				.append("diploma", Arrays.asList("ME","BE","MA"))
				.append("locationAxis", new Document("x",34).append("y", 35));
		collection.insertOne(document);
	}
	
	private void update(MongoCollection<Document> collection){
		collection.updateOne(eq("name", "Thangavel"), new Document("$set", new Document("name", "Thangavel Loganathan")));
	}
	
	private void delete(MongoCollection<Document> collection){
		collection.deleteOne(eq("title", "jaws"));
	}
}
