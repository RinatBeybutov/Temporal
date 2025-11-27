package com.moderation.adapter.temporal;

import static com.moderation.adapter.temporal.TemporalConstants.MODERATION_TASK_QUEUE;

import com.moderation.adapter.temporal.activity.ForbiddenWordModerationActivity;
import com.moderation.adapter.temporal.activity.LuckTestModerationActivity;
import com.moderation.adapter.temporal.activity.SymbolCountModerationActivity;
import com.moderation.domain.Moderation;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import java.time.Duration;

/**
 * Реализация workflow модерации
 *
 * @author Gaben 21.10.2025
 */
@WorkflowImpl(taskQueues = MODERATION_TASK_QUEUE)
public class ModerationWorkflowImpl implements ModerationWorkflow {

  private final SymbolCountModerationActivity symbolCountModerationActivity = Workflow.newActivityStub(
      SymbolCountModerationActivity.class,
      ActivityOptions.newBuilder()
          .setStartToCloseTimeout(Duration.ofMinutes(1))
          .setRetryOptions(RetryOptions.newBuilder()
                               .setMaximumAttempts(5)
                               .setInitialInterval(Duration.ofMinutes(1))
                               .build())
          .build()
  );

  private final ForbiddenWordModerationActivity forbiddenWordModerationActivity = Workflow.newActivityStub(
      ForbiddenWordModerationActivity.class,
      ActivityOptions.newBuilder()
          .setStartToCloseTimeout(Duration.ofMinutes(1))
          .setRetryOptions(RetryOptions.newBuilder()
                               .setMaximumAttempts(5)
                               .setBackoffCoefficient(1.0)
                               .setInitialInterval(Duration.ofSeconds(15))
                               .build())
          .build()
  );

  private final LuckTestModerationActivity luckTestModerationActivity = Workflow.newActivityStub(
      LuckTestModerationActivity.class,
      ActivityOptions.newBuilder()
          .setStartToCloseTimeout(Duration.ofMinutes(1))
          .setRetryOptions(RetryOptions.newBuilder()
                               .setMaximumAttempts(5)
                               .setBackoffCoefficient(1.0)
                               .setInitialInterval(Duration.ofSeconds(15))
                               .build())
          .build()
  );

  @Override
  public void moderate(Moderation moderation) {
    var symbolModerationStatus = symbolCountModerationActivity.symbolCountModerate(moderation);
    if (!symbolModerationStatus.isApproved()) {
      return;
    }

    var forbiddenWordsModerationStatus = forbiddenWordModerationActivity.forbiddenWordModerate(moderation);
    if (!forbiddenWordsModerationStatus.isApproved()) {
      return;
    }

    luckTestModerationActivity.luckyModerate(moderation);
  }
}
