package com.rest.demo.dao;

import com.rest.demo.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction,Long> {

    @Query("select t from Transaction t where t.accountNumber= ?1 and t.transactionDate between ?2 and ?3")
    public List<Transaction> findTransactionsByAccountNumber(long accountNumber, Timestamp fromDate,Timestamp toDate);

    @Query("select t from Transaction t where t.transactionId= ?1")
    public Transaction findTransactionByTransactionId(long transactionId);

    @Query("Delete from Transaction where accountNumber=?1")
    public void deleteTransactionByAccountNumber(long accountNumber);


}
