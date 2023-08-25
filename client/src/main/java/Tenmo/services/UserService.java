package Tenmo.services;

import Tenmo.model.Transfer;
import Tenmo.model.User;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

public class UserService {

    private static final String API_BASE_URL = "http://localhost:8080/tenmo";
    private final RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public BigDecimal getBalance(){
        return null;
    }

    public boolean sendMoney(BigDecimal amount, String username){
        return false;
    }

    public User[] getUsers(){
        return null;
    }

    public Transfer[] getTransfersForUser(){
        return null;
    }

    public boolean requestMoney(BigDecimal amount, String username){
        return false;
    }

    public List<Transfer> viewPendingRequests(){
        return null;
    }

    public boolean approveRequest(int id){
        return false;
    }

    public boolean rejectRequest(int id){
        return false;
    }

    public BigDecimal depositMoney(BigDecimal amount){
        return null;
    }
}
