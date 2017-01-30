package com.mongodb.crud.samples;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.ValidationOptions;

public class MongoDBIndexSamples {

	public static void main(String[] args) {
		MongoDatabase database = MongoDbConnection.getMongoDbConnection();

		MongoCollection<Document> purgeCollection = database.getCollection("indexing");
		purgeCollection.drop();
		
		MongoCollection<Document> collection = database.getCollection("indexing");
		singleAndCompoundIndex(collection);
		
		validateDocument(database);
	}

	/**
	 * The validateDocument Method is an example for validating documents with few rules.
	 * @param database
	 */
	private static void validateDocument(MongoDatabase database) {
		
		ValidationOptions validationOptions = new ValidationOptions()
				.validator(Filters.or(Filters.exists("email"),Filters.exists("phone")));
		
		MongoCollection<Document> deleteColl = database.getCollection("validateCollSample");
		deleteColl.drop();
		
		database.createCollection("validateCollSample",new CreateCollectionOptions().validationOptions(validationOptions));
		
		Document document = new Document("name","Thangavel")
				.append("email", "Germany@de.co.in");
		
		MongoCollection<Document> collection = database.getCollection("validateCollSample");
		collection.insertOne(document);
		
	}
	
	
	/**
	 * The singleAndCompoundIndex method for creating single and compound index examples.
	 * @param collection
	 */
	private static void singleAndCompoundIndex(MongoCollection<Document> collection) {
		Document document = new Document("empName", "Thangavel").append("empDept", "CS").append("location", "Poland");
		collection.insertOne(document);
		
		// Create Index with Ascending Order
		collection.createIndex(Indexes.ascending("empName"));
		
		// Create Compound Index with Ascending and Descending order
		collection.createIndex(Indexes.compoundIndex(Indexes.ascending("empName"),Indexes.descending("empDept")));
		
		for (Document data : collection.find()) {
			System.out.println(data.toJson());
		}
	}
}
