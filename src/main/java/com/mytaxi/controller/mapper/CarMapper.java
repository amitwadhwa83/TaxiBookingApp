package com.mytaxi.controller.mapper;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.mytaxi.datatransferobject.CarDTO;
import com.mytaxi.domainobject.CarDO;

public class CarMapper {

    public static CarDO makeCarDO(@Valid CarDTO carDTO) {
	return new CarDO(carDTO.getLicensePlate(), carDTO.getSeatCount(), carDTO.getConvertible(), carDTO.getRating(),
		carDTO.getEngineType(),carDTO.getManufacturer());
    }

    public static CarDTO makeCarDTO(@Valid CarDO carDO) {
	return CarDTO.newBuilder().setConvertible(carDO.getConvertible()).setEngineType(carDO.getEngineType())
		.setId(carDO.getId()).setLicensePlate(carDO.getLicensePlate()).setRating(carDO.getRating())
		.setSeatCount(carDO.getSeatCount()).setManufacturer(carDO.getManufacturer()).createCarDTO();

    }

    public static List<CarDTO> makeCarDTOList(List<CarDO> cars) {
	return cars.stream().map(CarMapper::makeCarDTO).collect(Collectors.toList());
    }
}
