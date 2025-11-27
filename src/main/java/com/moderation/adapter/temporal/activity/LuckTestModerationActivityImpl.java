package com.moderation.adapter.temporal.activity;

import static com.moderation.adapter.temporal.TemporalConstants.MODERATION_TASK_QUEUE;

import com.moderation.adapter.dao.moderation.ModerationDao;
import com.moderation.domain.Moderation;
import com.moderation.domain.ModerationStatus;
import com.moderation.service.ModerationService;
import io.temporal.activity.Activity;
import io.temporal.spring.boot.ActivityImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Реализация активити для третьего этапа модерации
 *
 * @author Gaben 22.10.2025
 */
@Component
@ActivityImpl(workers = MODERATION_TASK_QUEUE)
@RequiredArgsConstructor
public class LuckTestModerationActivityImpl implements LuckTestModerationActivity {

  private final ModerationService moderationService;
  private final ModerationDao moderationDao;

  @Override
  public ModerationStatus luckyModerate(Moderation moderation) {
    try {
      ModerationStatus status = moderationService.luckyModeration(moderation);
      moderationDao.updateFinalStatus(moderation.id(), true, true);
      return status;
    } catch (Exception e) {
      if (Activity.getExecutionContext().getInfo().getAttempt() == 5){
        moderationDao.updateFinalStatus(moderation.id(), true, false);
      }
      throw e;
    }
  }
}
