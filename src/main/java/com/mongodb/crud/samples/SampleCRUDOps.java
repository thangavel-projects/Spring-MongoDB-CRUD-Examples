package com.mongodb.crud.samples;

import java.net.UnknownHostException;
import org.bson.Document;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class SampleCRUDOps {
    
	public static void main(String[] args) throws UnknownHostException {
		SampleCRUDOps sampleCRUDOps = new SampleCRUDOps();
		sampleCRUDOps.findAll();
	}

	private void findAll() {
		MongoDatabase mongoDbConnection = MongoDbConnection.getMongoDbConnection();
		MongoCollection<Document> collection = mongoDbConnection.getCollection("movies");
		FindIterable<Document> cursor = collection.find();
		for (Document document : cursor) {
			String jsonObject = document.toJson();
			System.out.println(jsonObject);
		}
	}
}
