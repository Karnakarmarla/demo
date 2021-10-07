package com.rest.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountNumber;
    private String accountHolder;
    private Date dob;
    private String accountType;
    private int transactionFee;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    @OneToMany( targetEntity=Transaction.class,cascade = CascadeType.ALL)
    @JoinColumn(name="at_fk",referencedColumnName = "accountNumber")
    @JsonIgnore
    private List<Transaction> transactions;
    private long balance;

    public Account() {
    }

    public Account(String accountHolder, Date dob, String accountType, int transactionFee, Timestamp createdAt, Timestamp updatedAt, long balance) {
        this.accountHolder = accountHolder;
        this.dob = dob;
        this.accountType = accountType;
        this.transactionFee = transactionFee;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.balance = balance;
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

    public int getTransactionFee() {
        return transactionFee;
    }

    public void setTransactionFee(int transactionFee) {
        this.transactionFee = transactionFee;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", accountHolder='" + accountHolder + '\'' +
                ", dob=" + dob +
                ", accountType='" + accountType + '\'' +
                ", transactionFee=" + transactionFee +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", transactions=" + transactions +
                ", balance=" + balance +
                '}';
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }


}
