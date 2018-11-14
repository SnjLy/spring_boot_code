package com.yehao.boot.model;

import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * @Package: com.yehao.boot.model
 * @Description:
 * @function:
 * @Author : LiuYong
 * Created by yehao on 2018/11/2.
 */
@Document(indexName = "bank", type = "account")
public class BankModel implements Serializable{
    private Long id;
    private Long account_number;
    private Double balance;
    private String firstname;
    private String lastname;
    private int age;
    private GENDER gender;
    private String address;
    private String employer;
    private String email;
    private String city;
    private String state;
    private Integer _score;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccount_number() {
        return account_number;
    }

    public void setAccount_number(Long account_number) {
        this.account_number = account_number;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public GENDER getGender() {
        return gender;
    }

    public void setGender(GENDER gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer get_score() {
        return _score;
    }

    public void set_score(Integer _score) {
        this._score = _score;
    }

    enum GENDER{
        MAN("M"),
        FEMALE("F");

        private String type;
        GENDER(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
