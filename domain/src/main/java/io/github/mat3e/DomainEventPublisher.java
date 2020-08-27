package io.github.mat3e;

public interface DomainEventPublisher {
    void publish(DomainEvent event);
}
