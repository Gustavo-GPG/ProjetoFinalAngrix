package com.betrybe.agrix.ebytr.staff.service;


import com.betrybe.agrix.ebytr.staff.entity.Crop;
import com.betrybe.agrix.ebytr.staff.entity.Farm;
import com.betrybe.agrix.ebytr.staff.entity.Fertilizer;
import com.betrybe.agrix.ebytr.staff.repository.CropRepository;
import com.betrybe.agrix.ebytr.staff.service.exeption.CropNotFoundException;
import com.betrybe.agrix.ebytr.staff.service.exeption.FarmNotFoundException;
import com.betrybe.agrix.ebytr.staff.service.exeption.FertilizerNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Crop service.
 */
@Service
public class CropService {
  private final CropRepository cropRepository;
  private final FarmService farmService;
  private final FertilizerService fertilizerService;

  /**
   * Instantiates a new Crop service.
   *
   * @param cropRepository the crop repository
   * @param farmService    the farm service
   */
  @Autowired
  public CropService(CropRepository cropRepository, FarmService farmService,
      FertilizerService fertilizerService) {
    this.cropRepository = cropRepository;
    this.farmService = farmService;
    this.fertilizerService = fertilizerService;
  }

  /**
   * Find by id crop.
   *
   * @param id the id
   * @return the crop
   * @throws CropNotFoundException the crop not found exception
   */
  public Crop findById(Integer id) throws CropNotFoundException {
    return cropRepository.findById(id)
        .orElseThrow();
  }

  /**
   * Find all list.
   *
   * @return the list
   */
  public List<Crop> findAll() {
    return cropRepository.findAll();
  }

  /**
   * Create crop.
   *
   * @param crop the crop
   * @return the crop
   */
  public Crop save(Crop crop) {
    return cropRepository.save(crop);
  }

  /**
   * Update crop.
   *
   * @param id   the id
   * @param crop the crop
   * @return the crop
   * @throws CropNotFoundException the crop not found exception
   */
  public Crop update(Integer id, Crop crop) throws CropNotFoundException {
    Crop cropToUpdate = findById(id);

    cropToUpdate.setName(crop.getName());
    cropToUpdate.setPlantedArea(crop.getPlantedArea());

    return cropRepository.save(cropToUpdate);
  }

  /**
   * Delete by id crop.
   *
   * @param id the id
   * @return the crop
   * @throws CropNotFoundException the crop not found exception
   */
  public Crop deleteById(Integer id) throws CropNotFoundException {
    Crop crop = findById(id);
    cropRepository.deleteById(id);
    return crop;
  }

  /**
   * Sets crop farm.
   *
   * @param crop the crop id
   * @param farmId the farm id
   * @return the crop farm
   * @throws FarmNotFoundException the farm not found exception
   */
  public Crop setCropFarm(Crop crop, Integer farmId)
      throws FarmNotFoundException {
    Farm farm = farmService.findById(farmId);

    crop.setFarm(farm);
    return cropRepository.save(crop);
  }

  /**
   * Remove crop farm crop.
   *
   * @param cropId the crop id
   * @return the crop
   * @throws CropNotFoundException the crop not found exception
   */
  public Crop removeCropFarm(Integer cropId) throws CropNotFoundException {
    Crop crop = findById(cropId);
    crop.setFarm(null);
    return cropRepository.save(crop);
  }

  /**
   * Add crop to fertilizer.
   *
   * @param cropId       the crop id
   * @param fertilizerId the fertilizer id
   * @throws FertilizerNotFoundException the fertilizer not found exception
   */
  public void addCropToFertilizer(Integer cropId, Integer fertilizerId)
      throws FertilizerNotFoundException, CropNotFoundException {
    Crop crop = cropRepository.findById(cropId)
        .orElseThrow(() -> new CropNotFoundException("Plantação não encontrada!"));
    Fertilizer fertilizer = fertilizerService.getById(fertilizerId);
    crop.getFertilizers().add(fertilizer);
    cropRepository.save(crop);
  }

  /**
   * Gets fertilizer from crop.
   *
   * @param cropId the crop id
   * @return the fertilizer from crop
   * @throws CropNotFoundException the crop not found exception
   */
  public List<Fertilizer> getFertilizerFromCrop(Integer cropId) throws CropNotFoundException {
    Crop crop = cropRepository.findById(cropId)
        .orElseThrow(() -> new CropNotFoundException("Plantação não encontrada!"));
    return crop.getFertilizers();
  }
}