package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.*;

import java.math.BigDecimal;
import java.util.List;

public interface UserDao {

    List<User> findAll();

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password);

    Account retrieveBalances(String userName);

    List<UserName> listUsersForTransfer(String userName);
    boolean transfer(Account transferInfo, String userName);
    List<Transfer> listTransfers(String username);

    Transfer getTransferById(int transferId);

    Transfer requestTransfer(Account transferInfo, String username);
    List<Transfer> getPendingRequests(String username);
    Transfer recordTransfer(Account transferInfo, String userName);
    Transfer acceptRequest(TransferStatusUpdate update, String userName);
    Transfer rejectRequest(TransferStatusUpdate update, String userName);
}
