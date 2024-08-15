package com.zsl.tinycat.connector;

import java.net.URI;

public interface HttpExchangeRequest {
    String getRequestMethod();

    URI getRequestURI();
}
