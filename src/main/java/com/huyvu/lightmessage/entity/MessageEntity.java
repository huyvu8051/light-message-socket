package com.huyvu.lightmessage.entity;

import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.List;

@Builder
public record MessageEntity(
        long id,
        long convId,
        String content,
        long senderId,
        OffsetDateTime sentAt,
        List<Long> memberIds
) {
}
