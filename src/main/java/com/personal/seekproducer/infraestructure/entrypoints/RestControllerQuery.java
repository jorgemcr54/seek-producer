package com.personal.seekproducer.infraestructure.entrypoints;

import com.personal.seekproducer.domain.model.PlaceToHide;
import org.reactivecommons.async.api.AsyncQuery;
import org.reactivecommons.async.api.DirectAsyncGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class RestControllerQuery {

    @Autowired
    DirectAsyncGateway directAsyncGateway;

    @GetMapping("validate/{player}/{floor}/{room}")
    public Mono<String> SendQuery(@PathVariable String player, @PathVariable int floor, @PathVariable String room){
        PlaceToHide placeToHide = new PlaceToHide();
        placeToHide.setFloor(floor);
        placeToHide.setRoom(room);
        AsyncQuery<PlaceToHide> query = new AsyncQuery<>("player.searched.query",placeToHide);
        Mono<String> response = directAsyncGateway.requestReply(query,player, String.class);
        return response;
    }
}
