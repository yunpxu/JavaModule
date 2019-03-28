package com.service.impl;

import com.service.EchoService;

public class EchoGreetingServiceImpl implements EchoService {
    @Override
    public void echo(String message) {
        System.out.println("Greeting " + message);
    }
}
