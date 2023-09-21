package com.aws.lambda.service;

import com.aws.lambda.exception.UserNotFoundException;
import com.aws.lambda.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service("UserService")
public class UserServiceImpl implements UserService {
    @Override
    public Flux<User> getAllUsers() {
        return Flux.just(User.builder().id(1l).name("Mariano").surname("Camarzana").emailAddress("marianocamarzana@gmail.com").build(),
                User.builder().id(2l).name("Juan").surname("Lopez").emailAddress("juanlopez@gmail.com").build());
    }

    @Override
    public Mono<User> getUserById(Long userId) {
        Mono<User> userMono = getAllUsers()
                .filter(user -> user.getId().equals(userId))
                .next().switchIfEmpty(Mono.error(new UserNotFoundException("User not found with userId : "+ userId)));
        return userMono;
    }

    @Override
    public Mono<User> createUser(User user) {
        log.info("Create user invoked with: {}", user);
        return Mono.just(user);
    }
}
