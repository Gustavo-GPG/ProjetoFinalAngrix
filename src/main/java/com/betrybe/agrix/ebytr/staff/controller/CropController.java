package com.betrybe.agrix.ebytr.staff.controller;


import com.betrybe.agrix.ebytr.staff.controller.dto.CropDto;
import com.betrybe.agrix.ebytr.staff.controller.dto.FertilizerDto;
import com.betrybe.agrix.ebytr.staff.entity.Crop;
import com.betrybe.agrix.ebytr.staff.service.CropService;
import com.betrybe.agrix.ebytr.staff.service.FarmService;
import com.betrybe.agrix.ebytr.staff.service.exeption.CropNotFoundException;
import com.betrybe.agrix.ebytr.staff.service.exeption.FertilizerNotFoundException;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Crop controller.
 */
@RestController
@RequestMapping("/crops")
public class CropController {
  private final FarmService farmService;
  private final CropService cropService;

  @Autowired
  public CropController(
      FarmService farmService,
      CropService cropService1
  ) {
    this.farmService = farmService;
    this.cropService = cropService1;
  }


  /**
   * Gets crops.
   *
   * @return the crops
   */
  @GetMapping
  @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_MANAGER')")
  public List<CropDto> getCrops() {
    List<Crop> crops = farmService.getAllCrops();
    return crops.stream()
        .map(CropDto::fromEntity)
        .toList();
  }

  @GetMapping("/{id}")
  public CropDto getCropById(@PathVariable Integer id) throws CropNotFoundException {
    return CropDto.fromEntity(farmService.getCropById(id));
  }

  /**
   * Gets crops by date.
   *
   * @param start the start
   * @param end   the end
   * @return the crops by date
   */
  @GetMapping("/search")
  public List<CropDto> getCropsByDate(@RequestParam LocalDate start, @RequestParam LocalDate end) {
    List<CropDto> crops = farmService.getCropsByHarvestDateBetween(start, end)
        .stream()
        .map(CropDto::fromEntity)
        .toList();

    return crops;
  }

  /**
   * Add crop to fertilizer response entity.
   *
   * @param cropId       the crop id
   * @param fertilizerId the fertilizer id
   * @return the response entity
   * @throws CropNotFoundException       the crop not found exception
   * @throws FertilizerNotFoundException the fertilizer not found exception
   */
  @PostMapping("/{cropId}/fertilizers/{fertilizerId}")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<String> addCropToFertilizer(
      @PathVariable Integer cropId,
      @PathVariable Integer fertilizerId
  ) throws CropNotFoundException, FertilizerNotFoundException {
    cropService.addCropToFertilizer(cropId, fertilizerId);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body("Fertilizante e plantação associados com sucesso!");
  }

  /**
   * Gets fertilizer from crop.
   *
   * @param cropId the crop id
   * @return the fertilizer from crop
   * @throws CropNotFoundException the crop not found exception
   */
  @GetMapping("/{cropId}/fertilizers")
  @ResponseStatus(HttpStatus.OK)
  public List<FertilizerDto> getFertilizerFromCrop(
      @PathVariable Integer cropId
  ) throws CropNotFoundException {

    return cropService.getFertilizerFromCrop(cropId)
        .stream()
        .map(FertilizerDto::fromEntity)
        .toList();
  }
}
