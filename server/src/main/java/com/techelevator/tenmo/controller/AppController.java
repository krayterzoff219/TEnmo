package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.UserName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.ParameterResolutionDelegate;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
//@RequestMapping(path = "")
public class AppController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(path = "/balance", method = RequestMethod.GET)
    public Account getBalances(Principal principal){
        return userDao.retrieveBalances(principal.getName());
    }


    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<UserName> getUsers(Principal principal) {
        return userDao.listUsersForTransfer(principal.getName());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "/transfer", method = RequestMethod.PUT)
    public Transfer transferTE(@RequestBody @Valid Account transferInfo, Principal principal){
        return userDao.transfer(transferInfo, principal.getName());
    }

    @RequestMapping(path = "/transfer/view", method = RequestMethod.GET)
    public List<Transfer> listTransfers(Principal principal) {
        return userDao.listTransfers(principal.getName());
    }


    @RequestMapping(path = "/transfer/view/{id}", method = RequestMethod.GET)
    public Transfer getTransferById(@PathVariable int id) {
        return userDao.getTransferById(id);
    }

    @RequestMapping(path = "/transfer/view/pending", method = RequestMethod.GET)
    public List<Transfer> listPendingTransfers(Principal principal){
        return userDao.getPendingRequests(principal.getName());
    }

    @RequestMapping(path = "/transfer/request", method = RequestMethod.POST)
    public Transfer requestTransfer(@RequestBody @Valid Account transferInfo, Principal principal){
        return userDao.requestTransfer(transferInfo, principal.getName());
    }

}


