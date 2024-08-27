package com.betrybe.agrix.ebytr.staff.service;

import com.betrybe.agrix.ebytr.staff.entity.Fertilizer;
import com.betrybe.agrix.ebytr.staff.repository.FertilizerRepository;
import com.betrybe.agrix.ebytr.staff.service.exeption.FertilizerNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The type Fertilizer service.
 */
@Service
public class FertilizerService {
  private final FertilizerRepository fertilizerRepository;

  @Autowired
  public FertilizerService(FertilizerRepository fertilizerRepository) {
    this.fertilizerRepository = fertilizerRepository;
  }

  public Fertilizer save(Fertilizer fertilizer) {
    return fertilizerRepository.save(fertilizer);
  }

  public List<Fertilizer> getAll() {
    return fertilizerRepository.findAll();
  }

  public Fertilizer getById(Integer id) throws FertilizerNotFoundException {
    return fertilizerRepository.findById(id)
        .orElseThrow(() -> new FertilizerNotFoundException("Fertilizante não encontrado!"));
  }
}
