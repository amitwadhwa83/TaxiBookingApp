package com.mytaxi.service.car;

import java.time.ZonedDateTime;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.exception.InvalidCarRatingException;

/**
 * Service to encapsulate the link between DAO and controller and to have
 * business logic for car specific things.
 */

@Service
public class CarServiceImpl implements CarService {

    private static final Logger LOG = LoggerFactory.getLogger(CarServiceImpl.class);

    private final CarRepository carRepository;
    private final ValueRange range = ValueRange.of(1, 4);

    public CarServiceImpl(final CarRepository carRepository) {
	this.carRepository = carRepository;
    }

    /**
     * Creates a new car.
     *
     * @param CarDO
     * @return CarDO the created car
     * @throws ConstraintsViolationException
     *             if a car already exists with the given license or car already
     *             exists with given inputs
     */
    @Override
    public CarDO createCar(CarDO carDO) throws ConstraintsViolationException {
	if (carRepository.findByLicensePlate(carDO.getLicensePlate()).isPresent()) {
	    throw new ConstraintsViolationException(
		    "A car with this license plate already exists:" + carDO.getLicensePlate());
	}
	CarDO car;
	try {
	    car = carRepository.save(carDO);
	} catch (DataIntegrityViolationException e) {
	    LOG.warn("ConstraintsViolationException while creating a car: {}", carDO, e);
	    throw new ConstraintsViolationException(e.getMessage());
	}
	return car;
    }

    /**
     * Find a car by id.
     *
     * @param Long
     *            carId
     * @return CarDO found car
     * @throws EntityNotFoundException
     *             if no car with the given input is found
     */
    @Override
    public CarDO findCar(Long carId) throws EntityNotFoundException {
	return findCarChecked(carId);
    }

    /**
     * Updates a car rating
     *
     * @param Long
     *            carId,Integer rating
     * @return CarDO update car
     * @throws EntityNotFoundException
     *             if no car with the given input is found,
     *             InvalidCarRatingException for invalid rating input
     */
    @Override
    @Transactional
    public CarDO updateCar(Long carId, Integer rating) throws InvalidCarRatingException, EntityNotFoundException {
	CarDO car = findCarChecked(carId);
	if (!range.isValidValue(rating)) {
	    throw new InvalidCarRatingException(rating.toString());
	}
	car.setDateUpdated(ZonedDateTime.now());
	car.setRating(rating);
	return car;
    }

    /**
     * Deletes an existing car by id.
     *
     * @param Long
     *            carId
     * @return CarDO deleted car
     * @throws EntityNotFoundException
     *             if no car with the given input is found
     */
    @Override
    @Transactional
    public CarDO deleteCar(Long carId) throws EntityNotFoundException {
	CarDO car = findCarChecked(carId);
	carRepository.deleteById(carId);
	return car;
    }

    /**
     * Find all cars available in system
     * 
     * @return List<CarDO> List of cars in system
     */
    @Override
    public List<CarDO> listCar() {
	List<CarDO> listCardDO = new ArrayList<CarDO>();
	carRepository.findAll().forEach(listCardDO::add);
	return listCardDO;
    }

    private CarDO findCarChecked(Long carId) throws EntityNotFoundException {
	return carRepository.findCarChecked(carId);
    }
}