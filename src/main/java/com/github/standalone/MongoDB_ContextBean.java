package com.github.standalone;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.bson.types.ObjectId;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.github.dao.GridFSDBFileDao;
import com.github.dao.MongoFilesDAO;
import com.github.dao.SpringDataMongoDB;
import com.github.dao.SpringDataMongoDB_Impl;
import com.github.dto.EmpInfo;
import com.github.dto.UsersInfo;
import com.github.dto.EmpInfo.Address;
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
		
		SpringDataMongoDB mongo = (SpringDataMongoDB) context.getBean("springDataMongo");
		
		UsersInfo usersPojo = new UsersInfo();
		usersPojo.setUserName("Yash");
		usersPojo.setEmail("Yashwanth.m@gmail.com");
		//mongo.insertRecord( usersPojo );
		//System.out.println("_id : "+usersPojo.get_id());
		
		usersPojo.setEmail("Yashwanth777.@gmail.com");
		//mongo.updateRecord(usersPojo);
		
		EmpInfo emp = new EmpInfo();
		emp.setEmpId(777);
		emp.setUserName("Yash");
		
		Address add = new EmpInfo().new Address();
		add.setPincode(505174);
		add.setStreetName("Street1");
		
		Address add2 = new EmpInfo().new Address();
		add2.setPincode(505172);
		add2.setStreetName("Street2");
		
		List<Address> address = new ArrayList<Address>();
		address.add(add);
		address.add(add2);
		
		emp.setAddress(address);
		//mongo.insertEmpRecord( emp );
		System.out.println("_id : "+emp.get_id());
		
		emp.set_id( new ObjectId("596f797c986068870e3e4af6") );
		add.setPincode(505176);
		mongo.updateEmpRecord( emp );
	}
	
	static void mongoOperations() {
		//MongoFilesDAO mongo = (MongoFilesDAO) context.getBean("mongoDao");
		//mongo.insertBSONRecord("Yashwanth", "26");
		
		/*GridFSDBFileDao gridFSDBFileDao = (GridFSDBFileDao) context.getBean("mongoGridFSDao");
		gridFSDBFileDao.insertBSONRecord("Yashwanth", "26");*/
		
		//String fileDrive = "E:/", fileName = "capture.jpg", writeToFile = "captureDownload.jpg";
		//gridOperations( fileDrive, fileName, writeToFile );
		
		//String[] fileList = {fileName, writeToFile, "Untitled.jpg", "NPMUpdate_PackageJSON.png"};
		/*
		String mongoID = gridFSDBFileDao.insertManyFilesWithUniqueID(fileDrive, fileList);
		System.out.println("Final ID : "+ mongoID);*/
		
		//readFiles();
		
		/*ArrayList<String> data = gridFSDBFileDao.downloadFilesWithUniqueID("596f09b998609441f6c40bf5");
		System.out.println("Final Data : "+data);*/
		
		//deleteFileByGridFsId("596f09b998609441f6c40bf5");
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
	
	public static void readFiles() {
		try { // https://stackoverflow.com/q/22096983/5081877
		
		ApplicationContext context = new ClassPathXmlApplicationContext();
		Resource resource = context.getResource("file:E:/capture.jpg");
		System.out.println("RESOURCE FILE-Path URI « "+resource.getURI());
		// RESOURCE FILE-Path URI « file:E:/capture.jpg
		
		// /src/main/resources maven directory contents are placed in the root of your CLASSPATH
		// CLASS-Path URI « file:/D:/{workspace}/{projectName}/target/classes/mongo.properties
		ClassLoader WebappClassLoader = Thread.currentThread().getContextClassLoader();
		Resource resourceClass = context.getResource("classpath:mongo.properties");
		System.out.println("Class Loader URI « "+WebappClassLoader.getResource("mongo.properties").toURI());
		System.out.println("RESOURCE CLASS-Path URI « "+resourceClass.getURI());
		
		Properties props = new Properties();
		props.load(WebappClassLoader.getResourceAsStream("mongo.properties"));
		System.out.println("Properties : "+props);
		
		// Class Loader « {projectName}/src/main/resources/mongo.properties
		
		// Context - org.springframework.core.io.support.ResourcePatternResolver
		System.out.println("Context - [classpath*:] « "+ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX);
		System.out.println("Context - [classpath:] « "+ResourceLoader.CLASSPATH_URL_PREFIX);
		System.out.println("Context - [&] « "+BeanFactory.FACTORY_BEAN_PREFIX);
		
		Resource resourceContext = context.getResource("context:/wiki.txt");
		System.out.println("RESOURCE Context-Path URI « "+resourceContext.getURI());
		
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}