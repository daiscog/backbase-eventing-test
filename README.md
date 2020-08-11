# Eventing test

Demonstrates eventing retries and delivery to a DLQ.

## event-spec

Contains a single event called TestEvent, generated from a json-schema.

## Producer

Contains a `RestController` which listens for POSTs to `/message` and dispatches 
a `TestEvent` for every request handled.

## Consumer

Contains an `ExampleService` class which implements `EventHandler<TestEvent>`.

The `handle` method therein logs a message every time it is invoked and then 
throws a `RuntimeException`.

After five failed attempts to handle the `TestEvent`, it is forwarded to the 
queue `custom.test-event.dlq`.

The max attempts and DLQ name are both configured in the application.yml for 
the service.  Removing this configuration will result in the failed messages 
being forwarded to the queue `Spring.Cloud.Stream.dlq` after three failed 
attempts.

## Running

You will need an ActiveMQ server running on localhost:61616, or you will need to configure
an ActiveMQ connection in the application.yml files of the `producer` and `consumer`
services.

1. In the `event-spec` dir, run `mvn clean install` to generate the TestEvent object and 
   associated autoconfiguration class.
2. In the `consumer` dir, run `mvn clean spring-boot:run` to start the consumer service.
3. In the `producer` dir, run `mvn clean spring-boot:run` to start the producer service.
4. Send a post to http://localhost:9915/message with the following request body:
    ```json
    {
      "message": "Hello"
    }
    ```
5. The logs for the `consumer` service should show the handler was invoked five times, 
   with an increasing backoff.
