package com.service;

import java.util.ServiceLoader;
import java.util.stream.Stream;

public interface EchoService {
    void echo(String message);

    /**
     * Load first service provider, if no service provider found throw RuntimeException.
     *
     * @return
     */
    static EchoService getServiceProvider() {
        return ServiceLoader.load(EchoService.class).findFirst().orElseThrow(() -> new RuntimeException("Service Unavailable"));
    }

    /**
     * Load all service providers.
     *
     * @return
     */
    static Stream<EchoService> getServiceProviders() {
        return ServiceLoader.load(EchoService.class).stream().map(ServiceLoader.Provider::get);
    }
}
