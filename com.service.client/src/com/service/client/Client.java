package com.service.client;

import com.service.EchoService;

import java.util.stream.Stream;

public class Client {
    /**
     * java --module-path out/production/ -m com.service.client/com.service.client.Client
     * @param args
     */
    public static void main(String[] args) {
        EchoService echoService = EchoService.getServiceProvider();
        echoService.echo("1");

        Stream<EchoService> echoServices = EchoService.getServiceProviders();
        echoServices.forEach(e -> e.echo("2"));
    }
}
