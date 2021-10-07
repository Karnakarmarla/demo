package com.rest.demo.controller;

import com.rest.demo.EmptyInputException;
import com.rest.demo.dao.AccountRepo;
import com.rest.demo.dao.TransactionRepo;
import com.rest.demo.model.Account;
import com.rest.demo.model.Transaction;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
public class AccountController {
    @Autowired
    AccountRepo accountRepo;
    @Autowired
    TransactionRepo transactionRepo;
    Logger logger=Logger.getLogger("AccountController.class");

    @PostMapping("/accounts")
    public ResponseEntity<Account>createAccount(@RequestBody Account account)
    {
        Calendar calendar = Calendar.getInstance();
        java.util.Date currentTime = calendar.getTime();
        long time = currentTime.getTime();
        if(account.getAccountHolder().isEmpty()|| account.getAccountType().isEmpty()||account.getDob()==null)
            throw new EmptyInputException("601","Input fields are empty");

        logger.info("Account creation started");
        if(account.getAccountType().equalsIgnoreCase("current"))
            account.setTransactionFee(5);
        account.setCreatedAt(new Timestamp(time));
        account.setUpdatedAt(new Timestamp(time));
        accountRepo.save(account);

        if(account.getBalance()>0){
            Transaction t=new Transaction();
            t.setAccountNumber(account.getAccountNumber());
            t.setAmount(account.getBalance());
            t.setCreatedAt(new Timestamp(time));
            t.setUpdatedAt(new Timestamp(time));
            t.setStatus("success");
            t.setTransactionDate(new Timestamp(time));
            t.setNewBalance(account.getBalance());
            t.setTransactionType("deposit");
            transactionRepo.save(t);
            logger.info("Initial Transaction is saved");
        }

        return new ResponseEntity<Account>(account, HttpStatus.CREATED);
    }

    @GetMapping("/accounts")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Account> getAccounts()
    {
        logger.info("retrieving all accounts");
        return accountRepo.findAll();
    }

    @GetMapping("/accounts/{accountNumber}")
    public Optional<Account> getAccount(@PathVariable("accountNumber") long accountNumber)
    {
        logger.info("retrieving particular account based On accountNumber");
        if(!accountRepo.existsById(accountNumber))
            throw new NullPointerException("Account not present");
        return accountRepo.findById(accountNumber);
    }

    @DeleteMapping("/accounts")
    public JSONObject deleteAccounts()
    {
        accountRepo.deleteAll();
        logger.info("Deleting all accounts");
        JSONObject json=new JSONObject();
        json.put("No of Accounts deleted","All");
        json.put("Status","Success");
        return json;
    }

    @DeleteMapping("/accounts/{accountNumber}")
    public JSONObject deleteAccounts(@PathVariable("accountNumber") long accountNumber)
    {
        accountRepo.deleteById(accountNumber);
        logger.info("Deleting particular account based on accountNumber");
        JSONObject json=new JSONObject();
        json.put("AccountNumber",accountNumber);
        json.put("Account deleted",accountRepo.getAccount(accountNumber));
        json.put("Status","Success");
        return json;
    }

    @PutMapping("/accounts")
    public Account saveOrUpdate(@RequestBody Account account)
    {
        Calendar calendar = Calendar.getInstance();
        java.util.Date currentTime = calendar.getTime();
        long time = currentTime.getTime();
        if(account==null)
            throw new NullPointerException();

        boolean exists=accountRepo.existsById(account.getAccountNumber());

        if(!exists) {
            logger.info("Creating account");
            if (account.getAccountType().equalsIgnoreCase("current"))
                account.setTransactionFee(5);
            account.setCreatedAt(new Timestamp(time));
            account.setUpdatedAt(new Timestamp(time));
            accountRepo.save(account);

            if (account.getBalance() > 0) {
                Transaction t = new Transaction();
                t.setAccountNumber(account.getAccountNumber());
                t.setAmount(account.getBalance());
                t.setCreatedAt(new Timestamp(time));
                t.setUpdatedAt(new Timestamp(time));
                t.setStatus("success");
                t.setTransactionDate(new Timestamp(time));
                t.setNewBalance(account.getBalance());
                t.setTransactionType("deposit");
                transactionRepo.save(t);
            }
        }
        else {
            logger.info("Updating the account");
            if (account.getAccountType().equalsIgnoreCase("current"))
                account.setTransactionFee(5);
            account.setUpdatedAt(new Timestamp(time));
            accountRepo.save(account);
        }
        return account;
    }
}
