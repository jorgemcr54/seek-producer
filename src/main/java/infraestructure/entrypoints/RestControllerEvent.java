package infraestructure.entrypoints;

import infraestructure.domain.model.PlaceToHide;
import org.reactivecommons.api.domain.DomainEvent;
import org.reactivecommons.api.domain.DomainEventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
public class RestControllerEvent {

    @Autowired
    DomainEventBus eventBus;

    @GetMapping("/search/{floor}/{room}")
    public Mono<String> sendEvent(@PathVariable int floor,@PathVariable String room){
        PlaceToHide placeToHide = new PlaceToHide();
        placeToHide.setFloor(floor);
        placeToHide.setRoom(room);
        DomainEvent<PlaceToHide> event = new DomainEvent<PlaceToHide>("place.searched", UUID.randomUUID().toString(),placeToHide);
        return Mono.from(eventBus.emit(event)).thenReturn("Place searched");
    }

}
