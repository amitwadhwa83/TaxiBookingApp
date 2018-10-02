package com.mytaxi.domainobject;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.mytaxi.domainvalue.EngineType;

@Entity
@Table(name = "car", uniqueConstraints = @UniqueConstraint(name = "uc_licensePlate", columnNames = { "licensePlate" }))
public class CarDO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated = ZonedDateTime.now();

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateUpdated = ZonedDateTime.now();

    @Column(nullable = false)
    private String licensePlate;

    @Column(nullable = false)
    private Integer seatCount;

    @Column(nullable = false)
    private Boolean convertible = false;

    @Column(nullable = false)
    private Integer rating;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EngineType engineType;

    private String manufacturer;

    private Boolean selected;

    private CarDO() {
    }

    public CarDO(@NotNull(message = "License plate can not be null!") String licensePlate, @Min(1) Integer seatCount,
	    Boolean convertible, @Min(1) @Max(4) Integer rating,
	    @NotNull(message = "Engine type can not be null!") EngineType engineType, String manufacturer) {
	this.licensePlate = licensePlate;
	this.seatCount = seatCount;
	this.convertible = convertible;
	this.rating = rating;
	this.engineType = engineType;
	this.manufacturer = manufacturer;
	this.selected = false;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getLicensePlate() {
	return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
	this.licensePlate = licensePlate;
    }

    public Integer getSeatCount() {
	return seatCount;
    }

    public void setSeatCount(Integer seatCount) {
	this.seatCount = seatCount;
    }

    public Boolean getConvertible() {
	return convertible;
    }

    public void setConvertible(Boolean convertible) {
	this.convertible = convertible;
    }

    public Integer getRating() {
	return rating;
    }

    public void setRating(Integer rating) {
	this.rating = rating;
    }

    public EngineType getEngineType() {
	return engineType;
    }

    public void setEngineType(EngineType engineType) {
	this.engineType = engineType;
    }

    public ZonedDateTime getDateUpdated() {
	return dateUpdated;
    }

    public void setDateUpdated(ZonedDateTime dateUpdated) {
	this.dateUpdated = dateUpdated;
    }

    public String getManufacturer() {
	return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
	this.manufacturer = manufacturer;
    }

    public Boolean getSelected() {
	return selected;
    }

    public void setSelected(Boolean selected) {
	this.selected = selected;
    }
}
