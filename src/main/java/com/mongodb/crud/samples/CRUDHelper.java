package com.mongodb.crud.samples;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

public class CRUDHelper {

	// Insert Multiple Document
	protected static void insertMany(MongoCollection<Document> collection){
		
		Document document = new Document("name","Selvakumar")
				.append("country", "Poland")
				.append("title", "Designer")
				.append("Status", "Single");
		
		Document document1 = new Document("name","Karthi")
				.append("country", "London")
				.append("title", "Accoutant")
				.append("Status", "Married");
		
		List<Document> list = new ArrayList<>();
		list.add(document);
		list.add(document1);
		
		collection.insertMany(list);
		
	}
}
