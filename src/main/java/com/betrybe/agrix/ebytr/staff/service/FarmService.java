package com.betrybe.agrix.ebytr.staff.service;


import com.betrybe.agrix.ebytr.staff.controller.dto.CropDto;
import com.betrybe.agrix.ebytr.staff.entity.Crop;
import com.betrybe.agrix.ebytr.staff.entity.Farm;
import com.betrybe.agrix.ebytr.staff.repository.CropRepository;
import com.betrybe.agrix.ebytr.staff.repository.FarmRepository;
import com.betrybe.agrix.ebytr.staff.service.exeption.CropNotFoundException;
import com.betrybe.agrix.ebytr.staff.service.exeption.FarmNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Farm service.
 */
@Service
public class FarmService {
  private final FarmRepository farmRepository;
  private final CropRepository cropRepository;

  /**
   * Instantiates a new Farm service.
   *
   * @param farmRepository the farm repository
   */
  @Autowired
  public FarmService(FarmRepository farmRepository, CropRepository cropRepository) {
    this.farmRepository = farmRepository;
    this.cropRepository = cropRepository;
  }

  /**
   * Find by id farm.
   *
   * @param id the id
   * @return the farm
   * @throws FarmNotFoundException the farm not found exception
   */
  public Farm findById(Integer id) throws FarmNotFoundException {
    return farmRepository.findById(Math.toIntExact(id))
        .orElseThrow(() -> new FarmNotFoundException("Fazenda não encontrada!"));
  }

  /**
   * Find all list.
   *
   * @return the list
   */
  public List<Farm> findAll() {
    return farmRepository.findAll();
  }

  /**
   * Create farm.
   *
   * @param farm the farm
   * @return the farm
   */
  public Farm create(Farm farm) {
    return farmRepository.save(farm);
  }

  /**
   * Update farm.
   *
   * @param id   the id
   * @param farm the farm
   * @return the farm
   * @throws FarmNotFoundException the farm not found exception
   */
  public Farm update(Integer id, Farm farm) throws FarmNotFoundException {
    Farm farmToUpdate = findById(id);

    farmToUpdate.setName(farm.getName());
    farmToUpdate.setSize(farm.getSize());

    return farmRepository.save(farmToUpdate);
  }

  /**
   * Delete by id farm.
   *
   * @param id the id
   * @return the farm
   * @throws FarmNotFoundException the farm not found exception
   */
  public Farm deleteById(Integer id) throws FarmNotFoundException {
    Farm farm = findById(id);
    farmRepository.deleteById(Math.toIntExact(id));
    return farm;
  }

  /**
   * To crop dto list.
   *
   * @param farm the farm
   * @return the list
   */
  public List<CropDto> toCropDto(Farm farm) {
    return farm.getCrops().stream()
        .map(CropDto::fromEntity)
        .toList();
  }

  /**
   * Gets all crops.
   *
   * @return the all crops
   */
  public List<Crop> getAllCrops() {
    List<Farm> farms = farmRepository.findAll();
    return farms.stream()
        .flatMap(farm -> farm.getCrops().stream())
        .collect(Collectors.toList());
  }

  public Crop getCropById(Integer id) throws CropNotFoundException {
    return cropRepository.findById(id)
        .orElseThrow(() -> new CropNotFoundException("Plantação não encontrada!"));
  }

  public List<Crop> getCropsByHarvestDateBetween(LocalDate start, LocalDate end) {
    return cropRepository.findByHarvestDateBetween(start, end);
  }
}