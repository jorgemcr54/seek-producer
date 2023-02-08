# seek-producer
 
Project to learn how to use [reactive-commons](https://github.com/reactive-commons/reactive-commons-java) 

Expose 3 endpoints

* ### eliminate/{player}
    
    Emmit a command "player.eliminated" to the "player" application with the String "Eliminado"

* ### search/{floor}/{room}

    Emmit an event "place.searched" with an object type "Place".

* ### validate/{player}/{room}/{floor}

    Send a request to the "player" application with an object type "Place".