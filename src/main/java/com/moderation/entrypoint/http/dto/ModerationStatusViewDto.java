package com.moderation.entrypoint.http.dto;

/**
 * Дто статуса модерации
 *
 * @author Gaben 21.10.2025
 */
public record ModerationStatusViewDto(
    boolean isFinished,
    boolean isApproved
) {

}
