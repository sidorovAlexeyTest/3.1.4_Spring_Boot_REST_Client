package org.sidorov.spring_boot_rest_client.controller;

import org.sidorov.spring_boot_rest_client.restClient.GetSpecialPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/")
public class HomePage {

    private GetSpecialPassword getSpecialPassword;

    @Autowired
    public HomePage(GetSpecialPassword getSpecialPassword) {
        this.getSpecialPassword = getSpecialPassword;
    }

    @GetMapping
    public String getHomePage() {
        return getSpecialPassword.solution314();
    }
}
