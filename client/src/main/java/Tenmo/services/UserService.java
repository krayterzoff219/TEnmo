package Tenmo.services;

import Tenmo.model.Transfer;
import Tenmo.model.User;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

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

    public boolean sendMoney(){
        return false;
    }

    public User[] getUsers(){
        return null;
    }

    public Transfer getTransferById (){
        return null;
    }

    public Transfer[] getTransfersForUser(){
        return null;
    }

    public boolean requestMoney(){
        return false;
    }

    public void viewPendingRequests(){

    }

    public boolean approveRequest(){
        return false;
    }

    public boolean rejectRequest(){
        return false;
    }

    public BigDecimal depositMoney(){
        return null;
    }
}
