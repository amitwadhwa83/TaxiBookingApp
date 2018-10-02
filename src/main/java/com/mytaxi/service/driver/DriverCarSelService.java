package com.mytaxi.service.driver;

import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.exception.IncorrectStatusException;

public interface DriverCarSelService {

    DriverDO assignDriverCar(Long driverId, Long carId)
	    throws IncorrectStatusException, EntityNotFoundException, CarAlreadyInUseException;

    DriverDO removeDriverCar(Long driverId) throws EntityNotFoundException;
}