package org.sidorov.spring_boot_rest_client.restClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.sidorov.spring_boot_rest_client.model.User;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class GetSpecialPassword {

    private final String URI = "http://91.241.64.178:7081/api/users";

    public String solution314() {
        String specialPassword = "";
        User userN3 = new User(3L, "James", "Brown", (byte) 33);
        String cookie = getCookie();
        if(!cookie.isEmpty()) {
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.COOKIE, cookie);
            headers.set(HttpHeaders.CONTENT_TYPE, "application/json");

            specialPassword += postNewUser(headers, userToUserJSON(userN3));
            userN3.setName("Thomas");
            userN3.setLastName("Shelby");

            specialPassword += putUser(headers, userToUserJSON(userN3));
            specialPassword += deleteUser(headers, userN3.getId());
        }
        System.out.println(specialPassword);
        return specialPassword;
    }

    public String getCookie() {
        ResponseEntity<User[]> response = new RestTemplate().getForEntity(
                URI, User[].class
        );
        List<String> setCookies = response.getHeaders().get("Set-Cookie");
        return (setCookies != null)
                ? setCookies.get(0)
                : "";
    }

    public String postNewUser(HttpHeaders headers, String userN3) {
        HttpEntity<String> httpEntity = new HttpEntity<>(userN3, headers);
        ResponseEntity<String> response = new RestTemplate().exchange(
                URI, HttpMethod.POST, httpEntity, String.class
        );
        return response.getBody();
    }

    public String putUser(HttpHeaders headers, String userN3) {
        HttpEntity<String> httpEntity = new HttpEntity<>(userN3, headers);
        ResponseEntity<String> response = new RestTemplate().exchange(
                URI, HttpMethod.PUT, httpEntity, String.class
        );
        return response.getBody();
    }

    public String deleteUser(HttpHeaders headers, long userId) {
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = new RestTemplate().exchange(
                URI+"/"+userId, HttpMethod.DELETE, httpEntity, String.class
        );
        return response.getBody();
    }

    private String userToUserJSON(User user) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            return ow.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
