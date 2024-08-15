package com.zsl.tinycat;

import com.zsl.tinycat.connector.HttpConnector;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Start {
    public static void main(String[] args) {
        String host = "0.0.0.0";
        int port = 8080;
        try (HttpConnector connector = new HttpConnector(host, port)) {
            while (true) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    break;
                }
            }
        } catch (Exception e) {
            log.warn("stopped with exception.", e);
        }
        log.info("tinycat http server shutdown.");
    }
}
