package com.moderation.entrypoint.http;

import com.moderation.domain.Moderation;
import com.moderation.entrypoint.http.dto.ModerationCreateDto;
import com.moderation.entrypoint.http.dto.ModerationStatusViewDto;

/**
 * Маппер для дто модерации
 *
 * @author Gaben 20.10.2025
 */
public class Mapper {

  public static Moderation toDomain(ModerationCreateDto dto) {
    return new Moderation(null, dto.text(), false, false);
  }

  public static ModerationStatusViewDto toStatus(Moderation moderation) {
    return new ModerationStatusViewDto(
        moderation.isFinished(),
        moderation.isApproved()
    );
  }
}
