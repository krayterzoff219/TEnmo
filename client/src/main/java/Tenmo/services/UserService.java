package Tenmo.services;

import Tenmo.model.Transfer;
import Tenmo.model.TransferStatus;
import Tenmo.model.User;
import Tenmo.model.UserName;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private static final String API_BASE_URL = "http://localhost:8080/tenmo/";
    private final RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public BigDecimal getBalance(){
        User user = null;
        try {
            user = restTemplate.exchange(API_BASE_URL + "balance", HttpMethod.GET, makeAuthEntity(), User.class).getBody();
        } catch (RestClientResponseException | ResourceAccessException e){
            System.out.println(e.getMessage());
        }

        assert user != null;
        return user.getBalance();
    }

    public boolean sendMoney(BigDecimal amount, String username){
        User user = new User();
        user.setBalance(amount);
        user.setUsername(username);
        boolean success = false;
        try {
            restTemplate.exchange(API_BASE_URL + "transfer", HttpMethod.PUT, makeUserEntity(user), User.class);
            success = true;
        } catch (RestClientResponseException | ResourceAccessException e){
            System.out.println(e.getMessage());
        }

        return success;
    }

    public UserName[] getUsers(){
        UserName[] users = null;
        try {
            ResponseEntity<UserName[]> response = restTemplate.exchange(API_BASE_URL + "users", HttpMethod.GET, makeAuthEntity(), UserName[].class);
            users = response.getBody();
        } catch (RestClientResponseException | ResourceAccessException e){
            System.out.println(e.getMessage());
        }

        return users;
    }

    public Transfer[] getTransfersForUser(){
        Transfer[] transfers = null;
//        List<Transfer> transfers = new ArrayList<>();
        try {
            transfers = restTemplate.exchange(API_BASE_URL + "transfer/view", HttpMethod.GET, makeAuthEntity(), Transfer[].class).getBody();
        } catch (RestClientResponseException | ResourceAccessException e){
            System.out.println(e.getMessage());
        }

        return transfers;
    }

    public boolean requestMoney(BigDecimal amount, String username){
        User user = new User();
        user.setBalance(amount);
        user.setUsername(username);
        boolean success = false;
        try {
            restTemplate.exchange(API_BASE_URL + "transfer/request", HttpMethod.POST, makeUserEntity(user), Transfer.class);
            success = true;
        } catch (RestClientResponseException | ResourceAccessException e){
            System.out.println(e.getMessage());
        }

        return success;
    }

    public Transfer[] getPendingRequests(){
        Transfer[] transfers = null;
        try {
            transfers = restTemplate.exchange(API_BASE_URL + "transfer/pending", HttpMethod.GET, makeAuthEntity(), Transfer[].class).getBody();
        } catch (RestClientResponseException | ResourceAccessException e){
            System.out.println(e.getMessage());
        }

        return transfers;
    }

    public boolean approveRequest(int id){
        TransferStatus transferStatus = new TransferStatus();
        transferStatus.setTransferId(id);
        transferStatus.setStatus("Approved");
        boolean success = false;
        try {
            restTemplate.put(API_BASE_URL + "transfer/pending", makeStatusEntity(transferStatus));
            success = true;
        } catch (RestClientResponseException | ResourceAccessException e){
            System.out.println(e.getMessage());
        }

        return success;
    }

    public boolean rejectRequest(int id){
        TransferStatus transferStatus = new TransferStatus();
        transferStatus.setTransferId(id);
        transferStatus.setStatus("Rejected");
        boolean success = false;
        try {
            restTemplate.put(API_BASE_URL + "transfer/pending", makeStatusEntity(transferStatus));
            success = true;
        } catch (RestClientResponseException | ResourceAccessException e){
            System.out.println(e.getMessage());
        }

        return success;
    }

    public BigDecimal depositMoney(BigDecimal amount){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        HttpEntity<BigDecimal> entity = new HttpEntity<>(amount, headers);

        User user = null;
        try{
            user = restTemplate.exchange(API_BASE_URL + "deposit", HttpMethod.PUT, entity, User.class).getBody();
        } catch (RestClientResponseException | ResourceAccessException e){
            System.out.println(e.getMessage());
        }

        assert user != null;
        return user.getBalance();
    }



    private HttpEntity<TransferStatus> makeStatusEntity(TransferStatus transferStatus){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(transferStatus, headers);
    }
    private HttpEntity<User> makeUserEntity(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(user, headers);
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
