package com.mongodb.crud.samples;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;

public class GridFSExamples {
	
	public static void main(String[] args) {
		
		GridFSExamples fsExamples = new GridFSExamples();
		ObjectId storeFile = fsExamples.storeFile();
		fsExamples.readFile(storeFile);
	}
	
	@SuppressWarnings("resource")
	private ObjectId storeFile(){
		ObjectId fileId = null;
		MongoDatabase database = new MongoClient().getDatabase("sampleGridFS");
		GridFSBucket bucket = GridFSBuckets.create(database);
		try {
			ByteArrayInputStream byteStream = new ByteArrayInputStream(FileUtils.readFileToByteArray(new File("<input file path>")));
			GridFSUploadOptions options = new GridFSUploadOptions().chunkSizeBytes(200)
					.metadata(new Document("type", "samples"));
			fileId = bucket.uploadFromStream("mongodb-fs-samples", byteStream, options);
			byteStream.close();
			System.out.println("The file has been upload successfully with id:"+fileId);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileId;
	}
	
	@SuppressWarnings("resource")
	private void readFile(ObjectId storeFile){
		MongoDatabase database = new MongoClient().getDatabase("sampleGridFS");
		GridFSBucket bucket = GridFSBuckets.create(database);
		try {
			FileOutputStream downloadFile = new FileOutputStream("<output file path>");
			bucket.downloadToStream(storeFile, downloadFile);
			downloadFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
}