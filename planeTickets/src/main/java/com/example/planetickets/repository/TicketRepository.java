package com.example.planetickets.repository;

import com.example.planetickets.model.TicketsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<TicketsModel,Integer> {
    @Query(value="SELECT  * FROM ticket WHERE  seats > 0",nativeQuery = true)
    List<TicketsModel> findAllTickets();

    @Query(value="SELECT company.name from company inner join ticket on ticket.id_companie=company.id",nativeQuery = true)
    String[] getCompany();

    @Query(value="SELECT city.departure_city from city inner join ticket on ticket.id_oras=city.id",nativeQuery = true)
    String[] getDepartureCities();

    @Query(value="SELECT city.arrival_city from city inner join ticket on ticket.id_oras=city.id",nativeQuery = true)
    String[] getArrivalCities();

    @Query(value="SELECT airport.arrival_airport from airport inner join ticket on airport.id = ticket.id_aeroport",nativeQuery = true)
    String[] getArrivalAirports();

    @Query(value="SELECT airport.departure_airport from airport inner join ticket on airport.id = ticket.id_aeroport",nativeQuery = true)
    String[] getDepartureAirports();

    @Query(value="SELECT date.arrival_date from date inner join ticket on date.id = ticket.id_data",nativeQuery = true)
    String[] getArrivalDates();

    @Query(value="SELECT date.departure_date from date inner join ticket on date.id = ticket.id_data",nativeQuery = true)
    String[] getDepartureDates();

    @Query(value="SELECT hour.arrival from hour inner join ticket on hour.id = ticket.id_ora",nativeQuery = true)
    String[] getArrivalHours();

    @Query(value="SELECT hour.departure from hour inner join ticket on hour.id = ticket.id_ora",nativeQuery = true)
    String[] getDepartureHours();

    @Query(value="SELECT class.economic from class inner join ticket on class.id=ticket.id_clasa",nativeQuery = true)
    String[] getEconomyPrices();

    @Query(value="SELECT class.first from class inner join ticket on class.id=ticket.id_clasa",nativeQuery = true)
    String[] getFirstPrices();

    @Query(value="SELECT class.second from class inner join ticket on class.id=ticket.id_clasa",nativeQuery = true)
    String[] getSecondPrices();

    @Query(value="SELECT class.bussiness from class inner join ticket on class.id=ticket.id_clasa",nativeQuery = true)
    String[] getBussinessPrices();

    @Query(value="SELECT stopover.stopover_city from stopover inner join ticket on stopover.id = ticket.id_escala",nativeQuery = true)
    String[] getStopovers();

    @Query(value="SELECT time.flight_time from time inner join ticket on time.id = ticket.id_durata",nativeQuery = true)
    String[] getFlightTimes();

    @Query(value="SELECT checkedbaggage.checked_baggage_price from checkedbaggage inner join ticket on checkedbaggage.id=ticket.id_checkedbaggage",nativeQuery = true)
    String[] getLuggagePrices();

    @Query(value="SELECT ticket.seats from ticket ",nativeQuery = true)
    String[] getSeats();
    @Query(value="SELECT ticket.id from ticket ",nativeQuery = true)
    String[] getTicketsId();

    @Query(value="select * from ticket",nativeQuery = true)
    List<TicketsModel> findAllT();
    TicketsModel   findById(int id);

}
