package com.mongodb.crud.samples;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;

public class SampleCRUDOps {
    
	public static void main(String[] args) throws UnknownHostException {
		@SuppressWarnings("resource")
		MongoClient client = new MongoClient();
		MongoDatabase db = client.getDatabase("Thangavel");
		MongoCollection<Document> collection = db.getCollection("CRUDSamples");
		collection.drop();
		insertDocuments(collection);
		readDocuments(collection);
		readDocumentFeatures(collection);
		
	}
	
	private static void readDocumentFeatures(MongoCollection<Document> collection) {
		// Sorting Document
		System.out.println("###SORTED DOCUMENT###");
		Block<Document> document = new Block<Document>() {
			@Override
			public void apply(Document doc) {
				System.out.println(doc.toJson());
			}
		};
		collection.find(Filters.exists("Age")).sort(Sorts.ascending("Address")).forEach(document);
		
		// Use Projections
		MongoCursor<Document> projections = collection.find().projection(Projections.include("Address","Age"))
				.iterator();
		
		System.out.println("###Use Projections#####");
		while (projections.hasNext()) {
			System.out.println(projections.next().toJson());
		}
		
		// Use Filters
		System.out.println("Use of Filters@@@@@");
		Bson filter = Filters.and(Filters.lte("Age", "24"),Filters.eq("Address", "Muthur"));
		//Bson projection = new Document("Name",1).append("_id", 0);
		
		Bson project = Projections.fields(Projections.include("Age","Name"),Projections.excludeId());
		
		
		List<Document> filteredDocument = collection.find(filter)
				.projection(project)
				.into(new ArrayList<Document>());
		for (Document newDoc : filteredDocument) {
			System.out.println(newDoc.toJson());
		}
		
	}

	private static void insertDocuments(MongoCollection<Document> collection) throws UnknownHostException{
		
		Document singleDocument = new Document("Name", "Thangavel")
				.append("Age", "23")
				.append("Address", "India");
		
		// Insert Single Document
		collection.insertOne(singleDocument);
		
		Document multiDocumentOne = new Document("Name", "Selvakumar")
				.append("Age", "25")
				.append("Address", "Thennilai");
		
		Document multiDocumentTwo = new Document("Name", "Karthik")
				.append("Age", "24")
				.append("Address", "Muthur");
		
		// Insert Multiple Document
		collection.insertMany(Arrays.asList(multiDocumentOne,multiDocumentTwo));
		
	}
	
	private static void readDocuments(MongoCollection<Document> collection){
		
		Document first = collection.find().first();
		// Print First Document Document
		System.out.println(first.toJson());
		
		// Iterate All Document using Cursor
		MongoCursor<Document> cursor = collection.find().iterator();
		System.out.println("##Iterating Using Cursor##");
		
		try {
			while (cursor.hasNext()) {
				System.out.println(cursor.next().toJson());
			} 
		} finally {
			// Require to close cursor always
			cursor.close();
		}
		
		// Read Document using Filters
		System.out.println("##Iterating through Filters##");
		Document find = collection.find(Filters.eq("Age", "24")).first();
		System.out.println(find.toJson());
		
		System.out.println("##Iterating through Filters with Multiple Conditions##");
		MongoCursor<Document> findDoc = collection.find(Filters.or(Filters.gt("Age", "24"),
				Filters.eq("Address", "Muthur"))).iterator();
		while(findDoc.hasNext()){
			System.out.println(findDoc.next().toJson());
		}
		
		// Use large set of Query
		System.out.println("$$$$Large Set of Queries$$$$");
		Block<Document> document = new Block<Document>() {
			@Override
			public void apply(Document doc) {
				System.out.println(doc.toJson());
			}
		};
		collection.find().forEach(document);
		
	}
}
