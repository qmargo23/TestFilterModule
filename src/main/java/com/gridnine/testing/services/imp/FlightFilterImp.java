package com.gridnine.testing.services.imp;

import com.gridnine.testing.entity.Segment;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.services.FlightFilter;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FlightFilterImp implements FlightFilter {
    private final List<Flight> flights;

    public FlightFilterImp(List<Flight> flights) {
        this.flights = new ArrayList<>(flights);
    }

    @Override
    public FlightFilter departureBeforeCurrentTime() {
        flights.removeIf(flight ->
                flight.getSegments()
                        .stream()
                        .anyMatch(segment -> segment
                                .getDepartureDate()
                                .isBefore(LocalDateTime.now())));
        return this;
    }

    @Override
    public FlightFilter segmentsWithArrivalDateBeforeDepartureDate() {
        flights.removeIf(flight ->
                flight.getSegments().stream()
                        .anyMatch(segment ->
                                segment.getArrivalDate()
                                        .isBefore(segment
                                                .getDepartureDate())));
        return this;
    }

    @Override
    public FlightFilter flightsWhereTotalTimeOnTheGroundExceedsTwoHours() {
        flights.removeIf(flight -> {
            LocalDateTime currentDeparture;
            LocalDateTime lastArrival;
            Duration durationOnTheGround = Duration.ZERO;//установка на 0
            List<Segment> segments = flight.getSegments();//получили список сегментов

            for (int i = 1; i < segments.size(); i++) {
                currentDeparture = segments.get(i).getDepartureDate();
                lastArrival = segments.get(i - 1).getArrivalDate();
                //вычислить продолжительность
                durationOnTheGround = durationOnTheGround.plus(Duration.between(currentDeparture, lastArrival).abs());
            }
            return durationOnTheGround.toHours() >= 2;//если true, то удалится
        });
        return this;
    }

    @Override
    public List<Flight> buildList() {
        return flights;
    }
}