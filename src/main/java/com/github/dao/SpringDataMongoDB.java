package com.github.dao;

import com.github.dto.EmpInfo;
import com.github.dto.UsersInfo;

public interface SpringDataMongoDB {
	public void insertRecord(UsersInfo info);
	public boolean updateRecord(UsersInfo info);
	
	public void insertEmpRecord(EmpInfo info);
	public boolean updateEmpRecord(EmpInfo info);
	/*public String saveFilesData( ArrayList<List<String>> fileSet );
	public ArrayList<Map<String,String>> getFilesData(String _id);
	public boolean updateFilesData(String _id, String fileName, String update_id);*/
}
