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
 * Реализация активити для первого этапа модерации
 *
 * @author Gaben 21.10.2025
 */
@Component
@ActivityImpl(workers = MODERATION_TASK_QUEUE)
@RequiredArgsConstructor
public class SymbolCountModerationActivityImpl implements SymbolCountModerationActivity {

  private final ModerationDao moderationDao;
  private final ModerationService moderationService;

  @Override
  public ModerationStatus symbolCountModerate(Moderation moderation) {
    ModerationStatus status = moderationService.symbolCountModeration(moderation);
    if (!status.isApproved()) {
      moderationDao.updateFinalStatus(moderation.id(), true, false);
    }
    return status;
  }
}
