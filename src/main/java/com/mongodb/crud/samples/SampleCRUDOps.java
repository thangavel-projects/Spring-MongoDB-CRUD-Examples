package com.mongodb.crud.samples;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

public class SampleCRUDOps {
    
	public static void main(String[] args) throws UnknownHostException {
		MongoClient client = new MongoClient();
		DB db = client.getDB("Thangavel");
		DBCollection collection = db.getCollection("CRUDSamples");
		//collection.drop();
		insertDocument(collection);
		readDocument(collection);
		
	}
	
	private static void insertDocument(DBCollection collection) throws UnknownHostException{
		
		DBObject dbObject = (DBObject) JSON.parse("{'Name':'Thangavel', 'Age':30, 'Address':'India'}");
		DBObject OneDBObject = (DBObject) JSON.parse("{'Name':'Loganathan', 'Age':23, 'Address':'US'}");
		List<DBObject> dbObjects = new ArrayList<>();
		dbObjects.add(dbObject);
		dbObjects.add(OneDBObject);
		
		collection.insert(dbObjects);
		
	}
	
	private static void readDocument(DBCollection collection){
		DBCursor find = collection.find();
		while (find.hasNext()) {
			DBObject dbObject = (DBObject) find.next();
			System.out.println(dbObject.toMap());
		}
	}
}
