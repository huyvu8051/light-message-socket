package com.huyvu.lightmessage.entity;

import lombok.Builder;

import java.util.List;

@Builder
public record MessageKafkaDTO(
        long id,
        long convId,
        String content,
        long senderId,
        String sentAt,
        List<Long> memberIds
) {
}