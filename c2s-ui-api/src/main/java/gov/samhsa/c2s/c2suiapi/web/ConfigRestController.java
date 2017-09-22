package gov.samhsa.c2s.c2suiapi.web;

import gov.samhsa.c2s.c2suiapi.config.C2sUiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigRestController {

    private final C2sUiProperties c2sUiProperties;

    @Autowired
    public ConfigRestController(C2sUiProperties c2sUiProperties) {
        this.c2sUiProperties = c2sUiProperties;
    }

    @GetMapping("/config")
    public C2sUiProperties getC2sConfig() {
        return c2sUiProperties;
    }
}
