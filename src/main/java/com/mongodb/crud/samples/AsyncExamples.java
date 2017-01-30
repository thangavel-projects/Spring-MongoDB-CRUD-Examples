package com.mongodb.crud.samples;

import java.util.Arrays;
import org.bson.Document;
import com.mongodb.ServerAddress;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClientSettings;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import com.mongodb.connection.ClusterSettings;

public class AsyncExamples {

	public static void main(String[] args) {
		
		ClusterSettings clusterSettings = ClusterSettings.builder()
										.hosts(Arrays.asList(new ServerAddress("localhost")))
										.build();
		
		MongoClientSettings clientOptions = MongoClientSettings.builder()
										.clusterSettings(clusterSettings).build();
		
		MongoClient client = MongoClients.create(clientOptions); 
		
		MongoDatabase database = client.getDatabase("async");
		MongoCollection<Document> collection = database.getCollection("foo");
		
		Document document = new Document("name","Thangavel")
							.append("designation", "Developer")
							.append("diploma", Arrays.asList("ME","BE"))
							.append("graph", new Document("x","1").append("Y", "2"));
		
		// Java 7 <
		collection.insertOne(document, new SingleResultCallback<Void>() {
			@Override
			public void onResult(Void result, Throwable t) {
				System.out.println("Inserteted");
			}
		});			
		
		// Java8
		collection.insertOne(document, (Void result, Throwable t) -> System.out.println("inserted"));
	}
}
