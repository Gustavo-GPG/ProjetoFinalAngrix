package com.betrybe.agrix.ebytr.staff.service.exeption;

/**
 * The type Crop not found exception.
 */
public class CropNotFoundException extends NotFoundException {
  public CropNotFoundException(String message) {
    super(message);
  }
}
