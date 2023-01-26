package infraestructure.entrypoints;

import org.reactivecommons.async.api.HandlerRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

@Configuration
public class AsyncCommandAdapter {

    @Bean
    public HandlerRegistry handlerRegistry(){
        return HandlerRegistry.register()
                .handleCommand("player.eliminated",command->{
                    System.out.println("test");
                    return Mono.empty();
                }, String.class);
    }
}
