package com.moderation.adapter.dao.moderation;

import com.moderation.domain.Moderation;

/**
 * Маппер для сущности модерации
 *
 * @author Gaben 20.10.2025
 */
public class Mapper {

  public static Moderation toDomain(ModerationEntity entity) {
    return new Moderation(
        entity.getId(),
        entity.getText(),
        entity.isFinished(),
        entity.isApproved()
    );
  }
}
