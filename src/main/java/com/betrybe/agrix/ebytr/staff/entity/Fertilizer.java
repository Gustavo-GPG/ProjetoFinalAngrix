package com.betrybe.agrix.ebytr.staff.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.List;

/**
 * The type Fertilizer.
 */
@Entity
@Table(name = "fertilizer")
public class Fertilizer {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;
  private String brand;
  private String composition;

  @ManyToMany(mappedBy = "fertilizers")
  private List<Crop> crops;

  public Fertilizer() {
  }

  /**
   * Instantiates a new Fertilizer.
   *
   * @param name        the name
   * @param brand       the brand
   * @param composition the composition
   */
  public Fertilizer(String name, String brand, String composition) {
    this.name = name;
    this.brand = brand;
    this.composition = composition;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getComposition() {
    return composition;
  }

  public void setComposition(String composition) {
    this.composition = composition;
  }

  public List<Crop> getCrops() {
    return crops;
  }

  public void setCrops(List<Crop> crops) {
    this.crops = crops;
  }
}
