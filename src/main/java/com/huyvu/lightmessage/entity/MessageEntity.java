package com.huyvu.lightmessage.entity;

import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record MessageEntity(
        long id,
        long convId,
        String content,
        long senderId,
        OffsetDateTime sentAt
) {
}
