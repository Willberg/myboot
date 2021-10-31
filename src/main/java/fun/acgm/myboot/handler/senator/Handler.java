package fun.acgm.myboot.handler.senator;

import fun.acgm.myboot.entity.senator.Senator;
import fun.acgm.myboot.repository.senator.SenatorRepository;
import fun.acgm.myboot.utils.json.JsonUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@Component
public class Handler {
    @Resource
    private SenatorRepository senatorRepository;

    public Mono<ServerResponse> hello(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue("Hello, Spring!"));
    }

    public Mono<ServerResponse> getOneSenator(ServerRequest request) {
        long id = Integer.parseInt(request.pathVariable("id"));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromProducer(senatorRepository.findById(id), Senator.class));
    }

    public Mono<ServerResponse> getAllSenator(ServerRequest request) {
        Flux<String> flux = senatorRepository.findAll().flatMap(s -> Mono.just(JsonUtil.toJson(s)));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromProducer(flux, String.class));
    }

    public Mono<ServerResponse> getAllSenatorByName(ServerRequest request) {
        String name = request.queryParam("name").orElse("test");
        Mono<String> mono = senatorRepository.searchAll(name).collectList().flatMap(s -> Mono.just(JsonUtil.toJson(s)));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromProducer(mono, String.class));
    }

    public Mono<ServerResponse> count(ServerRequest request) {
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromProducer(senatorRepository.count(), Long.class));
    }

    public Mono<ServerResponse> addOneSenator(ServerRequest request) {
        return request.bodyToMono(Senator.class)
                .flatMap(s -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromProducer(senatorRepository.save(s), Senator.class)));
    }

    public Mono<ServerResponse> addManySenator(ServerRequest request) {
        Flux<String> flux = senatorRepository.saveAll(request.bodyToFlux(Senator.class)).flatMap(s -> Mono.just(JsonUtil.toJson(s)));
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromProducer(flux, String.class));
    }
}
