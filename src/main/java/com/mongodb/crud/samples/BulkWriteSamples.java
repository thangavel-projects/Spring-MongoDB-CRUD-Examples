package com.mongodb.crud.samples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.BulkWriteOptions;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.InsertManyOptions;
import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.WriteModel;

public class BulkWriteSamples {

	
	public static void main(String[] args) {
		MongoDatabase mongoDbConnection = MongoDbConnection.getMongoDbConnection();
		mongoDbConnection.getCollection("bulkWrite").drop();
		MongoCollection<Document> collection = mongoDbConnection.getCollection("bulkWrite");
		normalBulkInsert(collection);
		bulkDocumentWrites(collection);
		readDocumentWithoutIndex(collection);
		readDocumentWithIndex(collection);
	}
	
	private static void normalBulkInsert(MongoCollection<Document> collection) {
		List<Document> documents = new ArrayList<>();
		long start = System.currentTimeMillis();
		for(int i=0; i< 50000 ; i++){
			Document document = new Document("name", "Thangavel" + i)
					.append("value", i)
					.append("country", "Poland")
					.append("title", "Designer")
					.append("Status", "Single")
					.append("diploma", Arrays.asList("ME","BE","MA"))
					.append("locationAxis", new Document("x",i).append("y", i));
			documents.add(document);
		}
		collection.insertMany(documents,new InsertManyOptions().ordered(false));
		long end = System.currentTimeMillis();
		System.out.println("The time taken for complete task is::"+ (end - start));
		
	}

	private static void bulkDocumentWrites(MongoCollection<Document> collection) {

		long start = System.currentTimeMillis();
		List<WriteModel<Document>> writes = new ArrayList<>();
		for (int i = 0; i < 50000; i++) {
			writes.add(new InsertOneModel<Document>(
						new Document("name", "Thangavel" + i)
						.append("value", i)
						.append("country", "Poland")
						.append("title", "Designer")
						.append("Status", "Single")
						.append("diploma", Arrays.asList("ME","BE","MA"))
						.append("locationAxis", new Document("x",i).append("y", i))
						)
					);
		}
		collection.bulkWrite(writes,new BulkWriteOptions().ordered(false));
		long end = System.currentTimeMillis();
		System.out.println("The time taken for complete task is::"+ (end - start));
	}
	
	private static void readDocumentWithoutIndex(MongoCollection<Document> collection){
		long start = System.currentTimeMillis();
		for (Document document : collection.find()) {
		}
		long end = System.currentTimeMillis();
		System.out.println("The time taken to read::"+ (end - start));
	}
	
	private static void readDocumentWithIndex(MongoCollection<Document> collection){
		long start = System.currentTimeMillis();
		collection.createIndex(Indexes.ascending("name:1"));
		
		for (Document document : collection.find()) {
		}
		
		long end = System.currentTimeMillis();
		System.out.println("The time taken to read::"+ (end - start));
	}
}
