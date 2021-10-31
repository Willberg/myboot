package fun.acgm.myboot.router;

import fun.acgm.myboot.handler.senator.Handler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration(proxyBeanMethods = false)
public class Router {

    @Bean
    public RouterFunction<ServerResponse> route(Handler greetingHandler) {

        return RouterFunctions
                .route(GET("/get/{id}").and(accept(MediaType.APPLICATION_JSON)), greetingHandler::getOneSenator)
                .andRoute(GET("/get").and(accept(MediaType.APPLICATION_JSON)), greetingHandler::getAllSenatorByName)
                .andRoute(POST("/add").and(accept(MediaType.APPLICATION_JSON)), greetingHandler::addOneSenator)
                .andRoute(POST("/many/add").and(accept(MediaType.APPLICATION_JSON)), greetingHandler::addManySenator)
                .andRoute(GET("/all").and(accept(MediaType.APPLICATION_JSON)), greetingHandler::getAllSenator)
                .andRoute(GET("/count").and(accept(MediaType.APPLICATION_JSON)), greetingHandler::count)
                .andRoute(GET("/hello").and(accept(MediaType.APPLICATION_JSON)), greetingHandler::hello);
    }
}
