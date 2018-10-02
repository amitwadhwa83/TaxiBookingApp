package com.mytaxi.service.car;

import java.util.List;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.exception.InvalidCarRatingException;

public interface CarService {

    CarDO createCar(CarDO carDO) throws ConstraintsViolationException;

    CarDO findCar(Long carId) throws EntityNotFoundException;

    CarDO updateCar(Long carId, Integer rating) throws InvalidCarRatingException, EntityNotFoundException;

    CarDO deleteCar(Long carId) throws EntityNotFoundException;

    List<CarDO> listCar();
}
