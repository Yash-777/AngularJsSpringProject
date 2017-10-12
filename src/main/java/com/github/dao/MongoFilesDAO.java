package com.github.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Making proper use of connection pooling can massively improve the performance of your MongoDB deployment.
 * 
 * @author yashwanth.m
 *
 */
public interface MongoFilesDAO {
	public void insertBSONRecord(String name, String age);
	public String saveFilesData( ArrayList<List<String>> fileSet );
	public ArrayList<Map<String,String>> getFilesData(String _id);
	public boolean updateFilesData(String _id, String fileName, String update_id);
}