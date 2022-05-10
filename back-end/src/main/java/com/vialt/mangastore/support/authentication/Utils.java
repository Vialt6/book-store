package com.vialt.mangastore.support.authentication;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("http://localhost:4200/")
@UtilityClass
@Log4j2
public class Utils {


    public Jwt getPrincipal() {
        return (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public String getAuthServerId() {
        return getTokenNode().get("subject").asText();
    }

    public String getName() {
        return getTokenNode().get("claims").get("name").asText();
    }

//    public String getSurname(){ return getTokenNode().get("claims").get("lastName").asText(); }

    public String getEmail() {
        return getTokenNode().get("claims").get("email").asText();
    }

    private JsonNode getTokenNode() {
        Jwt jwt = getPrincipal();
        ObjectMapper objectMapper = new ObjectMapper();
        String jwtAsString;
        JsonNode jsonNode;
        try {
            jwtAsString = objectMapper.writeValueAsString(jwt);
            jsonNode = objectMapper.readTree(jwtAsString);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new RuntimeException("Unable to retrieve user's info!");
        }
        return jsonNode;
    }


}
