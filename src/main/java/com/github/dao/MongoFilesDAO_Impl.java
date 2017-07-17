package com.github.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.json.JSONException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@Repository
public class MongoFilesDAO_Impl implements MongoFilesDAO {
	
	public MongoFilesDAO_Impl() {
		System.out.println("MongoFilesDAO_Impl :: ");
	}
	
	private String collectionName;
	public String getCollectionName() {
		return collectionName;
	}
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	//@Autowired @Qualifier(value="mongoTemplate")
	private MongoTemplate mongoTemplate;
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		System.out.println("Mongo Template : "+mongoTemplate);
		this.mongoTemplate = mongoTemplate;
	}
	
	@Override
	public void insertBSONRecord(String name, String age) {
		try {
			DBCollection table = mongoTemplate.getCollection( collectionName );
			BasicDBObject document = new BasicDBObject();
			document.put("name", name );
			document.put("age", age );
			
			table.insert(document);
			String id = document.get( CollectionKey.ID ).toString();
			System.out.println("Inserted ID : "+id);
			
			BasicDBObject document2 = new BasicDBObject(CollectionKey.ID,new ObjectId( id ));
			
			DBCursor cursor = table.find(document2);
			if ( cursor.hasNext() ){
				DBObject next = cursor.next();
				String userName = (String) next.get("name");
				System.out.println("Inserted with key ID: "+userName);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String saveFilesData( ArrayList<List<String>> fileSet ) {
		try {
			DBCollection table = mongoTemplate.getCollection( collectionName );
			
			DBObject document = new BasicDBObject();
			document.put("filesCount", fileSet.size() );
			document.put("linkedFileIDS", AL2Jaon_Format(fileSet) );
			
			table.insert(document);
			String id = document.get( CollectionKey.ID ).toString();
			System.out.println("Inserted ID : "+id);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public BasicDBList AL2Jaon_Format(ArrayList<List<String>> fileSet) throws JSONException{
		BasicDBList finalArray = new BasicDBList();
		
		for (List<String> list : fileSet) {
			DBObject obj = new BasicDBObject();
			obj.put("FileID", list.get(0));
			obj.put("FileName", list.get(1));
			finalArray.add(obj);
		}
		System.out.println("JSON Array : \n"+finalArray);
		return finalArray;
	}
}