package com.moderation.domain;

/**
 * Доменный класс для модерации
 *
 * @author Gaben 20.10.2025
 */
public record Moderation(
    Integer id,
    String text,
    boolean isFinished,
    boolean isApproved
) {
}
