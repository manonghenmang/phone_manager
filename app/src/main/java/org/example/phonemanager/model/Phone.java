package org.example.phonemanager.model;

import java.io.Serializable;
import java.util.Date;

public class Phone implements Serializable {
    private int id;
    private String phoneId;
    private String owner;
    private String brand;
    private String model;
    private String status;
    private String borrower;
    private String lender;
    private Date lendDate;
    private Date returnDate;
    private String returnPerson;

    public Phone() {
    }

    public Phone(String phoneId, String owner, String brand, String model) {
        this.phoneId = phoneId;
        this.owner = owner;
        this.brand = brand;
        this.model = model;
        this.status = "available";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(String phoneId) {
        this.phoneId = phoneId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String getLender() {
        return lender;
    }

    public void setLender(String lender) {
        this.lender = lender;
    }

    public Date getLendDate() {
        return lendDate;
    }

    public void setLendDate(Date lendDate) {
        this.lendDate = lendDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getReturnPerson() {
        return returnPerson;
    }

    public void setReturnPerson(String returnPerson) {
        this.returnPerson = returnPerson;
    }
}