package com.rest.demo.controller;

import com.rest.demo.EmptyInputException;
import com.rest.demo.dao.AccountRepo;
import com.rest.demo.dao.TransactionRepo;
import com.rest.demo.dto.StatemetRequest;
import com.rest.demo.dto.TranxResponse;
import com.rest.demo.model.Account;
import com.rest.demo.model.Transaction;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
public class TransactionController {
    @Autowired
    AccountRepo accountRepo;
    @Autowired
    TransactionRepo transactionRepo;
    Logger logger= Logger.getLogger("TransactionController.class");

    @PostMapping("/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction createTransaction(@RequestBody Transaction transaction)
    {
        logger.info("Transaction creation started");
        Calendar calendar = Calendar.getInstance();
        java.util.Date currentTime = calendar.getTime();
        long time = currentTime.getTime();
        if(transaction==null || transaction.getTransactionType().isEmpty())
            throw new EmptyInputException("400","Input is empty");
        String accountType=accountRepo.findAccountTypeByAcctID(transaction.getAccountNumber());
        long balance=accountRepo.findBalanceByAcctID(transaction.getAccountNumber());
        long fee=0;
        if(accountType.equalsIgnoreCase("Current"))
            fee=5;
        else
            fee=0;

        if(transaction.getTransactionType().equalsIgnoreCase("deposit")) {
            transaction.setNewBalance(balance + transaction.getAmount() - fee);
            transaction.setOldBalance(balance);
            transaction.setTransactionDate(new Timestamp(time));
            transaction.setStatus("success");
            transaction.setCreatedAt(new Timestamp(time));
            transaction.setUpdatedAt(new Timestamp(time));
            transactionRepo.save(transaction);
            accountRepo.updateBalanceByAccountNumber(transaction.getAccountNumber(),transaction.getNewBalance());
            logger.info("Depositing the amount");
        }
        else {
            if (balance < 0 || (balance-fee) < transaction.getAmount()) {
                transaction.setStatus("Failed");
                transaction.setNewBalance(balance);
                transaction.setOldBalance(balance);
                transaction.setTransactionDate(new Timestamp(time));
                transaction.setCreatedAt(new Timestamp(time));
                transaction.setUpdatedAt(new Timestamp(time));
                transactionRepo.save(transaction);

            }
            else {

                transaction.setNewBalance(balance - transaction.getAmount() - fee);
                transaction.setOldBalance(balance);
                transaction.setTransactionDate(new Timestamp(time));
                transaction.setStatus("success");
                transaction.setCreatedAt(new Timestamp(time));
                transaction.setUpdatedAt(new Timestamp(time));
                transactionRepo.save(transaction);
                accountRepo.updateBalanceByAccountNumber(transaction.getAccountNumber(),transaction.getNewBalance());
                logger.info("WithDrawal of amount ");
            }

        }

        return transaction;
    }

    @GetMapping("/transactions")
    public List<Transaction> getTransactions()
    {
        logger.info("Getting all transactions");
        return transactionRepo.findAll();
    }

    @GetMapping("/transactions/{transactionId}")
    public Transaction getTransaction(@PathVariable("transactionId") long transactionId)
    {
        logger.info("Retrieving particular Transaction based on Id ");
        return transactionRepo.findTransactionByTransactionId(transactionId);
    }

   @GetMapping("/transactions/getstatement")
    public TranxResponse getTransactions(@RequestBody StatemetRequest statemetRequest)
    {
        logger.info("Retrieving the Account statement");
        if(statemetRequest==null)
            throw new EmptyInputException("400","Empty request");
       Account account=accountRepo.getAccount(statemetRequest.getAccountNumber());
        Calendar calendar = Calendar.getInstance();
        java.util.Date currentTime = calendar.getTime();
        long time = currentTime.getTime();

       Timestamp fromDate=statemetRequest.getFromDate();
       Timestamp toDate=statemetRequest.getToDate()!=null?statemetRequest.getToDate():new Timestamp(time);
       if(statemetRequest.getFromDate()==null)
           fromDate=account.getCreatedAt();
       List<Transaction> transactionList=transactionRepo.findTransactionsByAccountNumber(statemetRequest.getAccountNumber(),fromDate,toDate);

       TranxResponse tranxResponse=new TranxResponse();
       tranxResponse.setAccountNumber(statemetRequest.getAccountNumber());
       tranxResponse.setAccountHolder(account.getAccountHolder());
       tranxResponse.setDob(account.getDob());
       tranxResponse.setBalance(account.getBalance());
       tranxResponse.setAccountType(account.getAccountType());
       tranxResponse.setFromDate(fromDate);
       tranxResponse.setToDate(toDate);
       tranxResponse.setTransactionList(transactionList);

       return tranxResponse;

    }

    @DeleteMapping("/transactions")
    public JSONObject deleteTransactions()
    {
        transactionRepo.deleteAll();
        logger.info("Deleting all Transactions");
        JSONObject json=new JSONObject();
        json.put("No of transactions deleted","All");
        json.put("Status","Success");
        return json;
    }

    @DeleteMapping("/transactionsId/{transactionId}")
    public JSONObject deleteTransactions(@PathVariable("transactionId") long transactionId)
    {
        JSONObject json=new JSONObject();
        logger.info("Deleting particular transaction based on TransactionID");
        json.put("No of transactions deleted",1);
        json.put("Status","Success");
        json.put("Transaction",getTransaction(transactionId));
        transactionRepo.deleteById(transactionId);
        return json;

    }

    @DeleteMapping("/transactions/{accountNumber}")
    public JSONObject deleteTransaction(@PathVariable("accountNumber") long accountNumber)
    {
        transactionRepo.deleteTransactionByAccountNumber(accountNumber);
        logger.info("Deleting Transactions of a particular account");
        JSONObject json=new JSONObject();
        json.put("AccountNumber",accountNumber);
        json.put("Status","Success");
        json.put("No of transactions deleted","All");
        return json;
    }

}
