package com.aws.lambda.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class User {
    private Long id;
    private String name;
    private String surname;
    private String emailAddress;
}
