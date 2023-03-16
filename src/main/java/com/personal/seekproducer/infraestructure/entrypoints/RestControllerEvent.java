package com.personal.seekproducer.infraestructure.entrypoints;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.personal.seekproducer.domain.model.PlaceToHide;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.provider.EventFormatProvider;
import io.cloudevents.jackson.JsonFormat;
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

import java.io.UnsupportedEncodingException;
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
    public Mono<String> sendEvent(@PathVariable int floor,@PathVariable String room) throws JsonProcessingException {
        PlaceToHide placeToHide = new PlaceToHide();
        placeToHide.setFloor(floor);
        placeToHide.setRoom(room);
        ObjectMapper om = new ObjectMapper();
        String objectClass = om.writeValueAsString(placeToHide);
        CloudEvent attributes = null;
        try {
            attributes = CloudEventBuilder.v1() //
                    .withId(UUID.randomUUID().toString()) //
                    .withSource(URI.create("https://spring.io/foos"))//
                    .withType("event") //
                    .withDataContentType("application/json")
                    .withTime(OffsetDateTime.now())
                    .withData("text/plain", objectClass.getBytes("UTF-8"))
                    .build();



        } catch (UnsupportedEncodingException e) {
            System.out.println("ERROR -> " + e.getMessage());
        }
        byte[] serialized = EventFormatProvider
                .getInstance()
                .resolveFormat(JsonFormat.CONTENT_TYPE)
                .serialize(attributes);

        DomainEvent<byte[]> event = new DomainEvent<byte[]>("place.searched", UUID.randomUUID().toString(), serialized);
        return Mono.from(eventBus.emit(event)).thenReturn("Place searched");
    }

}
