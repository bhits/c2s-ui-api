package gov.samhsa.c2s.c2suiapi.web;

import gov.samhsa.c2s.c2suiapi.service.UaaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/uaa")
public class UaaRestController {

    @Autowired
    private UaaService uaaService;

    @PostMapping("/login")
    Object login(@RequestParam String username, @RequestParam String password) {
        return uaaService.login(username, password);
    }
}