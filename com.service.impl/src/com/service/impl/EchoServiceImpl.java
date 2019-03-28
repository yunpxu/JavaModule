package com.service.impl;

import com.service.EchoService;

public class EchoServiceImpl implements EchoService {
    @Override
    public void echo(String message) {
        System.out.println(message);
    }
}
