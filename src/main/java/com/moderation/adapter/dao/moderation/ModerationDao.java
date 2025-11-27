package com.moderation.adapter.dao.moderation;

import com.moderation.domain.Moderation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * DAO для модераций
 *
 * @author Gaben 20.10.2025
 */
@Slf4j
@Component
public class ModerationDao {

  private final Map<Integer, ModerationEntity> storage = new HashMap();
  private int idCounter = 0;

  public Moderation create(Moderation moderation) {
    int id = idCounter++;
    ModerationEntity entity = ModerationEntity.createNew(id, moderation.text());
    storage.put(id, entity);
    log.debug("Created moderation for {}", moderation);
    return Mapper.toDomain(entity);
  }

  public Moderation getById(Integer id) {
    validateModerationExist(id);
    ModerationEntity entity = storage.get(id);
    return Mapper.toDomain(entity);
  }

  public void updateInProgress(Integer moderationId) {
    ModerationEntity entity = storage.get(moderationId);
    entity.setInProgress(true);
  }

  public void updateFinalStatus(Integer id, boolean isFinished, boolean isApproved) {
    validateModerationExist(id);
    ModerationEntity entity = storage.get(id);
    entity.setFinished(isFinished);
    entity.setApproved(isApproved);
  }

  public Set<Integer> getAllIds() {
    return storage.keySet();
  }

  private void validateModerationExist(Integer id) {
    if (!storage.containsKey(id)) {
      throw new RuntimeException("Moderation with id " + id + " not found");
    }
  }
}
