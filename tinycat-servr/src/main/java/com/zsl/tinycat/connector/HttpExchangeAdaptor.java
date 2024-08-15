package com.zsl.tinycat.connector;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

public class HttpExchangeAdaptor implements HttpExchangeRequest, HttpExchangeResponse {
    private final HttpExchange httpExchange;

    public HttpExchangeAdaptor(HttpExchange httpExchange) {
        this.httpExchange = httpExchange;
    }

    @Override
    public String getRequestMethod() {
        return httpExchange.getRequestMethod();
    }

    @Override
    public URI getRequestURI() {
        return httpExchange.getRequestURI();
    }


    @Override
    public Headers getResponseHeaders() {
        return httpExchange.getResponseHeaders();
    }

    @Override
    public void sendResponseHeaders(int rCode, int responseLength) throws IOException {
        httpExchange.sendResponseHeaders(rCode, responseLength);

    }

    @Override
    public OutputStream getResponseBody() {
        return httpExchange.getResponseBody();
    }

}
