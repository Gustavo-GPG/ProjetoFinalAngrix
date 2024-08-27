package com.betrybe.agrix.ebytr.staff.controller.dto;

import com.betrybe.agrix.ebytr.staff.entity.Fertilizer;

/**
 * The type Fertilizer creation dto.
 */
public record FertilizerCreationDto(String name, String brand, String composition) {

  public Fertilizer toEntity() {
    return new Fertilizer(name, brand, composition);
  }

}
