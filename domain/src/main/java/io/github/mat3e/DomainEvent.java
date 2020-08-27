package io.github.mat3e;

import java.time.Instant;

public interface DomainEvent {
    Instant getOccurredOn();
}
