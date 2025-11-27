package com.moderation.adapter.dao.moderation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Сущность модерации
 *
 * @author Gaben 20.10.2025
 */
@Getter
@Setter
@AllArgsConstructor
public class ModerationEntity {
  private Integer id;
  private String text;
  private boolean isFinished;
  private boolean isInProgress;
  private boolean isApproved;

  public static ModerationEntity createNew(Integer id, String text) {
    return new ModerationEntity(id, text, false, false, false);
  }
}
