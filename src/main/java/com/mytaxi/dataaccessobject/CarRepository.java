package com.mytaxi.dataaccessobject;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mytaxi.domainobject.CarDO;
import com.mytaxi.exception.EntityNotFoundException;

/**
 * Database Access Object for car table/domain.
 * <p/>
 */
@Repository
public interface CarRepository extends CrudRepository<CarDO, Long>, JpaSpecificationExecutor<CarDO> {

    Optional<CarDO> findByLicensePlate(String licensePlate);

    default CarDO findCarChecked(Long carId) throws EntityNotFoundException {
	return findById(carId).orElseThrow(() -> new EntityNotFoundException(carId.toString()));
    }
}
