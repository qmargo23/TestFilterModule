package com.gridnine.testing.services;

import com.gridnine.testing.model.Flight;

import java.util.List;

public interface FlightFilter {

    //1//найти и исключить вылет до текущего момента времени
    FlightFilter departureBeforeCurrentTime();

    //2//найти и исключить сегменты с датой прилёта раньше даты вылета
    FlightFilter segmentsWithArrivalDateBeforeDepartureDate();

    //2//найти и исключить перелеты где общее время проведённое на земле превышает два часа
    FlightFilter flightsWhereTotalTimeOnTheGroundExceedsTwoHours();

    //возвратить список полетов
    List<Flight> buildList();
}
