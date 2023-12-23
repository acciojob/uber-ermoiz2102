package com.driver.services.impl;

import com.driver.cabNotAvailableException;
import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;
import com.driver.model.TripStatus;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		Customer customer=customerRepository2.findById(customerId).get();
		customerRepository2.delete(customer);
	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
		List<Driver>driverList=driverRepository2.findAll();
		Driver availableDriver=null;
		for(Driver driver:driverList){
			if(driver.getCab().getAvailable()){
				availableDriver=driver;
				break;
			}
		}
		if(availableDriver==null)
			throw new cabNotAvailableException("No cab available!");
       availableDriver.getCab().setAvailable(false);
		TripBooking tripBooking= new TripBooking();
		Customer customer=customerRepository2.findById(customerId).get();
		tripBooking.setCustomer(customer);
		tripBooking.setDistanceInKm(distanceInKm);
		tripBooking.setStatus(TripStatus.CONFIRMED);
		tripBooking.setBill(distanceInKm*availableDriver.getCab().getPerKmRate());
		tripBooking.setDriver(availableDriver);
		tripBooking.setFromLocation(fromLocation);
		tripBooking.setToLocation(toLocation);



		customer.getTripBookingList().add(tripBooking);
		availableDriver.getTripBookingList().add(tripBooking);


		tripBooking=tripBookingRepository2.save(tripBooking);

		return tripBooking;

	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly

		TripBooking tripBooking=tripBookingRepository2.findById(tripId).get();

		Driver driver=tripBooking.getDriver();
		Customer customer=tripBooking.getCustomer();

		tripBooking.setStatus(TripStatus.CANCELED);

		driver.getCab().setAvailable(true);

		tripBookingRepository2.save(tripBooking);


	}

	@Override
	public void completeTrip(Integer tripId){
		TripBooking tripBooking=tripBookingRepository2.findById(tripId).get();

		Driver driver=tripBooking.getDriver();
		Customer customer=tripBooking.getCustomer();

		tripBooking.setStatus(TripStatus.COMPLETED);
		driver.getCab().setAvailable(true);


		tripBookingRepository2.save(tripBooking);

	}
}
