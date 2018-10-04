package com.mytaxi.service.driver;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.mytaxi.dataaccessobject.DriverRepository;
import com.mytaxi.domainobject.DriverDO;
import com.mytaxi.domainvalue.OnlineStatus;

/**
 * Service to search for drivers
 *
 */
@Service
public class DriverSearchServiceImpl implements DriverSearchService {

    private final DriverRepository driverRepository;

    public DriverSearchServiceImpl(final DriverRepository driverRepository) {
	this.driverRepository = driverRepository;
    }

    @Override
    public List<DriverDO> searchDrivers(String username, OnlineStatus onlineStatus, String licensePlate, Integer rating) {

	return driverRepository
		.findAll(DriverSpecifications.withDynamicQuery(username, onlineStatus, licensePlate, rating));
    }
}

class DriverSpecifications {

    /**
     * Specification to construct dynamic query based on JPA Criteria API.
     * 
     * @param username
     * @param onlineStatus
     * @param licensePlate
     * @param rating
     * @return Specification<DriverDO> to be used in search
     */
    static Specification<DriverDO> withDynamicQuery(final String username, final OnlineStatus onlineStatus,
	    final String licensePlate, final Integer rating) {

	return new Specification<DriverDO>() {
	    @Override
	    public Predicate toPredicate(Root<DriverDO> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

		List<Predicate> predicates = new ArrayList<Predicate>();

		Optional.ofNullable(username)
			.ifPresent(username -> predicates.add(cb.like(root.get("username"), username)));

		Optional.ofNullable(onlineStatus)
			.ifPresent(onlineStatus -> predicates.add(cb.equal(root.get("onlineStatus"), onlineStatus)));

		Optional.ofNullable(licensePlate).ifPresent(
			licensePlate -> predicates.add(cb.like(root.join("car").get("licensePlate"), licensePlate)));

		Optional.ofNullable(rating)
			.ifPresent(rating -> predicates.add(cb.equal(root.join("car").get("rating"), rating)));

		return cb.and(predicates.toArray(new Predicate[predicates.size()]));
	    }
	};
    }
}