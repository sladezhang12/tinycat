package com.zsl.tinycat.connector;

import com.sun.net.httpserver.Headers;

import java.io.IOException;
import java.io.OutputStream;

public interface HttpExchangeResponse {

    Headers getResponseHeaders();


    void sendResponseHeaders(int rCode, int responseLength) throws IOException;

    OutputStream getResponseBody();
}
