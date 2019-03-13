package com.github.dao;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;

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
	
	@Autowired @Qualifier(value="mongoTemplate")
	private MongoTemplate mongoTemplate;
	@Autowired @Qualifier(value="gridFsTemplate")
	private GridFsTemplate gridFsTemplate;
	@Autowired @Qualifier(value="gridFsTemplate_Video")
	private	GridFsTemplate	gridFsVideoTemplate;
	
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
	
	/*
[ 
  { "FileID" : "596f09b998609441f6c40bec" , "FileName" : "capture.jpg"},
  { "FileID" : "596f09b998609441f6c40bee" , "FileName" : "captureDownload.jpg"},
  { "FileID" : "596f09b998609441f6c40bf0" , "FileName" : "Untitled.jpg"},
  { "FileID" : "596f09b998609441f6c40bf3" , "FileName" : "NPMUpdate_PackageJSON.png"}
]
	 */
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
	@Override
	public ArrayList<Map<String,String>> getFilesData(String _id) {
		
		try {
			DBCollection table = mongoTemplate.getCollection( collectionName );
			BasicDBObject document = new BasicDBObject(CollectionKey.ID,new ObjectId( _id ));
			
			DBCursor cursor = table.find(document);
			if ( cursor.hasNext() ){
				DBObject next = cursor.next();
				BasicDBList dblist = (BasicDBList) next.get("linkedFileIDS");
				System.out.println("JSON Data : \n"+dblist);
				return jsonTo_ArrayList(dblist);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	@Override
	public boolean updateFilesData(String _id, String fileName, String update_id) {
		try {
			DBCollection table = mongoTemplate.getCollection( collectionName );
			BasicDBObject document = new BasicDBObject(CollectionKey.ID,new ObjectId( _id ));
			DBCursor cursor = table.find(document);
			if ( cursor.hasNext() ){
				DBObject next = cursor.next();
				BasicDBList dblist = (BasicDBList) next.get("linkedFileIDS");
				System.out.println("JSON Data : \n"+dblist);
				
				for (int index = 0; index < dblist.size(); index++) {
					BasicDBObject map = (BasicDBObject) dblist.get(index);
					String file_Name = (String) map.get("FileName");
					if ( file_Name.equalsIgnoreCase(fileName) ) {
						/*BasicDBList updatedList = new BasicDBList( dblist );
						table.update(dblist, dblist.get(index))*/
						return true;
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
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
	@SuppressWarnings("unchecked")
	public static ArrayList<Map<String,String>> jsonTo_ArrayList( BasicDBList dblist ) {
		ArrayList<Map<String,String>> fileData = new ArrayList<Map<String,String>>();
		
		for (Object objString : dblist) {
			HashedMap fileinfo = new HashedMap();
			BasicDBObject object = (BasicDBObject) objString;
			fileinfo.put("FileID", object.get("FileID"));
			fileinfo.put("FileName", object.get("FileName"));
			System.out.println("File Info : "+fileinfo);
			fileData.add(fileinfo);
		}
		return fileData;
	}
	
	@Override
	public InputStream getImageStream(String fileObjectID) {
		Query query = new Query();
		
		ObjectId objectId = new ObjectId( fileObjectID );
		System.out.println("objectId" +objectId);
		
		query.addCriteria( Criteria.where("_id").is(objectId) );
		
		GridFSDBFile file = gridFsTemplate.findOne(query);
		System.out.println("file ::: "+file );
		InputStream inputStream = file.getInputStream();
		System.out.println("inputStream "+inputStream);
		return inputStream;
	}
	
	@Override
	public InputStream getVideoStream(String fileID) {
		Query query = new Query();
		
		query.addCriteria( Criteria.where("_id").is( fileID ) );
		
		GridFSDBFile file = gridFsVideoTemplate.findOne(query);
		System.out.println("file ::: "+file );
		InputStream inputStream = file.getInputStream();
		System.out.println("inputStream "+inputStream);
		return inputStream;
	}
}