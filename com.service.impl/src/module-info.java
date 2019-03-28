module com.service.impl {
    requires com.service;
    provides com.service.EchoService with com.service.impl.EchoServiceImpl,
            com.service.impl.EchoGreetingServiceImpl;
}