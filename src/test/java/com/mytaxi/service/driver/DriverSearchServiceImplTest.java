package com.mytaxi.service.driver;

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.domainvalue.OnlineStatus;

@RunWith(SpringRunner.class)
public class DriverSearchServiceImplTest {

    DriverRepository driverRepository = mock(DriverRepository.class);

    @InjectMocks
    DriverSearchService service = new DriverSearchServiceImpl(driverRepository);

    Random random = new Random();

    @Captor
    ArgumentCaptor<Specification<DriverDO>> argCaptor;

    @Test
    public final void testSearchDrivers() {
	// GIVEN
	String username = randomAlphanumeric(5);
	OnlineStatus onlineStatus = OnlineStatus.OFFLINE;
	String licensePlate = randomAlphanumeric(5);
	Integer rating = getRating();

	List<DriverDO> listDriverDO = aDriverDOList();
	when(driverRepository.findAll(any())).thenReturn(listDriverDO);

	// WHEN
	List<DriverDO> response = service.searchDrivers(username, onlineStatus, licensePlate, rating);

	// THEN
	// Expected invocations
	verify(driverRepository, times(1)).findAll(any());
	// Assertions
	assertThat(response, is(equalTo(listDriverDO)));
    }

    private List<DriverDO> aDriverDOList() {

	int size = random.nextInt(10);
	List<DriverDO> listDriverDO = new ArrayList<DriverDO>(size);
	int count = 0;
	while (count < size) {
	    count++;
	    listDriverDO.add(aDriverDO());
	}
	return listDriverDO;
    }

    private DriverDO aDriverDO() {
	DriverDO driver = new DriverDO(randomAlphanumeric(5), randomAlphanumeric(5));
	driver.setId(random.nextLong());
	driver.setOnlineStatus(OnlineStatus.ONLINE);
	driver.setCar(aCarDO());
	driver.getCar().setSelected(true);
	return driver;
    }

    private CarDO aCarDO() {
	CarDO car = new CarDO(randomAlphanumeric(5), random.nextInt(1), random.nextBoolean(), getRating(),
		EngineType.GAS, randomAlphanumeric(5));
	car.setId(random.nextLong());
	return car;
    }

    private Integer getRating() {
	int rating = 0;
	while (rating <= 0 || rating > 4) {
	    rating = random.nextInt(2);
	}
	return rating;
    }
}