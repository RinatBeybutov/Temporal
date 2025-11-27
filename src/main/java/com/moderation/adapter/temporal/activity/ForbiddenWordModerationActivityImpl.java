package com.moderation.adapter.temporal.activity;

import static com.moderation.adapter.temporal.TemporalConstants.MODERATION_TASK_QUEUE;

import com.moderation.adapter.dao.moderation.ModerationDao;
import com.moderation.domain.Moderation;
import com.moderation.domain.ModerationStatus;
import com.moderation.service.ModerationService;
import io.temporal.spring.boot.ActivityImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Реализация активити для второго этапа модерации
 *
 * @author Gaben 22.10.2025
 */
@Component
@ActivityImpl(workers = MODERATION_TASK_QUEUE)
@RequiredArgsConstructor
public class ForbiddenWordModerationActivityImpl implements ForbiddenWordModerationActivity {

  private final ModerationService moderationService;
  private final ModerationDao moderationDao;

  @Override
  public ModerationStatus forbiddenWordModerate(Moderation moderation) {
    ModerationStatus status = moderationService.forbiddenWordModeration(moderation);
    if (!status.isApproved()) {
      moderationDao.updateFinalStatus(moderation.id(), true, false);
    }
    return status;
  }
}
