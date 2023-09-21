package com.aws.lambda.handler;

import com.aws.lambda.model.User;
import com.aws.lambda.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class UserHandler {

    private UserService userService;

    private UserHandler(@Qualifier("UserService") UserService userService) {
        this.userService = userService;
    }

    public Mono<ServerResponse> getAllUsers(ServerRequest request) {
        return ServerResponse.ok().body(userService.getAllUsers(), User.class);
    }

    public Mono<ServerResponse> getUserById(ServerRequest request){
        Long userId= Long.valueOf( request.pathVariable("id"));
        Mono<User> userMono = userService.getUserById(userId);
        return ServerResponse.ok().body(userMono,User.class);
    }
    public Mono<ServerResponse> createUser(ServerRequest request) {
        Mono<User> userMono = request.bodyToMono(User.class);
        Mono<String> saveResponse = userMono.map(dto -> "name: " + dto.getName() + " surname: " + dto.getSurname());
        return ServerResponse.ok().body(saveResponse,String.class);
    }
}
