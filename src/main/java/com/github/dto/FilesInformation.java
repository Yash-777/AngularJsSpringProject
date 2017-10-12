package com.github.dto;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.mongodb.BasicDBList;

@Document(collection = "CollectionData")
public class FilesInformation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private ObjectId _id;
	
	/*
	@Id private String fileID;
	@NotNull @Indexed(unique = true) private String username;
{
    "_id" : ObjectId("596f09b998609441f6c40bf5"),
    "filesCount" : 4,
    "linkedFileIDS" : [ 
        {
            "FileID" : "596f09b998609441f6c40bec",
            "FileName" : "capture.jpg"
        }, 
        {
            "FileID" : "596f09b998609441f6c40bee",
            "FileName" : "captureDownload.jpg"
        }
    ]
}
	*/
	@NotNull @Field private String filesCount;
	private Envinfo emp;
	
	@Field private BasicDBList linkedFileIDS;
	
	protected class Envinfo {
		@Field private String FileID;
		@Field private String FileName;
		
		public String getFileID() {
			return FileID;
		}
		public void setFileID(String fileID) {
			FileID = fileID;
		}
		public String getFileName() {
			return FileName;
		}
		public void setFileName(String fileName) {
			FileName = fileName;
		}
	}

	public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public String getFilesCount() {
		return filesCount;
	}

	public void setFilesCount(String filesCount) {
		this.filesCount = filesCount;
	}

	public Envinfo getEmp() {
		return emp;
	}

	public void setEmp(Envinfo emp) {
		this.emp = emp;
	}

	public BasicDBList getLinkedFileIDS() {
		return linkedFileIDS;
	}

	public void setLinkedFileIDS(BasicDBList linkedFileIDS) {
		this.linkedFileIDS = linkedFileIDS;
	}
	
	
}
