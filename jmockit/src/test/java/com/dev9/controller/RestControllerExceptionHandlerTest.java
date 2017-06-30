package com.dev9.controller;

import com.dev9.model.Error;
import mockit.Mocked;
import mockit.Tested;
import mockit.Verifications;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Justin Graham
 * @since 6/28/17
 */
@Test
public class RestControllerExceptionHandlerTest {

    @Mocked private ResponseEntity responseEntity;
    @Mocked private Error error;
    @Tested private RestControllerExceptionHandler tested;

    @Test
    public void testHandleIllegalArgumentException() throws Exception {
        final String message = "message";
        assertThat(tested.handleIllegalArgumentException(new IllegalArgumentException(message))).isNotNull();
        new Verifications() {{
            final Error errorResponse = Error.builder().message(message).statusCode(400).build();
            new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }};
    }
}
