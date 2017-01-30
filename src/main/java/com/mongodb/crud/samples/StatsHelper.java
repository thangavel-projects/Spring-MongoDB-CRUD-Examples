package com.mongodb.crud.samples;

import org.bson.Document;

import com.mongodb.client.MongoDatabase;

public class StatsHelper {

	public static void main(String[] args) {
		MongoDatabase mongoDbConnection = MongoDbConnection.getMongoDbConnection();
		Document buildInfoCommand = mongoDbConnection.runCommand(new Document("buildInfo",1));
		System.out.println(buildInfoCommand.toJson());
		Document explainCommand = mongoDbConnection.runCommand(new Document("explain", new Document("count","movies")));
		System.out.println(explainCommand.toJson());
	}
}
