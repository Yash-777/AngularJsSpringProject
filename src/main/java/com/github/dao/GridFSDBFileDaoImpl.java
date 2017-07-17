package com.github.dao;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;

import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;

public class GridFSDBFileDaoImpl extends MongoFilesDAO_Impl implements GridFSDBFileDao {
	
	@Autowired
	GridFsTemplate gridFsTemplate;
	
	@Override
	public String store(InputStream inputStream, String fileName, String contentType, DBObject metaData) {
		try {
			return this.gridFsTemplate.store(inputStream, fileName, contentType, metaData).getId().toString();
		} catch (Exception ex) {
			System.err.println("Unable to Store File : "+ex);
			return null;
		}
	}

	@Override
	public GridFSDBFile fileRetriveById(String id) {
		try {
			Query query = new Query(Criteria.where(CollectionKey.ID).is( new ObjectId(id) ));
			//return this.gridFsTemplate.findOne(query);
			return this.gridFsTemplate.findOne(query);
		} catch (Exception ex) {
			System.err.println("Unable to Retrive File : "+ex);
			return null;
		}
	}

	@Override
	public GridFSDBFile fileRetriveByFileName(String fileName) {
		try {
			Query query = new Query(Criteria.where(CollectionKey.FILE_NAME).is(fileName));
			return gridFsTemplate.findOne( query );
		} catch (Exception ex) {
			System.err.println("Unable to Retrive File : "+ex);
			return null;
		}
	}

	@Override
	public boolean deleteFileByGridFsId(String id) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where(CollectionKey.ID).is( new ObjectId(id) ));
			this.gridFsTemplate.delete(query);
		} catch (Exception ex) {
			System.err.println("Unable to Delete File"+ex);
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}
	
	@Override
	public int getFileNameRecordCount(String fileName) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where(CollectionKey.FILE_NAME).is( fileName ));
			Object[] array = this.gridFsTemplate.find(query).toArray();
			return array.length;
		} catch (Exception ex) {
			System.err.println("Unable to get File Duplicate Names : "+ex);
		}
		return 0;
	}
	
	@Override
	public int findAllRecordsCount() {
		try {
			Object[] array = this.gridFsTemplate.find(null).toArray();
			return array.length;
		} catch (Exception ex) {
			System.err.println("Unable to get All Records Count : "+ex);
		}
		return 0;
	}
	
	@Override
	public List<GridFSDBFile> findAll(){
		try {
			return this.gridFsTemplate.find(null);
		} catch (Exception ex) {
			System.err.println("Unable to get all records : "+ex);
		}
		return null;
	}
	/*public Boolean updateFileName(String oldName, String newName) {
		try {
			Query query = new Query();
			query.addCriteria(Criteria.where("filename").is(oldName));
			
			Update update = new Update();
			update.set("filename", newName);
			
			this.gridFsTemplate.find(query).updateFirst(query, update);
			
		} catch (Exception ex) {
			System.err.println("Unable to Delete File"+ex);
			return Boolean.FALSE;
		}
		return Boolean.TRUE;
	}*/
	
	@Override
	public String getBase64ByFileName( String fileName ) {
		try{
			Query query = new Query();
			query.addCriteria( Criteria.where( CollectionKey.FILE_NAME ).is( fileName ) );
			//Query query=new Query(Criteria.where("_id").is(new ObjectId(id)));
			String downloadFileLocation = "E://Y_Images.png";
			// Query: { "filename" : "MyFile_DateTIME.png"}, Fields: null, Sort: null
			System.out.println("gridFsTemplate « "+gridFsTemplate);
			GridFSDBFile file = gridFsTemplate.findOne(query);
			
			File image = new File( downloadFileLocation );
			file.writeTo( image );
			System.out.println("Downloaded Successfully.");
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			//ImageIO.write(image, ".png", baos);
			file.writeTo( baos );
			byte[] imageData = baos.toByteArray();
		
			// https://mvnrepository.com/artifact/commons-codec/commons-codec
			String encodedfile = new String(org.apache.commons.codec.binary.Base64.encodeBase64( imageData ), "UTF-8");
			return encodedfile;
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}