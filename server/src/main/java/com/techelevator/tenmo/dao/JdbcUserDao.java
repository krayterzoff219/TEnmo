package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserName;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcUserDao implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int findIdByUsername(String username) {
        String sql = "SELECT user_id FROM tenmo_user WHERE username ILIKE ?;";
        Integer id = jdbcTemplate.queryForObject(sql, Integer.class, username);
        if (id != null) {
            return id;
        } else {
            return -1;
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT user_id, username, password_hash FROM tenmo_user;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while(results.next()) {
            User user = mapRowToUser(results);
            users.add(user);
        }
        return users;
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException {
        String sql = "SELECT user_id, username, password_hash FROM tenmo_user WHERE username ILIKE ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
        if (rowSet.next()){
            return mapRowToUser(rowSet);
        }
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }

    @Override
    public boolean create(String username, String password) {

        // create user
        String sql = "INSERT INTO tenmo_user (username, password_hash) VALUES (?, ?) RETURNING user_id";
        String password_hash = new BCryptPasswordEncoder().encode(password);
        Integer newUserId;
        try {
            newUserId = jdbcTemplate.queryForObject(sql, Integer.class, username, password_hash);
            sql = "INSERT INTO account (user_id, balance) VALUES (?, ?);";
            int numberOfRows = jdbcTemplate.update(sql, newUserId, new BigDecimal(1000));
            if(numberOfRows!= 1){
                return false;
            }
        } catch (DataAccessException e) {
            return false;
        }

        return true;
    }

    public List<Account> retrieveBalances(String userName){
        List<Account> accountList = new ArrayList<>();
        String sql = "SELECT username, balance FROM account JOIN tenmo_user ON account.user_id = tenmo_user.user_id " +
                "WHERE username = ?;";
        try{
            SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, userName);
            while(rows.next()){
                Account account = new Account();
                account.setUsername(rows.getString("username"));
                account.setBalance(rows.getBigDecimal("balance"));
                accountList.add(account);
            }
        } catch (ResourceAccessException e){

        }

        return accountList;
    }

    public List<UserName> listUsersForTransfer(String userName) {
        List<UserName> users = new ArrayList<>();
        String sql = "Select username From tenmo_user Where username != ?;";

        try{
            SqlRowSet row = jdbcTemplate.queryForRowSet(sql, userName);
            while(row.next()) {
                UserName user = new UserName();
                user.setUserName(row.getString("username"));
                users.add(user);
            }
        } catch (ResourceAccessException e) {
            e.printStackTrace();
        }
        return users;
    }

    public Transfer transfer(Account transferInfo, String userName){
        Transfer transferAttempt = new Transfer();

        String checkSql = "SELECT balance FROM account JOIN tenmo_user ON account.user_id = tenmo_user.user_id WHERE username = ?;";
        String addSql = "UPDATE account SET balance = balance + ? WHERE account_id = (SELECT account_id FROM account JOIN tenmo_user " +
                "ON account.user_id = tenmo_user.user_id WHERE username = ?);";
        String subtractSql = "UPDATE account SET balance = balance - ? WHERE account_id = (SELECT account_id FROM account JOIN tenmo_user " +
                "ON account.user_id = tenmo_user.user_id WHERE username = ?);";
        String transferSql = "INSERT INTO transfer (sender_id, receiver_id, amount) " +
                "VALUES ((SELECT user_id FROM tenmo_user WHERE username = ?), " +
                "(SELECT user_id FROM tenmo_user WHERE username = ?), " +
                "?) RETURNING transfer_id;";

        try {

            transferAttempt.setFrom(userName);

            transferAttempt.setTo(transferInfo.getUsername());

            transferAttempt.setTransferAmount(transferInfo.getBalance());


            SqlRowSet row = jdbcTemplate.queryForRowSet(checkSql, userName);
            if(row.next()){
                if (row.getBigDecimal("balance").compareTo(transferInfo.getBalance()) < 0){
                    throw new DataIntegrityViolationException("Transfer amount more than account balance");
                }
            }

            int numberOfRows = jdbcTemplate.update(addSql, transferInfo.getBalance(), transferInfo.getUsername());
            if (numberOfRows == 0){
                System.out.println("Didn't update");
                return transferAttempt;
            }
            numberOfRows = jdbcTemplate.update(subtractSql, transferInfo.getBalance(), userName);
            if (numberOfRows == 0){
                System.out.println("Didn't update");
                return transferAttempt;
            }

            int newTransferId = jdbcTemplate.queryForObject(transferSql, Integer.class, userName, transferInfo.getUsername(), transferInfo.getBalance());
            transferAttempt.setTransferId(newTransferId);

        } catch (ResourceAccessException e){

        } catch (NullPointerException e){
            System.out.println("Couldn't find transfer info");
        }

        return transferAttempt;
    }

    private User mapRowToUser(SqlRowSet rs) {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        user.setActivated(true);
        user.setAuthorities("USER");
        return user;
    }
}
