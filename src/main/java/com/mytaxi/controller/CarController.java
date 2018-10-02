package com.mytaxi.controller;

import static com.mytaxi.controller.mapper.CarMapper.makeCarDO;
import static com.mytaxi.controller.mapper.CarMapper.makeCarDTO;
import static com.mytaxi.controller.mapper.CarMapper.makeCarDTOList;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.exception.GenericException;
import com.mytaxi.service.car.CarService;

/**
 * All operations with a car will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/cars")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(final CarService carService) {
	this.carService = carService;
    }

    @GetMapping("/{carId}")
    public CarDTO getCar(@PathVariable long carId) throws GenericException {
	return makeCarDTO(carService.findCar(carId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarDTO createCar(@Valid @RequestBody CarDTO carDTO) throws GenericException {
	CarDO carDO = makeCarDO(carDTO);
	return makeCarDTO(carService.createCar(carDO));
    }

    @DeleteMapping("/{carId}")
    public CarDTO deleteCar(@PathVariable long carId) throws GenericException {
	return makeCarDTO(carService.deleteCar(carId));
    }

    @PutMapping("/{carId}/{rating}")
    public CarDTO updateCar(@PathVariable long carId, @PathVariable Integer rating) throws GenericException {
	return makeCarDTO(carService.updateCar(carId, rating));
    }

    @GetMapping
    public List<CarDTO> listCar() {
	return makeCarDTOList(carService.listCar());
    }
}