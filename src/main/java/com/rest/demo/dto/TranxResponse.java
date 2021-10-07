package com.rest.demo.dto;

import com.rest.demo.model.Transaction;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class TranxResponse {
    private long accountNumber;
    private String accountHolder;
    private Date dob;
    private String accountType;
    private long balance;
    private Timestamp fromDate;
    private Timestamp toDate;
    private List<Transaction> transactionList;

    public TranxResponse() {
    }

    public TranxResponse(long accountNumber, String accountHolder, Date dob, String accountType, long balance, Timestamp fromDate,
                         Timestamp toDate, List<Transaction> transactionList) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.dob = dob;
        this.accountType = accountType;
        this.balance = balance;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.transactionList = transactionList;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    public Timestamp getFromDate() {
        return fromDate;
    }

    public void setFromDate(Timestamp fromDate) {
        this.fromDate = fromDate;
    }

    public Timestamp getToDate() {
        return toDate;
    }

    public void setToDate(Timestamp toDate) {
        this.toDate = toDate;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }
}
