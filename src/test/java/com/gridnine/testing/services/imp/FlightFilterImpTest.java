package com.gridnine.testing.services.imp;

import com.gridnine.testing.entity.Segment;
import com.gridnine.testing.model.Flight;
import com.gridnine.testing.services.FlightFilter;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class FlightFilterImpTest {
    LocalDateTime threeDaysFromNow = LocalDateTime.now().plusDays(3);
    LocalDateTime normalFlightWithTwoHourDuration = threeDaysFromNow.plusHours(2);
    LocalDateTime flightDepartingInThePast = threeDaysFromNow.minusDays(6);
    LocalDateTime flightThatDepartsBeforeItArrives = threeDaysFromNow.minusHours(6);
    LocalDateTime flightWithMoreThanTwoHoursGroundTime_ar = threeDaysFromNow.plusHours(5);
    LocalDateTime flightWithMoreThanTwoHoursGroundTime_dep = threeDaysFromNow.plusHours(6);
    LocalDateTime flightWithNormalDurationGroundTime = threeDaysFromNow.plusHours(3);

    @Test
    public void shouldReturnTrue_testDepartureBeforeCurrentTime_IsEmpty() {
        Segment segment = new Segment(flightDepartingInThePast, threeDaysFromNow);
        List<Segment> segmentList = new ArrayList<>();
        segmentList.add(0, segment);
        Flight flight = new Flight(segmentList);
        List<Flight> flightList = new ArrayList<>();
        flightList.add(flight);

        FlightFilter flightFilter = new FlightFilterImp(flightList);
        List<Flight> result = flightFilter.departureBeforeCurrentTime().buildList();

        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldReturnFalse_testDepartureBeforeCurrentTime_IsNotEmpty() {
        Segment segment = new Segment(threeDaysFromNow, normalFlightWithTwoHourDuration);
        List<Segment> segmentList = new ArrayList<>();
        segmentList.add(0, segment);
        Flight flight = new Flight(segmentList);
        List<Flight> flightList = new ArrayList<>();
        flightList.add(flight);

        FlightFilter flightFilter = new FlightFilterImp(flightList);
        List<Flight> result = flightFilter.departureBeforeCurrentTime().buildList();

        assertFalse(result.isEmpty());
    }

    @Test
    public void shouldReturnTrue_segmentsWithArrivalDateBeforeDepartureDate_IsEmpty() {
        Segment segment = new Segment(threeDaysFromNow, flightThatDepartsBeforeItArrives);
        List<Segment> segmentList = new ArrayList<>();
        segmentList.add(0, segment);
        Flight flight = new Flight(segmentList);
        List<Flight> flightList = new ArrayList<>();
        flightList.add(flight);

        FlightFilter flightFilter = new FlightFilterImp(flightList);
        List<Flight> result = flightFilter.segmentsWithArrivalDateBeforeDepartureDate().buildList();

        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldReturnFalse_segmentsWithArrivalDateBeforeDepartureDate_IsEmpty() {
        Segment segment = new Segment(threeDaysFromNow, normalFlightWithTwoHourDuration);
        List<Segment> segmentList = new ArrayList<>();
        segmentList.add(0, segment);
        Flight flight = new Flight(segmentList);
        List<Flight> flightList = new ArrayList<>();
        flightList.add(flight);

        FlightFilter flightFilter = new FlightFilterImp(flightList);
        List<Flight> result = flightFilter.segmentsWithArrivalDateBeforeDepartureDate().buildList();

        assertFalse(result.isEmpty());
    }

    @Test
    public void shouldReturnTrue_flightsWhereTotalTimeOnTheGroundExceedsTwoHours_IsEmpty() {
        Segment segment1 = new Segment(threeDaysFromNow, normalFlightWithTwoHourDuration);
        Segment segment2 = new Segment(flightWithMoreThanTwoHoursGroundTime_ar, flightWithMoreThanTwoHoursGroundTime_dep);
        List<Segment> segmentList = new ArrayList<>();
        segmentList.add(0, segment1);
        segmentList.add(1, segment2);
        Flight flight = new Flight(segmentList);
        List<Flight> flightList = new ArrayList<>();
        flightList.add(flight);

        FlightFilter flightFilter = new FlightFilterImp(flightList);
        List<Flight> result = flightFilter.flightsWhereTotalTimeOnTheGroundExceedsTwoHours().buildList();

        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldReturnFalse_flightsWhereTotalTimeOnTheGroundExceedsTwoHours_IsEmpty() {
        Segment segment3 = new Segment(threeDaysFromNow, normalFlightWithTwoHourDuration);
        Segment segment4 = new Segment(flightWithNormalDurationGroundTime, flightWithMoreThanTwoHoursGroundTime_dep);
        List<Segment> segmentList = new ArrayList<>();
        segmentList.add(0, segment3);
        segmentList.add(1, segment4);
        Flight flight = new Flight(segmentList);
        List<Flight> flightList = new ArrayList<>();
        flightList.add(flight);

        FlightFilter flightFilter = new FlightFilterImp(flightList);
        List<Flight> result = flightFilter.flightsWhereTotalTimeOnTheGroundExceedsTwoHours().buildList();

        assertFalse(result.isEmpty());
    }
}