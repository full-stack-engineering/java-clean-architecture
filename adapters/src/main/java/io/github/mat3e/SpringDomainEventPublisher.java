package io.github.mat3e;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class SpringDomainEventPublisher implements DomainEventPublisher {
    private final ApplicationEventPublisher innerPublisher;

    public SpringDomainEventPublisher(final ApplicationEventPublisher innerPublisher) {
        this.innerPublisher = innerPublisher;
    }

    @Override
    public void publish(final DomainEvent event) {
        innerPublisher.publishEvent(event);
    }
}
