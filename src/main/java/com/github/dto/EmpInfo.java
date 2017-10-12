
package com.github.dto;

import java.util.List;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

// https://stackoverflow.com/questions/1612334/difference-between-dto-vo-pojo-javabeans
@Document(collection = "empData")
public class EmpInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private ObjectId _id;
	
	@NotNull @Field
	private int empid;
	
	@NotNull @Field(value = "empName")
	private String userName;
	
	@Field(value = "address")
	private List<Address> address;
	
	public List<Address> getAddress() {
		return address;
	}
	public void setAddress(List<Address> address) {
		this.address = address;
	}
	
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}

	public int getEmpId() {
		return empid;
	}
	public void setEmpId(int empid) {
		this.empid = empid;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public class Address {
		@Field(value = "street")
		private String streetName;
		
		@Field
		private int pincode;
		
		public String getStreetName() {
			return streetName;
		}
		public void setStreetName(String streetName) {
			this.streetName = streetName;
		}

		public int getPincode() {
			return pincode;
		}
		public void setPincode(int pincode) {
			this.pincode = pincode;
		}
	}
}
