package com.datasorce.storage.dao.hlj.entity;

import java.util.Date;

public class UserCollect {
	private String collectId;
	private String userId;
	private int collType;
	private String collectObject;
	private String collectDesc;
	private Date createTime;
	public String getCollectId() {
		return collectId;
	}
	public void setCollectId(String collectId) {
		this.collectId = collectId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public int getCollType() {
		return collType;
	}
	public void setCollType(int collType) {
		this.collType = collType;
	}
	public String getCollectObject() {
		return collectObject;
	}
	public void setCollectObject(String collectObject) {
		this.collectObject = collectObject;
	}
	public String getCollectDesc() {
		return collectDesc;
	}
	public void setCollectDesc(String collectDesc) {
		this.collectDesc = collectDesc;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
