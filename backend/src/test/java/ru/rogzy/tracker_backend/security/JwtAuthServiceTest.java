package ru.rogzy.tracker_backend.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtAuthServiceTest {

    @Autowired
    private JwtService jwtService;

    @Test
    void generateTokenTest() {
        //arrange
        var email = "test@test.ru";
        //act
        var token = jwtService.generateToken(email);
        //assert
        System.out.println(token);
        Assertions.assertNotEquals("", token);
    }

    @Test
    void extractUsernameTest() {
        //arrange
        var email = "test@test.ru";
        var token = jwtService.generateToken(email);
        //act
        var userName = jwtService.extractUsername(token);
        //assert
        Assertions.assertEquals("test@test.ru", userName);
    }
}
