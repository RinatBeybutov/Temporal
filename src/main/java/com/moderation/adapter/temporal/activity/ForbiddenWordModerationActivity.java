package com.moderation.adapter.temporal.activity;

import com.moderation.domain.Moderation;
import com.moderation.domain.ModerationStatus;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

/**
 * Интерфейс активити для второго этапа модерации
 *
 * @author Gaben 22.10.2025
 */
@ActivityInterface
public interface ForbiddenWordModerationActivity {

  @ActivityMethod
  ModerationStatus forbiddenWordModerate(Moderation moderation);
}
