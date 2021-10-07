package com.rest.demo.dto;

import java.sql.Timestamp;

public class StatemetRequest {
    private long accountNumber;
    private Timestamp fromDate;
    private Timestamp toDate;

    public StatemetRequest(long accountNumber, Timestamp fromDate, Timestamp toDate) {
        this.accountNumber = accountNumber;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber) {
        this.accountNumber = accountNumber;
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
}
