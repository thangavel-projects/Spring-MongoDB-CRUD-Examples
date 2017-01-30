package com.mongodb.crud.samples;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDbConnection {

	public static final String DB_IP_ADDRESS = "127.0.0.1";
	public static final int DB_PORT = 27017;
	public static final String DB_NAME = "flim";
	
	private static volatile MongoDatabase database ;
	
	private MongoDbConnection() {
	}
	
	@SuppressWarnings("resource")
	public static MongoDatabase getMongoDbConnection() {
		synchronized (MongoDbConnection.class) {
			if(database == null){
				MongoClient mongoClient = new MongoClient(DB_IP_ADDRESS,DB_PORT);
				//If the database not exist in DB, then it will create it for you.
				database = mongoClient.getDatabase("flim");
				return database;
			}else{
				return database;
			}
		}
	}
}
