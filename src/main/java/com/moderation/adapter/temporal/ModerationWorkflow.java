package com.moderation.adapter.temporal;

import com.moderation.domain.Moderation;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

/**
 * Интерфейс для workflow модерации
 *
 * @author Gaben 21.10.2025
 */
@WorkflowInterface
public interface ModerationWorkflow {

  @WorkflowMethod
  void moderate(Moderation moderation);
}
