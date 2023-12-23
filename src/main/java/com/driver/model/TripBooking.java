package com.driver.model;

import javax.persistence.*;

@Entity
@Table(name="tripBooking")
public class TripBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tripBookingId;

    private String startLocation;
     private String endLocation;

     private Integer tripKm;
      private  Integer bill;

      @Enumerated(value = EnumType.STRING)

      private TripStatus tripStatus;

      @ManyToOne
      @JoinColumn
    private Driver driver;

      @ManyToOne
    @JoinColumn
    private Customer customer;

    public Integer getTripBookingId() {
        return tripBookingId;
    }

    public void setTripBookingId(Integer tripBookingId) {
        this.tripBookingId = tripBookingId;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public Integer getTripKm() {
        return tripKm;
    }

    public void setTripKm(Integer tripKm) {
        this.tripKm = tripKm;
    }

    public Integer getBill() {
        return bill;
    }

    public void setBill(Integer bill) {
        this.bill = bill;
    }

    public TripStatus getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(TripStatus tripStatus) {
        this.tripStatus = tripStatus;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public TripBooking() {
    }




}
