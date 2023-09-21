package com.aws.lambda.router;

import com.aws.lambda.handler.UserHandler;
import com.aws.lambda.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Slf4j
@Configuration
public class RouterConfig {

    @Autowired
    private UserHandler userHandler;

    @Bean
    public WebProperties.Resources resources(){
        return new WebProperties.Resources();
    }

    @Bean
    @RouterOperations({
            @RouterOperation(path = "/api/v1/users", produces = {
                    MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.GET, beanClass = UserHandler.class, beanMethod = "getAllUsers",
                    operation = @Operation(operationId = "getAllUsers", responses = {
                            @ApiResponse(responseCode = "200", description = "get al users successfully", content = @Content(schema = @Schema(implementation = User.class)))})),
            @RouterOperation(path = "/api/v1/users", produces = {
                    MediaType.APPLICATION_JSON_VALUE}, method = RequestMethod.POST, beanClass = UserHandler.class, beanMethod = "createUser",
                    operation = @Operation(operationId = "createUser", responses = {
                            @ApiResponse(responseCode = "200", description = "create user succesfully", content = @Content(schema = @Schema(implementation = String.class)))},
                            requestBody = @RequestBody(content = @Content(schema = @Schema(implementation = User.class)))))
    })

    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route()
                .path("/api/v1", builder -> builder
                        .GET("/users", userHandler::getAllUsers)
                        .GET("/users/{id}", userHandler::getUserById)
                        .POST("/users", userHandler::createUser)
                )
                .build();
    }
}
