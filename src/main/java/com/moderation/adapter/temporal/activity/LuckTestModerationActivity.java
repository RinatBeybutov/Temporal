package com.moderation.adapter.temporal.activity;

import com.moderation.domain.Moderation;
import com.moderation.domain.ModerationStatus;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

/**
 * Интерфейс активити для третьего этапа модерации
 *
 * @author Gaben 27.10.2025
 */
@ActivityInterface
public interface LuckTestModerationActivity {

  @ActivityMethod
  ModerationStatus luckyModerate(Moderation moderation);
}
