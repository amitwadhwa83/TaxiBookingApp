package com.mytaxi.service.car;

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringRunner;

import com.mytaxi.dataaccessobject.CarRepository;
import com.mytaxi.domainobject.CarDO;
import com.mytaxi.domainvalue.EngineType;
import com.mytaxi.exception.ConstraintsViolationException;
import com.mytaxi.exception.EntityNotFoundException;
import com.mytaxi.exception.GenericException;
import com.mytaxi.exception.InvalidCarRatingException;

@RunWith(SpringRunner.class)
public class CarServiceImplTest {

    CarRepository carRepository = mock(CarRepository.class);

    @InjectMocks
    CarService service = new CarServiceImpl(carRepository);

    Random random = new Random();

    @Captor
    ArgumentCaptor<CarDO> carArgCaptor;

    @Test(expected = ConstraintsViolationException.class)
    public final void testCreateCarFailsForConstraintsViolationException() throws ConstraintsViolationException {
	// GIVEN
	CarDO car = aCarDO();
	when(carRepository.findByLicensePlate(anyString())).thenReturn(Optional.of(car));

	// WHEN
	service.createCar(car);

	// THEN
	// Expected exception
    }

    @Test
    public final void testCreateCarCreateACar() throws ConstraintsViolationException {
	// GIVEN
	CarDO car = aCarDO();
	when(carRepository.findByLicensePlate(anyString())).thenReturn(Optional.empty());
	when(carRepository.save(any(CarDO.class))).thenReturn(car);

	// WHEN
	CarDO response = service.createCar(car);

	// THEN
	// Expected invocations
	verify(carRepository, times(1)).findByLicensePlate(car.getLicensePlate());
	verify(carRepository, times(1)).save(carArgCaptor.capture());
	// Assertions
	assertThat(response, is(equalTo(carArgCaptor.getAllValues().get(0))));
    }

    @Test(expected = EntityNotFoundException.class)
    public final void testFindCarFailsForEntityNotFoundException() throws EntityNotFoundException {
	// GIVEN
	Long carId = random.nextLong();
	when(carRepository.findCarChecked(any(Long.class))).thenCallRealMethod();
	when(carRepository.findById(any(Long.class))).thenReturn(Optional.empty());

	// WHEN
	service.findCar(carId);

	// THEN
	// Expected exception
    }

    @Test
    public final void testFindCarReturnsACar() throws EntityNotFoundException {
	// GIVEN
	CarDO car = aCarDO();
	when(carRepository.findCarChecked(any(Long.class))).thenReturn(car);

	// WHEN
	CarDO response = service.findCar(car.getId());

	// THEN
	// Expected invocations
	verify(carRepository, times(1)).findCarChecked(car.getId());
	// Assertions
	assertThat(response, is(equalTo(car)));
    }

    @Test(expected = InvalidCarRatingException.class)
    public final void testUpdateCarFailsForInvalidCarRatingException() throws GenericException {
	// GIVEN
	Long carId = random.nextLong();

	// WHEN
	service.updateCar(carId, Integer.MAX_VALUE);

	// THEN
	// Expected exception
    }

    @Test(expected = EntityNotFoundException.class)
    public final void testUpdateCarFailsForEntityNotFoundException() throws GenericException {
	// GIVEN
	Long carId = random.nextLong();
	when(carRepository.findCarChecked(any(Long.class))).thenCallRealMethod();
	when(carRepository.findById(any(Long.class))).thenReturn(Optional.empty());

	// WHEN
	service.updateCar(carId, 1);

	// THEN
	// Expected exception
    }

    @Test
    public final void testUpdateCarUpdateCarRating() throws GenericException {
	// GIVEN
	CarDO car = aCarDO();
	car.setRating(1);
	when(carRepository.findCarChecked(any(Long.class))).thenReturn(car);

	// WHEN
	CarDO response = service.updateCar(car.getId(), 2);

	// THEN
	// Expected invocations
	verify(carRepository, times(1)).findCarChecked(car.getId());
	// Assertions
	assertThat(response, is(equalTo(car)));
    }

    @Test(expected = EntityNotFoundException.class)
    public final void testDeleteCarFailsForEntityNotFoundException() throws GenericException {
	// GIVEN
	Long carId = random.nextLong();
	when(carRepository.findCarChecked(any(Long.class))).thenCallRealMethod();
	when(carRepository.findById(any(Long.class))).thenReturn(Optional.empty());

	// WHEN
	service.deleteCar(carId);

	// THEN
	// Expected exception
    }

    @Test
    public final void testDeleteCarDeletesACar() throws GenericException {
	// GIVEN
	CarDO car = aCarDO();
	when(carRepository.findCarChecked(any(Long.class))).thenReturn(car);

	// WHEN
	CarDO response = service.deleteCar(car.getId());

	// THEN
	// Expected invocations
	verify(carRepository, times(1)).deleteById(car.getId());
	// Assertions
	assertThat(response, is(equalTo(car)));
    }

    @Test
    public final void testListCarReturnsACarList() throws GenericException {
	// GIVEN
	Iterable<CarDO> carList = aCarDOList();
	when(carRepository.findAll()).thenReturn(carList);

	// WHEN
	List<CarDO> response = service.listCar();

	// THEN
	// Expected invocations
	verify(carRepository, times(1)).findAll();
	// Assertions
	assertThat(response, is(equalTo(carList)));
    }

    private CarDO aCarDO() {
	CarDO car = new CarDO(randomAlphanumeric(5), random.nextInt(1), random.nextBoolean(), getRating(),
		EngineType.GAS, randomAlphanumeric(5));
	car.setId(random.nextLong());
	return car;
    }

    private Iterable<CarDO> aCarDOList() {

	int size = random.nextInt(10);
	List<CarDO> listCarDO = new ArrayList<CarDO>(size);
	int count = 0;
	while (count < size) {
	    count++;
	    listCarDO.add(aCarDO());
	}
	return listCarDO;
    }

    private Integer getRating() {
	int rating = 0;
	while (rating <= 0 || rating > 4) {
	    rating = random.nextInt(2);
	}
	return rating;
    }
}