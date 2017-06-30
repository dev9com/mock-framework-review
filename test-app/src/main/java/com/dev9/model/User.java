package com.dev9.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Null;
import java.util.UUID;

/**
 * @author Justin Graham
 * @since 6/28/17
 */
@Data
public class User {

    @Null(message = "The user id cannot be provided; it will be generated")
    private final UUID uuid;

    @NotEmpty(message = "First name must be provided")
    private final String firstName;

    @NotEmpty(message = "Last name must be provided")
    private final String lastName;

    @Min(value = 1, message = "Age must be greater than 1")
    @Max(value = 150, message = "Age must be less than 150")
    private final int age;
}
