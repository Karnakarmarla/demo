package com.rest.demo.dao;

import com.rest.demo.model.Account;
import org.hibernate.mapping.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface AccountRepo extends JpaRepository<Account,Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Account set balance =?2 where accountNumber=?1")
    public void updateBalanceByAccountNumber(long accountNumber, long balance);

    @Query("select balance from Account where accountNumber= ?1")
    public long findBalanceByAcctID(long accountNumber);

    @Query("select accountType from Account where accountNumber= ?1")
    public String findAccountTypeByAcctID(long accountNumber);

    @Query(value = "select a from Account a where a.accountNumber=?1")
    public Account getAccount(long accountNumber);
}
