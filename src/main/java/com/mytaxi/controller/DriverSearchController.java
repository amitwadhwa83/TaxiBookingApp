package com.mytaxi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mytaxi.controller.mapper.DriverMapper;
import com.mytaxi.datatransferobject.DriverDTO;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.service.driver.DriverSearchService;

/**
 * All operations related to driver search will be routed by this controller
 */
@RestController
@RequestMapping("v1/driverSearch")
public class DriverSearchController {

    private final DriverSearchService driverSearchService;

    @Autowired
    public DriverSearchController(final DriverSearchService driverSearchService) {
	this.driverSearchService = driverSearchService;
    }

    @GetMapping
    public List<DriverDTO> searchDrivers(@RequestParam(required = false) String username,
	    @RequestParam(required = false) OnlineStatus onlineStatus,
	    @RequestParam(required = false) String licensePlate, @RequestParam(required = false) Integer rating) {

	return DriverMapper
		.makeDriverDTOList(driverSearchService.searchDrivers(username, onlineStatus, licensePlate, rating));
    }
}
