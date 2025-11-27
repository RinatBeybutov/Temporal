package com.moderation.entrypoint.http;

import com.moderation.domain.Moderation;
import com.moderation.entrypoint.http.dto.ModerationCreateDto;
import com.moderation.entrypoint.http.dto.ModerationStatusViewDto;
import com.moderation.service.ModerationHandler;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для модерации постов
 *
 * @author Gaben 20.10.2025
 */
@RestController
@RequestMapping("/moderation")
@RequiredArgsConstructor
public class ModerationController {

  private final ModerationHandler service;

  @PostMapping
  @Operation(summary = "Создать новую заявку на модерацию")
  public ResponseEntity<Integer> createModeration(@RequestBody ModerationCreateDto dto) {
    Moderation moderation = Mapper.toDomain(dto);
    Integer id = service.startModeration(moderation);
    return ResponseEntity.ok(id);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Получить статус модерации по id")
  public ResponseEntity<ModerationStatusViewDto> getStatus(@PathVariable Integer id) {
    Moderation moderation = service.getById(id);
    var status = Mapper.toStatus(moderation);
    return ResponseEntity.ok(status);
  }

  @GetMapping
  @Operation(summary = "Получить список идентификаторов заявок на модерацию")
  public ResponseEntity<Set<Integer>> getModerationIds() {
    return ResponseEntity.ok(service.getAllIds());
  }
}
