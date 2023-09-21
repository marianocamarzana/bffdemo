package com.aws.lambda.service;

import com.aws.lambda.model.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Flux<User> getAllUsers();

    Mono<User> getUserById(Long userId);
    Mono<User> createUser(User user);
}
