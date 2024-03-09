package com.gridnine.testing;

import com.gridnine.testing.model.Flight;
import com.gridnine.testing.services.FlightFilter;
import com.gridnine.testing.services.imp.FlightFilterImp;
import com.gridnine.testing.util.FlightBuilder;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        String text = "Список всех полетов";
        List<Flight> flightsList = FlightBuilder.createFlights();
        printResults(text, flightsList);

        String text1 = "Исключены вылеты до текущего момента времени";
        FlightFilter filter1 = new FlightFilterImp(flightsList);
        List<Flight> res1 = filter1.departureBeforeCurrentTime().buildList();
        printResults(text1, res1);

        String text2 = "Исключены вылеты сегменты с датой прилёта раньше даты вылета";
        FlightFilter filter2 = new FlightFilterImp(flightsList);
        List<Flight> res2 = filter2.segmentsWithArrivalDateBeforeDepartureDate().buildList();
        printResults(text2, res2);

        String text3 = "Исключены  перелеты где общее время проведённое на земле превышает два часа";
        FlightFilter filter3 = new FlightFilterImp(flightsList);
        List<Flight> res3 = filter3.flightsWhereTotalTimeOnTheGroundExceedsTwoHours().buildList();
        printResults(text3, res3);

    }
    public static void printResults(String text, List<Flight> flights) {
        System.out.println(text + ":");// вывод на консоль - заголовок
        flights.forEach(System.out::println);// вывод на консоль - результат фильтрации
        System.out.println();
    }
}
