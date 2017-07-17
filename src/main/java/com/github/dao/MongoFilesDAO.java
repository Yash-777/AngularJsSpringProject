package com.github.dao;

import java.util.ArrayList;
import java.util.List;

public interface MongoFilesDAO {
	public void insertBSONRecord(String name, String age);
	public String saveFilesData( ArrayList<List<String>> fileSet );
}