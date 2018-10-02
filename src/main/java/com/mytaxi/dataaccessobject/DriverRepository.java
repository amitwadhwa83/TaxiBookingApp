package com.mytaxi.dataaccessobject;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.EntityNotFoundException;

/**
 * Database Access Object for driver table.
 * <p/>
 */
public interface DriverRepository extends CrudRepository<DriverDO, Long>,JpaSpecificationExecutor<DriverDO>
{

    List<DriverDO> findByOnlineStatus(OnlineStatus onlineStatus);
    
    default DriverDO findDriverChecked(Long driverId) throws EntityNotFoundException {
	return findById(driverId).orElseThrow(() -> new EntityNotFoundException(driverId.toString()));
    }
}
