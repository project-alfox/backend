package com.ace.alfox;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ThisWillActuallyRun {

    @RequestMapping("/")
    Test1 home() {
        return new Test1();
    }

    class Test1 {
        public String name = "Hello!";
    }

}