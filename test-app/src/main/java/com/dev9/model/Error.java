package com.dev9.model;

import lombok.Builder;
import lombok.Value;

/**
 * @author Justin Graham
 * @since 6/28/17
 */
@Value
@Builder
public class Error {
    private final int statusCode;
    private final String message;
}
