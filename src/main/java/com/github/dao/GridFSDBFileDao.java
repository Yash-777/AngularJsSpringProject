package com.github.dao;

import java.io.InputStream;
import java.util.List;

import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;

public interface GridFSDBFileDao extends MongoFilesDAO {
	public String store(InputStream inputStream, String fileName, String contentType, DBObject metaData);
	public GridFSDBFile fileRetriveById(String id);
	public GridFSDBFile fileRetriveByFileName(String filename);
	public boolean deleteFileByGridFsId(String id);
	
	public int getFileNameRecordCount(String fileName);
	
	public int findAllRecordsCount();
	public List<GridFSDBFile> findAll();
	
	public String getBase64ByFileName(String fileName);
}