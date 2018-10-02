package com.mytaxi.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mytaxi.controller.mapper.DriverMapper;
import com.mytaxi.datatransferobject.DriverDTO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.exception.GenericException;
import com.mytaxi.service.driver.DriverCarSelService;
import com.mytaxi.service.driver.DriverService;

/**
 * All operations with a driver will be routed by this controller.
 * <p/>
 */
@RestController
@RequestMapping("v1/drivers")
public class DriverController {

    private final DriverService driverService;
    private final DriverCarSelService driverCarSelService;

    @Autowired
    public DriverController(final DriverService driverService, final DriverCarSelService driverCarSelService) {
	this.driverService = driverService;
	this.driverCarSelService = driverCarSelService;
    }

    @GetMapping("/{driverId}")
    public DriverDTO getDriver(@PathVariable long driverId) throws EntityNotFoundException {
	return DriverMapper.makeDriverDTO(driverService.find(driverId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DriverDTO createDriver(@Valid @RequestBody DriverDTO driverDTO) throws ConstraintsViolationException {
	DriverDO driverDO = DriverMapper.makeDriverDO(driverDTO);
	return DriverMapper.makeDriverDTO(driverService.create(driverDO));
    }

    @DeleteMapping("/{driverId}")
    public void deleteDriver(@PathVariable long driverId) throws EntityNotFoundException {
	driverService.delete(driverId);
    }

    @PutMapping("/{driverId}")
    public void updateLocation(@PathVariable long driverId, @RequestParam double longitude,
	    @RequestParam double latitude) throws EntityNotFoundException {
	driverService.updateLocation(driverId, longitude, latitude);
    }

    @GetMapping
    public List<DriverDTO> findDrivers(@RequestParam OnlineStatus onlineStatus) {
	return DriverMapper.makeDriverDTOList(driverService.find(onlineStatus));
    }

    @PostMapping("/{driverId}/assignDriverCar/{carId}")
    @ResponseStatus(HttpStatus.CREATED)
    public DriverDO assignDriverCar(@PathVariable long driverId, @PathVariable long carId) throws GenericException {
	return driverCarSelService.assignDriverCar(driverId, carId);
    }

    @DeleteMapping("/{driverId}/removeDriverCar")
    public DriverDO removeDriverCar(@PathVariable long driverId) throws GenericException {
	return driverCarSelService.removeDriverCar(driverId);
    }
}
