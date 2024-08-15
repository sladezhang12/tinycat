package com.zsl.tinycat.connector;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.zsl.tinycat.engine.HttpServletRequestImpl;
import com.zsl.tinycat.engine.HttpServletResponseImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Slf4j
public class HttpConnector implements HttpHandler, AutoCloseable {

    final HttpServer httpServer;
    final String host;
    final int port;

    public HttpConnector(String host, int port) throws IOException {
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

        var adaptor = new HttpExchangeAdaptor(exchange);
        var request = new HttpServletRequestImpl(adaptor);
        var response = new HttpServletResponseImpl(adaptor);


        try {
            process(request, response);
        } catch (Exception e) {
            log.error("process with exception.", e);
        }
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        String html = "<h1>Hello, " + (name == null ? "world" : name) + ".</h1>";
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.write(html);
        writer.close();
    }

    @Override
    public void close() throws Exception {
        httpServer.stop(3);
    }
}
