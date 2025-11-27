package com.moderation;

import static com.moderation.adapter.temporal.TemporalConstants.MODERATION_TASK_QUEUE;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

import com.moderation.adapter.dao.moderation.ModerationDao;
import com.moderation.adapter.temporal.ModerationWorkflow;
import com.moderation.adapter.temporal.ModerationWorkflowImpl;
import com.moderation.adapter.temporal.activity.ForbiddenWordModerationActivityImpl;
import com.moderation.adapter.temporal.activity.LuckTestModerationActivityImpl;
import com.moderation.adapter.temporal.activity.SymbolCountModerationActivityImpl;
import com.moderation.domain.Moderation;
import com.moderation.service.ModerationHandler;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.testing.TestWorkflowEnvironment;
import io.temporal.worker.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootTest()
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ModerationWorkflowTest {

  @Autowired ConfigurableApplicationContext applicationContext;
  @Autowired TestWorkflowEnvironment testWorkflowEnvironment;
  @Autowired WorkflowClient workflowClient;
  @Autowired SymbolCountModerationActivityImpl symbolCountModerationActivity;
  @Autowired ForbiddenWordModerationActivityImpl forbiddenWordModerationActivity;
  @Autowired LuckTestModerationActivityImpl luckTestModerationActivity;
  @Autowired ModerationDao moderationDao;
  @Autowired ModerationHandler moderationHandler;

  @BeforeEach
  void setUp() {
    applicationContext.start();
    Worker worker = testWorkflowEnvironment.newWorker(MODERATION_TASK_QUEUE);
    worker.registerWorkflowImplementationTypes(ModerationWorkflowImpl.class);

    worker.registerActivitiesImplementations(symbolCountModerationActivity);
    worker.registerActivitiesImplementations(forbiddenWordModerationActivity);
    worker.registerActivitiesImplementations(luckTestModerationActivity);
    testWorkflowEnvironment.start();
  }

  @Test
  void shouldSuccessWorkflow() {
    ModerationWorkflow workflow = workflowClient.newWorkflowStub(
        ModerationWorkflow.class,
        WorkflowOptions.newBuilder().setTaskQueue(MODERATION_TASK_QUEUE).build()
    );
    Moderation moderation = new Moderation(0, "Text", false, false);
    moderationHandler.startModeration(moderation);
    workflow.moderate(moderation);

    await()
        .atMost(10, SECONDS)
        .pollInterval(1, SECONDS)
        .until(() -> {
          Moderation actual = moderationDao.getById(0);
          assertThat(actual.isApproved()).isTrue();
          return true;
        });
  }
}
