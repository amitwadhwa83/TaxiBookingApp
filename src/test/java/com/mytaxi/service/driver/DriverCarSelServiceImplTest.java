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

import java.util.Optional;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.domainvalue.OnlineStatus;
import com.mytaxi.exception.CarAlreadyInUseException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.exception.GenericException;
import com.mytaxi.exception.IncorrectStatusException;

@RunWith(SpringRunner.class)
public class DriverCarSelServiceImplTest {

    CarRepository carRepository = mock(CarRepository.class);
    DriverRepository driverRepository = mock(DriverRepository.class);

    @InjectMocks
    DriverCarSelService service = new DriverCarSelServiceImpl(carRepository, driverRepository);

    Random random = new Random();

    @Test(expected = EntityNotFoundException.class)
    public final void testAssignDriverCarFailsForEntityNotFoundException() throws GenericException {
	// GIVEN
	when(driverRepository.findDriverChecked(any(Long.class))).thenCallRealMethod();
	when(driverRepository.findById(any(Long.class))).thenReturn(Optional.empty());

	// WHEN
	service.assignDriverCar(random.nextLong(), random.nextLong());

	// THEN
	// Expected exception
    }

    @Test(expected = IncorrectStatusException.class)
    public final void testAssignDriverCarFailsForIncorrectStatusException() throws GenericException {
	// GIVEN
	DriverDO driver = aDriverDO();
	driver.setOnlineStatus(OnlineStatus.OFFLINE);
	when(driverRepository.findDriverChecked(any(Long.class))).thenReturn(driver);

	// WHEN
	service.assignDriverCar(random.nextLong(), random.nextLong());

	// THEN
	// Expected exception
    }

    @Test(expected = EntityNotFoundException.class)
    public final void testAssignDriverCarFailsForCarEntityNotFoundException() throws GenericException {
	// GIVEN
	DriverDO driver = aDriverDO();
	when(driverRepository.findDriverChecked(any(Long.class))).thenReturn(driver);
	when(carRepository.findCarChecked(any(Long.class))).thenCallRealMethod();
	when(carRepository.findById(any(Long.class))).thenReturn(Optional.empty());

	// WHEN
	service.assignDriverCar(random.nextLong(), random.nextLong());

	// THEN
	// Expected exception
    }

    @Test(expected = CarAlreadyInUseException.class)
    public final void testAssignDriverCarFailsForCarAlreadyInUseException() throws GenericException {
	// GIVEN
	DriverDO driver = aDriverDO();
	when(driverRepository.findDriverChecked(any(Long.class))).thenReturn(driver);
	CarDO car = aCarDO();
	car.setSelected(true);
	when(carRepository.findCarChecked(any(Long.class))).thenReturn(car);

	// WHEN
	service.assignDriverCar(random.nextLong(), random.nextLong());

	// THEN
	// Expected exception
    }

    @Test
    public final void testAssignDriverCarAssignedACarToDriver() throws GenericException {
	// GIVEN
	DriverDO driver = aDriverDO();
	CarDO oldCar = driver.getCar();
	when(driverRepository.findDriverChecked(any(Long.class))).thenReturn(driver);
	when(carRepository.save(oldCar)).thenReturn(oldCar);

	CarDO newCar = aCarDO();
	newCar.setSelected(false);
	when(carRepository.findCarChecked(any(Long.class))).thenReturn(newCar);
	

	Long driverId = random.nextLong();
	Long carId = random.nextLong();

	// WHEN
	DriverDO response = service.assignDriverCar(driverId, carId);

	// THEN
	// Expected invocations
	verify(carRepository, times(1)).findCarChecked(carId);
	verify(driverRepository, times(1)).findDriverChecked(driverId);
	verify(carRepository, times(1)).save(oldCar);
	verify(carRepository, times(1)).save(newCar);
	// Assertions
	assertThat(response, is(equalTo(driver)));
	assertThat(false, is(equalTo(oldCar.getSelected())));
    }

    @Test(expected = EntityNotFoundException.class)
    public final void testRemoveDriverCarFailsForEntityNotFoundException() throws GenericException {
	// GIVEN
	when(driverRepository.findDriverChecked(any(Long.class))).thenCallRealMethod();
	when(driverRepository.findById(any(Long.class))).thenReturn(Optional.empty());

	// WHEN
	service.removeDriverCar(random.nextLong());

	// THEN
	// Expected exception
    }

    @Test
    public final void testRemoveDriverCarRemovesTheCarAssignedToDriver() throws GenericException {
	// GIVEN
	DriverDO driver = aDriverDO();
	CarDO car = driver.getCar();
	when(driverRepository.findDriverChecked(any(Long.class))).thenReturn(driver);
	when(carRepository.save(any(CarDO.class))).thenReturn(car);

	Long driverId = random.nextLong();

	// WHEN
	DriverDO response = service.removeDriverCar(driverId);

	// THEN
	// Expected invocations
	verify(driverRepository, times(1)).findDriverChecked(driverId);
	verify(carRepository, times(1)).save(car);
	verify(driverRepository, times(1)).save(driver);
	// Assertions
	assertThat(response, is(equalTo(driver)));
	assertThat(false, is(equalTo(car.getSelected())));
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