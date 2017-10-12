package com.github.dao;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;

@Repository
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

	@Override
	public String insertManyFilesWithUniqueID(String fileDrive, String[] fileList) {
		try {
			int size = 0;
			ArrayList<List<String>> fileSet = new ArrayList<List<String>>();
			while( size < fileList.length ) {
				String fileName = fileList[size];// get file name and mime type.. 
						// put it into new object and generate new object which contains all the informatin regarding
						// all the updated files.
				ArrayList<String> fileMetaData = new ArrayList<String>();
				
				ApplicationContext context = new ClassPathXmlApplicationContext();
				Resource resource = context.getResource("file:"+fileDrive+fileName);
				System.out.println("RESOURCE FILE-Path URI « "+resource.getURI());
				// RESOURCE FILE-Path URI « file:E:/capture.jpg
				// { "filename" : "capture.jpg" , "fileMimeType" : "jpg" , "fileExtension" : "image/jpeg"}
				DBObject metaData = new BasicDBObject();
				metaData.put("filename", fileName);
				
				String extension = getFileExtension(fileName), contentType = "";
				// https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types
				if( extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpeg") ) {
					metaData.put("FileExtension", "jpg"); // JPEG Image
					contentType = "image/jpeg";
				} else if( extension.equalsIgnoreCase("png") ) {
					metaData.put("FileExtension", "png"); // Portable Network Graphics
					contentType = "image/png";
				}
				metaData.put("MIME|Internet_MediaType", contentType);
				
				String id = store(resource.getInputStream(), fileName, contentType, metaData);
				System.out.println("Find By Id ::"+id);
				
				fileMetaData.add(id);
				fileMetaData.add(fileName);
				size++;
				fileSet.add( fileMetaData );
			}
			
			if( !fileSet.isEmpty() ) {
				String mongoID = saveFilesData(fileSet);
				System.out.println("Final Mongo ID : "+ mongoID);
				return mongoID;
			}
			
		} catch( Exception e ) {
			e.printStackTrace();
		}
		return null;
	}
	public String getFileExtension( String fileName ) {
		int lastIndexOf = fileName.lastIndexOf(".");
		System.out.println("Last Index : "+lastIndexOf);
		String extension = fileName.substring(lastIndexOf+1);
		System.out.println("Extension : "+extension);
		return extension;
	}
	
	/*
Final Data : [
%TEMP%\1500458557745_capture.jpg, %TEMP%\1500458557761_captureDownload.jpg,
%TEMP%\1500458557761_Untitled.jpg, %TEMP%\1500458558089_NPMUpdate_PackageJSON.png
]
	 */
	@Override
	public ArrayList<String> downloadFilesWithUniqueID(String _id) {
		ArrayList<String> fileNames = new ArrayList<String>();
		
		String filePath = System.getProperty("java.io.tmpdir")+"/";

		ArrayList<Map<String, String>> filesData = getFilesData(_id);
		for (Map<String, String> map : filesData) {
			String fileID = map.get("FileID"), fileName = map.get("FileName");
			GridFSDBFile outputImageFile = fileRetriveById( fileID );
			
			Date date = new Date();
			long timeInMilliSeconds = date.getTime();
			System.out.println("Sec : "+timeInMilliSeconds);
			File file = new File( filePath+timeInMilliSeconds+"_"+fileName );
			
			try {
				outputImageFile.writeTo( file );
				fileNames.add( file.getAbsolutePath() );
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileNames;
	}
}