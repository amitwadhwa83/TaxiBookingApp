package com.mytaxi.service.driver;

import java.util.List;

import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;

public interface DriverSearchService {

    List<DriverDO> searchDrivers(String username, OnlineStatus onlineStatus, String licensePlate, Integer rating);
}
