package com.mytaxi.datatransferobject;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mytaxi.domainvalue.EngineType;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CarDTO {

    private CarDTO() {
    }

    private CarDTO(Long id, String licensePlate, Integer seatCount, Boolean convertible, Integer rating,
	    EngineType engineType, String manufacturer) {
	this.id = id;
	this.licensePlate = licensePlate;
	this.seatCount = seatCount;
	this.convertible = convertible;
	this.rating = rating;
	this.engineType = engineType;
	this.manufacturer = manufacturer;
    }

    @JsonIgnore
    private Long id;

    @NotNull(message = "License plate can not be null!")
    private String licensePlate;

    @Min(1)
    private Integer seatCount;

    private Boolean convertible;

    @Min(1)
    @Max(4)
    private Integer rating;

    @NotNull(message = "Engine type can not be null!")
    private EngineType engineType;

    private String manufacturer;

    @JsonProperty
    public Long getId() {
	return id;
    }

    public String getLicensePlate() {
	return licensePlate;
    }

    public Integer getSeatCount() {
	return seatCount;
    }

    public Boolean getConvertible() {
	return convertible;
    }

    public Integer getRating() {
	return rating;
    }

    public EngineType getEngineType() {
	return engineType;
    }

    public String getManufacturer() {
	return manufacturer;
    }

    public static CarDTOBuilder newBuilder() {
	return new CarDTOBuilder();
    }

    public static class CarDTOBuilder {
	private Long id;
	private String licensePlate;
	private Integer seatCount;
	private Boolean convertible;
	private Integer rating;
	private EngineType engineType;
	private String manufacturer;

	public CarDTOBuilder setId(Long id) {
	    this.id = id;
	    return this;
	}

	public CarDTOBuilder setLicensePlate(String licensePlate) {
	    this.licensePlate = licensePlate;
	    return this;
	}

	public CarDTOBuilder setSeatCount(Integer seatCount) {
	    this.seatCount = seatCount;
	    return this;
	}

	public CarDTOBuilder setConvertible(Boolean convertible) {
	    this.convertible = convertible;
	    return this;
	}

	public CarDTOBuilder setRating(Integer rating) {
	    this.rating = rating;
	    return this;
	}

	public CarDTOBuilder setEngineType(EngineType engineType) {
	    this.engineType = engineType;
	    return this;
	}

	public CarDTOBuilder setManufacturer(String manufacturer) {
	    this.manufacturer = manufacturer;
	    return this;
	}

	public CarDTO createCarDTO() {
	    return new CarDTO(id, licensePlate, seatCount, convertible, rating, engineType, manufacturer);
	}
    }
}
