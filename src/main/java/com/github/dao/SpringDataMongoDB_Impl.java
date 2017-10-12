package com.github.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.github.dto.EmpInfo;
import com.github.dto.EmpInfo.Address;
import com.github.dto.UsersInfo;

public class SpringDataMongoDB_Impl implements SpringDataMongoDB {
	
	@Autowired @Qualifier(value="mongoTemplate")
	private MongoTemplate mongoTemplate;
	
	@Override
	public void insertRecord(UsersInfo info) {
		mongoTemplate.save(info);
	}

	@Override
	public boolean updateRecord(UsersInfo info) {
		try {
			Query query = new Query(Criteria.where("_id")
					.is(info.get_id()));
					//.is(info.getUserName()));
			
			Update update = new Update();
			update.set("email", info.getEmail());
			
			//run it!
			mongoTemplate.upsert(query, update, UsersInfo.class);
		
		} catch (Exception ex) {
			System.err.println("Unable to Update File"+ex);
			return false;
		}
		return true;
	}

	@Override
	public void insertEmpRecord(EmpInfo info) {
		mongoTemplate.save(info);
	}
/*
"address" : 
[ { "street" : "Street1", "pincode" : 505174 }, 
  { "street" : "Street2", "pincode" : 505172 }
]
Change: https://stackoverflow.com/a/35672335/5081877
Street1 = 505176
 */
	@Override
	public boolean updateEmpRecord(EmpInfo info) {
		try {
			List<Address> address = info.getAddress();
			if( address.size() >= 1 ) {
				Address add = address.get(0);
				final Query query = new Query(new Criteria().andOperator(
					Criteria.where("_id").is( info.get_id() ),
					Criteria.where("address").elemMatch(Criteria.where("street").is( add.getStreetName() ))
				));
				// .addToSet("address.$.street", "New Address Object Data")
				final Update update = new Update().set("address.$.pincode", add.getPincode() );
				
				mongoTemplate.updateFirst(query, update, EmpInfo.class);
				return true;
			}
		} catch (Exception ex) {
			System.err.println("Unable to Update File"+ex);
		}
		return false;
	}
}