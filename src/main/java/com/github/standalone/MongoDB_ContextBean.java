package com.github.standalone;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;

import com.github.dao.GridFSDBFileDao;
import com.github.dao.MongoFilesDAO;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;

/**
 * StandAlone application to communicate with DataBase.
 * 
 * https://github.com/mustafamym/spring-data-mongodb-GridFS-CRUD
 * @author yashwanth.m
 *
 */
public class MongoDB_ContextBean {
	static ApplicationContext context = 
			new ClassPathXmlApplicationContext("webApplicationContext/Spring-MongoDB_1.2.0.xml");

	public static void main(String[] args) {
		//mongoOperations();
		
		String fileDrive = "E:/", fileName = "capture.jpg", writeToFile = "captureDownload.jpg";
		//gridOperations( fileDrive, fileName, writeToFile );
		
		String[] fileList = {fileName, writeToFile, "Untitled.jpg", "NPMUpdate_PackageJSON.png"};
		String mongoID = insertManyFilesWithUniqueID(fileDrive, fileList);
		System.out.println("Final ID : "+ mongoID);
	}
	
	public static void mongoOperations() {
		MongoFilesDAO mongo = (MongoFilesDAO) context.getBean("mongoDao");
		mongo.insertBSONRecord("Yashwanth", "26");
	}
	
	public static String insertManyFilesWithUniqueID( String fileDrive, String[] fileList ) {
		try {
			GridFSDBFileDao gridFSDBFileDao = (GridFSDBFileDao) context.getBean("mongoGridFSDao");
			int size = 0;
			ArrayList<List<String>> fileSet = new ArrayList<List<String>>();
			while( size < fileList.length ) {
				String fileName = fileList[size];// get file name and mime type.. 
						// put it into new object and generate new object which contains all the informatin regarding
						// all the updated files.
				ArrayList<String> fileMetaData = new ArrayList<String>();
				
				Resource resource = context.getResource("file:"+fileDrive+fileName);
				// { "filename" : "capture.jpg" , "fileMimeType" : "jpg" , "fileExtension" : "image/jpeg"}
				DBObject metaData = new BasicDBObject();
				metaData.put("filename", fileName);
				
				String extension = getFileExtension(fileName), contentType = "";
				// https://developer.mozilla.org/en-US/docs/Web/HTTP/Basics_of_HTTP/MIME_types/Complete_list_of_MIME_types
				if( extension.equalsIgnoreCase("jpg") || extension.equalsIgnoreCase("jpg") ) {
					metaData.put("FileExtension", "jpg"); // JPEG Image
					contentType = "image/jpeg";
				} else if( extension.equalsIgnoreCase("png") ) {
					metaData.put("FileExtension", "png"); // Portable Network Graphics
					contentType = "image/png";
				}
				metaData.put("MIME|Internet_MediaType", contentType);
				
				String id = gridFSDBFileDao.store(resource.getInputStream(), fileName, contentType, metaData);
				System.out.println("Find By Id ::"+id);
				
				fileMetaData.add(id);
				fileMetaData.add(fileName);
				size++;
				fileSet.add( fileMetaData );
			}
			
			if( !fileSet.isEmpty() ) {
				String mongoID = gridFSDBFileDao.saveFilesData(fileSet);
				System.out.println("Final Mongo ID : "+ mongoID);
				return mongoID;
			}
			
		} catch( Exception e ) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getFileExtension( String fileName ) {
		int lastIndexOf = fileName.lastIndexOf(".");
		System.out.println("Last Index : "+lastIndexOf);
		String extension = fileName.substring(lastIndexOf+1);
		System.out.println("Extension : "+extension);
		return extension;
	}
	
	public static void gridOperations( String fileDrive, String fileName, String writeToFile ) {
		InputStream inputStream = null;
		try {
			GridFSDBFileDao gridFSDBFileDao = (GridFSDBFileDao) context.getBean("mongoGridFSDao");
			// URL [file:E:/capture.jpg]
			Resource resource = context.getResource("file:"+fileDrive+fileName);
			// { "filename" : "capture.jpg" , "fileMimeType" : "jpg" , "fileExtension" : "image/jpeg"}
			DBObject metaData = new BasicDBObject();
			metaData.put("filename", fileName);
			metaData.put("fileMimeType", "jpg");
			metaData.put("fileExtension", "image/jpeg");

			String id = gridFSDBFileDao.store(resource.getInputStream(), fileName, "image/jpeg", metaData);
			System.out.println("Find By Id ::"+id);
			
			GridFSDBFile byId = gridFSDBFileDao.fileRetriveById(id);
			System.out.println("File Name:- " + byId.getFilename());
			System.out.println("Content Type:- " + byId.getContentType());
			byId.writeTo( fileDrive+"FileID_"+writeToFile );

			System.out.println("Find By Filename ::"+ fileDrive+fileName );
			GridFSDBFile getFileByName = gridFSDBFileDao.fileRetriveByFileName( fileName );
			System.out.println("File Name:- " + getFileByName.getFilename());
			System.out.println("Content Type:- " + getFileByName.getContentType());
			getFileByName.writeTo( fileDrive+"FileName_"+writeToFile );
			
			/*boolean isDeleted = gridFSDBFileDao.deleteFileByGridFsId(id);
			System.out.println("File Deleted. "+isDeleted);*/
			
			int fileNameRecordCount = gridFSDBFileDao.getFileNameRecordCount(fileName);
			System.out.println("Duplicate FileName Reords Count : "+fileNameRecordCount);
			
			int recordsCount = gridFSDBFileDao.findAllRecordsCount();
			System.out.println("Total Records Count in a Bucket: "+recordsCount);
			
			System.out.println("List All Files...");
			for (GridFSDBFile file : gridFSDBFileDao.findAll()) {
				System.out.println("-----     -----     -----");
				System.out.println("File Name:- " + file.getFilename());
				System.out.println("Content Type:- " + file.getContentType());
				System.out.println("Meta Data Brand:- " + file.getMetaData().get("fileMimeType"));
			}
		} catch (BeansException e) {
			System.out.println("BeansException:-" + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException:-" + e.getMessage());
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					System.out.println("IOException Finally:-" + e.getMessage());
				}
			}
		}
	}
	
	public String getBase64String( String fileLocation ) {
		try{
			File file = new File( fileLocation );
			DataInputStream dis = new DataInputStream(new FileInputStream(file));
			byte[] byteArray = new byte[(int) file.length()];
			
			try {
				dis.readFully(byteArray); // now the array contains the image
			} catch (Exception e) {
				byteArray = null;
			} finally {
				dis.close();
			}
			String encodedfile = 
					new String(org.apache.commons.codec.binary.Base64.encodeBase64( byteArray ), "UTF-8");
			return encodedfile;
		} catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}