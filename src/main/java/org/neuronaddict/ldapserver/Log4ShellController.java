package org.neuronaddict.ldapserver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.directory.InitialDirContext;
import javax.servlet.http.HttpServletRequest;


@RestController
public class Log4ShellController {

    private static final Logger logger = LogManager.getLogger(Log4ShellController.class);

    @GetMapping("/")
    public String index(@RequestParam String name, HttpServletRequest request) {
        logger.info("User has user-agent = {}", request.getHeader("User-Agent"));
        return "Hello " + name;
    }

    @GetMapping("/dns-lookup")
    public Object lookup(@RequestParam String name) throws Exception{
        return new InitialDirContext().getAttributes(name, new String[]{"A"}).get("A").get();
    }

}
