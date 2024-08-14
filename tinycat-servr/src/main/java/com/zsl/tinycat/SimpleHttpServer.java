package com.zsl.tinycat;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.concurrent.locks.LockSupport;

@Slf4j
public class SimpleHttpServer implements HttpHandler, AutoCloseable {

    public static void main(String[] args) {
        String host = "0.0.0.0";
        int port = 8080;
        try (SimpleHttpServer connector = new SimpleHttpServer(host, port)) {
            while (true) {
                Thread.sleep(10000);
            }
        } catch (Exception e) {
            log.warn("stopped with exception.", e);
        }
    }

    final HttpServer httpServer;
    final String host;
    final int port;

    public SimpleHttpServer(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        this.httpServer = HttpServer.create(new InetSocketAddress(host, port), 0);
        this.httpServer.createContext("/", this);
        this.httpServer.start();
        log.info("start tinycat http server at {}:{}", host, port);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        URI uri = exchange.getRequestURI();
        String path = uri.getPath();
        String query = uri.getRawQuery();

        log.info("{}: {}?{}", method, path, query);

        Headers respHeaders = exchange.getResponseHeaders();
        respHeaders.set("Content-Type", "text/html; charset=utf-8");
        respHeaders.set("Cache-Control", "no-cache");
        // 设置200响应:
        exchange.sendResponseHeaders(200, 0);

        String s = "<h1>Hello, world.</h1><p>" + LocalDateTime.now().withNano(0) + "</p>";

        try (OutputStream outputStream = exchange.getResponseBody()) {
            outputStream.write(s.getBytes(StandardCharsets.UTF_8));
        }
    }

    @Override
    public void close() throws Exception {
        httpServer.stop(3);
    }
}
