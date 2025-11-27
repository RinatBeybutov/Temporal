package com.moderation.domain;

/**
 * Доменный класс для статуса модерации
 *
 * @author Gaben 20.10.2025
 */
public record ModerationStatus(
  boolean isFinished,
  boolean isApproved
) {

}
