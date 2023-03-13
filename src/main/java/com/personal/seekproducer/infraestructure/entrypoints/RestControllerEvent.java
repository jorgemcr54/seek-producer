package com.personal.seekproducer.infraestructure.entrypoints;

import com.personal.seekproducer.domain.model.PlaceToHide;
import io.cloudevents.CloudEvent;
import io.cloudevents.spring.http.CloudEventHttpUtils;
import io.cloudevents.core.builder.CloudEventBuilder;
import org.reactivecommons.api.domain.DomainEvent;
import org.reactivecommons.api.domain.DomainEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.Timestamp;
import java.time.OffsetDateTime;
import java.util.Date;
import java.util.UUID;

@RestController
public class RestControllerEvent {

    @Autowired
    DomainEventBus eventBus;

    @GetMapping("/search/{floor}/{room}")
    public Mono<String> sendEvent(@PathVariable int floor,@PathVariable String room, @RequestHeader HttpHeaders headers){
        PlaceToHide placeToHide = new PlaceToHide();
        placeToHide.setFloor(floor);
        placeToHide.setRoom(room);
        CloudEvent attributes = CloudEventBuilder.v1() //
                .withId(UUID.randomUUID().toString()) //
                .withSource(URI.create("https://spring.io/foos"))//
                .withType("event") //
                .withDataContentType("application/json")
                .withTime(OffsetDateTime.now())
                .withData("text/plain", String.valueOf(placeToHide).getBytes(StandardCharsets.UTF_8))
                .build();

        DomainEvent<CloudEvent> event = new DomainEvent<CloudEvent>("place.searched", UUID.randomUUID().toString(), attributes);
        return Mono.from(eventBus.emit(event)).thenReturn("Place searched");
    }

}
