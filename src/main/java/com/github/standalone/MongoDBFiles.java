package com.github.standalone;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.Properties;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

/**
 * MongoDBFiles class used Grid FS to store files.
 * <UL><B>Grid FS</B> stores files in two collections:
 * <LI>bucketName.chunks (GridFS divides the file into chunks that are stored as 
 * distinct documents in a chunks collection.)</LI>
 * <LI>bucketName.files GridFS stores information about stored files in file collection.
 * There will be one files collection document per stored file.</LI>
 * </UL>
 * @author yashwanth.m
 *
 */
public class MongoDBFiles {
	
	static Properties props = new Properties();
	
	static String mongoDBHost, mongoDBName, mongoDBUserName, mongoDBPassword, mongoDB_BucketName;
	static Integer mongoDBPort;
	
	static {
		ClassLoader classLoader = MongoDBFiles.class.getClassLoader();
		InputStream resourceAsStream = classLoader.getResourceAsStream("mongo.properties");
		try {
			props.load(resourceAsStream);
			mongoDBHost = props.getProperty("mongoDBHost");
			mongoDBName = props.getProperty("mongoDBName");
			mongoDBUserName = props.getProperty("mongoDBUserName");
			mongoDBPassword = props.getProperty("mongoDBPassword");
			mongoDB_BucketName = props.getProperty("mongoDB_BucketName");
			mongoDBPort = Integer.valueOf( props.getProperty("mongoDBPort") );
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String fileName = "screenshot4529721785846045902.png";// "YashTest.mp4";
		//upload( "E:\\IMediaWriterVedio7.mp4", "First");
		//download( "E:\\IMediaWriterVedio"+fileName, fileName);
		download( "E:\\IMediaWriterVedio"+fileName, fileName);
	}
	
	public static void upload(String uploadFileLocation, String fileName, String sessionid) {
		Mongo mongoClient = null;
		try {
			mongoClient = new MongoClient( mongoDBHost, mongoDBPort ); // Connect to MongoDB
			DB db = mongoClient.getDB( mongoDBName ); // Get database
			boolean auth = db.authenticate(mongoDBUserName, mongoDBPassword.toCharArray());
			System.out.println("Mongo DB Authentication >>> "+auth);
			
			if ( !auth ) {
				//Create instance of GridFS implementation
				GridFS gridFs = new GridFS(db, mongoDB_BucketName);
				GridFSInputFile gridFsInputFile = gridFs.createFile(new File(uploadFileLocation));
				gridFsInputFile.setId( sessionid );
				gridFsInputFile.setFilename(fileName); //Set a name on GridFS entry
				gridFsInputFile.save(); //Save the file to MongoDB
				System.out.println("Uploaded Successfully.");
			}
			//download( "E:\\IMediaWriterVedio"+fileName+".mp4", fileName);
			mongoClient.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if( mongoClient != null ) mongoClient.close();
		}
	}
	
	public static void download(String downloadFileLocation, String fileName) {
		Mongo mongoClient = null;
		try {
			mongoClient = new MongoClient( mongoDBHost, mongoDBPort ); // Connect to MongoDB
			DB db = mongoClient.getDB( mongoDBName ); // Get database
			boolean auth = db.authenticate(mongoDBUserName, mongoDBPassword.toCharArray());
			System.out.println("Mongo DB Authentication >>> "+auth);
			if( auth) {
				
				//Create instance of GridFS implementation
				GridFS gridFs = new GridFS(db, mongoDB_BucketName);
				GridFSDBFile outputImageFile = gridFs.findOne(fileName);
				outputImageFile.writeTo(new File( downloadFileLocation ));
				System.out.println("Downloaded Successfully.");
			}
			mongoClient.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if( mongoClient != null ) mongoClient.close();
		}
	}
	
	//		upload2( "E:\\Capture.PNG", "MyJavaFile777");
	// db.getCollection('automation-images.files').find({"_id" : ObjectId("5901d0c329ab510cac02f2cf")})
	public static void upload2(String uploadFileLocation, String fileName) {
		Mongo mongoClient = null;
		try {
			mongoClient = new Mongo( mongoDBHost, mongoDBPort ); // Connect to MongoDB
			DB db = mongoClient.getDB( mongoDBName ); // Get database
			boolean auth = db.authenticate(mongoDBUserName, mongoDBPassword.toCharArray());
			System.out.println("Mongo DB Authentication >>> "+auth);
			if ( auth ) {
				GridFS gridFs = new GridFS(db, "automation_images");
				//Create instance of GridFS implementation
				GridFSInputFile gridFsInputFile = gridFs.createFile(new File(uploadFileLocation));
				//gridFsInputFile.setId("777");
				gridFsInputFile.setFilename(fileName); //Set a name on GridFS entry
				
				String contentType = "image/png";
				gridFsInputFile.setContentType( contentType );
				gridFsInputFile.save(); //Save the file to MongoDB
				
				System.out.println("Uploaded Successfully.");
				
				// JAVA : 5903106929abbde32151cf1d
				// NODE : 5902dbeb2c48bf0813ae2e4f
				BasicDBObject query = new BasicDBObject();
				query.put("_id", new ObjectId("5902dbeb2c48bf0813ae2e4f"));
				GridFSDBFile outputImageFile = gridFs.findOne( fileName );
				System.out.println("Object ID: "+ outputImageFile.getId().toString());
				String downloadFileLocation = "E:\\Y_N_"+fileName+".png";
				outputImageFile.writeTo(new File( downloadFileLocation ));
				System.out.println("Downloaded Successfully.");
			}
			mongoClient.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if( mongoClient != null ) mongoClient.close();
		}
	}
	
	public static void insertBSONRecord() {
		Mongo mongoClient = null;
		try {
			mongoClient = new MongoClient( mongoDBHost, mongoDBPort ); // Connect to MongoDB
			DB db = mongoClient.getDB( mongoDBName ); // Get database
			DBCollection table = db.getCollection( "mydatacollection" );
			BasicDBObject document = new BasicDBObject();
			document.put("name", "Yashwanth" );
			document.put("age", "26" );
			
			table.insert(document);
			String id = document.get("_id").toString();
			System.out.println("Inserted ID : "+id);
			
			BasicDBObject document2 = new BasicDBObject("_id",new ObjectId( id ));
			
			DBCursor cursor = table.find(document2);
			if ( cursor.hasNext() ){
				DBObject next = cursor.next();
				String name = (String) next.get("name");
				System.out.println("Inserted with key ID: "+name);
			}
			
			mongoClient.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} finally {
			if( mongoClient != null ) mongoClient.close();
		}
	}
	
	public static String getBase64String(String fileName, String id) {
		//String fileName = "screenshot4529721785846045902.png";
		Mongo mongoClient = null;
		String objectID = null;
		try {
			
			mongoClient = new Mongo( mongoDBHost, mongoDBPort ); // Connect to MongoDB
			DB db = mongoClient.getDB( mongoDBName ); // Get database
			boolean auth = db.authenticate(mongoDBUserName, mongoDBPassword.toCharArray());
			System.out.println("Mongo DB Authentication >>> "+auth);
			if ( auth ) {
				Query query = new Query();
				query.addCriteria( Criteria.where("filename").is( fileName ) );
				
				//Create instance of GridFS implementation
				GridFS gridFs = new GridFS(db, mongoDB_BucketName);
				
	
				GridFSDBFile outputImageFile = gridFs.findOne(fileName);
				// { "_id" : { "$oid" : "58ecfc624c3a0eeb31842752"} , "chunkSize" : 262144 , "length" : 52021 , "md5" : "dd28069b3fedc2a81d4b4d0d46129979" , "filename" : "screenshot4922235209450374429.png" , "contentType" :  null  , "uploadDate" : { "$date" : "2017-04-11T15:55:14.518Z"} , "aliases" :  null }
				Object id2 = outputImageFile.getId();
				System.out.println("Object ID : "+ id2.toString());
				objectID = id2.toString();
			}
			mongoClient.close();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} finally {
			if( mongoClient != null ) mongoClient.close();
		}
		
		return objectID;
	}
}