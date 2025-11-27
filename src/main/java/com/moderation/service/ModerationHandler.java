package com.moderation.service;

import static com.moderation.adapter.temporal.TemporalConstants.MODERATION_TASK_QUEUE;

import com.moderation.adapter.dao.moderation.ModerationDao;
import com.moderation.adapter.temporal.ModerationWorkflow;
import com.moderation.domain.Moderation;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Сервис для создания и получения заявок модерации
 *
 * @author Gaben 20.10.2025
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ModerationHandler {

  private final ModerationDao moderationDao;
  private final WorkflowClient workflowClient;

  public Integer startModeration(Moderation moderation) {
    Moderation createdModeration = moderationDao.create(moderation);

    ModerationWorkflow workflow = workflowClient.newWorkflowStub(
        ModerationWorkflow.class,
        WorkflowOptions.newBuilder()
            .setTaskQueue(MODERATION_TASK_QUEUE)
            .setWorkflowId(createdModeration.id().toString())
            .build()
    );
    WorkflowClient.start(workflow::moderate, createdModeration);

    moderationDao.updateInProgress(createdModeration.id());

    return createdModeration.id();
  }

  public Moderation getById(Integer id) {
    return moderationDao.getById(id);
  }

  public Set<Integer> getAllIds() {
    return moderationDao.getAllIds();
  }
}
