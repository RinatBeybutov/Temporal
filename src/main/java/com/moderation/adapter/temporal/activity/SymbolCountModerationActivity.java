package com.moderation.adapter.temporal.activity;

import com.moderation.domain.Moderation;
import com.moderation.domain.ModerationStatus;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

/**
 * Интерфейс активити для первого этапа модерации
 *
 * @author Gaben 21.10.2025
 */
@ActivityInterface
public interface SymbolCountModerationActivity {

  @ActivityMethod
  ModerationStatus symbolCountModerate(Moderation moderation);
}
