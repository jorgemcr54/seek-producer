package com.personal.seekproducer.infraestructure.entrypoints;

import org.reactivecommons.api.domain.Command;
import org.reactivecommons.async.api.DirectAsyncGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
public class RestControllerCommand {

    @Autowired
    DirectAsyncGateway directAsyncGateway;

    @GetMapping("/eliminate/{player}")
    public Mono<String> sendCommand(@PathVariable String player){
        Command<String> command = new Command<>("player.eliminated", UUID.randomUUID().toString(),"Eliminado");
        return directAsyncGateway.sendCommand(command,player).thenReturn("OK");
    }
}
