package com.datasorce.storage.dao.search.entity;

import java.util.Date;

public class RecommendWord {
	private String word;
	private int num;
	private Date maxTime;
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public Date getMaxTime() {
		return maxTime;
	}
	public void setMaxTime(Date maxTime) {
		this.maxTime = maxTime;
	}
	
	
}
