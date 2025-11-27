package com.moderation.service;

import static java.lang.Thread.sleep;

import com.moderation.adapter.dao.moderation.ModerationDao;
import com.moderation.domain.Moderation;
import com.moderation.domain.ModerationStatus;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Реализация этапов модерации
 *
 * @author Gaben 21.10.2025
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ModerationService {

  private static final int CONTENT_LENGTH = 140;
  private static final List<String> forbiddenFords = List.of("box", "men", "noigram", "metan");

  public ModerationStatus symbolCountModeration(Moderation moderation) {
    log.info("Started first stage for {}", moderation);
    simulateLongExecution();
    var isApproved = moderation.text().length() < CONTENT_LENGTH;
    return new ModerationStatus(true, isApproved);
  }

  public ModerationStatus forbiddenWordModeration(Moderation moderation) {
    log.info("Started second stage for {}", moderation);
    simulateLongExecution();
    var content = moderation.text();
    boolean isApproved = forbiddenFords.stream()
        .noneMatch(content::contains);
    return new ModerationStatus(true, isApproved);
  }

  public ModerationStatus luckyModeration(Moderation moderation) {
    log.info("Started third stage for {}", moderation);
    checkLuck();
    return new ModerationStatus(true, true);
  }

  private void simulateLongExecution() {
    try {
      sleep(10_000L);
    } catch (InterruptedException ignored) {
    }
  }

  private void checkLuck() {
    if (Math.random() * 10 > 4) {
      throw new RuntimeException("You are not lucky!");
    }
  }
}
