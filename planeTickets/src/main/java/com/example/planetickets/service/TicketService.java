package com.example.planetickets.service;

import com.example.planetickets.dto.InfoUser;
import com.example.planetickets.dto.Information;
import com.example.planetickets.dto.TicketInfo;
import com.example.planetickets.dto.TicketsDto;
import com.example.planetickets.model.*;
import com.example.planetickets.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TicketService {

    private final TicketRepository ticketRepository ;
    private final CompanyRepo companyRepo;
    private final AirportsRepo airportsRepo;
    private  final HourRepo hourRepo;
    private final DateRepo dateRepo;
    private final CityRepo cityRepo;
    private  final LuggageRepo luggageRepo;
    private final StopRepo stopRepo;
    private final ClassRepo classRepo;
    private final TimeRepo timeRepo;

    public TicketService(TicketRepository ticketRepository, CompanyRepo companyRepo, AirportsRepo airportsRepo, HourRepo hourRepo, DateRepo dateRepo, CityRepo cityRepo, LuggageRepo luggageRepo, StopRepo stopRepo, ClassRepo classRepo, TimeRepo timeRepo) {
        this.ticketRepository = ticketRepository;
        this.companyRepo = companyRepo;
        this.airportsRepo = airportsRepo;
        this.hourRepo = hourRepo;
        this.dateRepo = dateRepo;
        this.cityRepo = cityRepo;
        this.luggageRepo = luggageRepo;
        this.stopRepo = stopRepo;
        this.classRepo = classRepo;
        this.timeRepo = timeRepo;
    }

      public List<TicketsModel> allTickets()
      {
          return ticketRepository.findAllTickets();
      }


      public TicketsModel getTicket(int id)
      {
          return ticketRepository.findById(id);
      }

      public String[] getCompany()
      {
          return ticketRepository.getCompany();
      }

     public List<TicketsDto> findTickets(Information form)
     {
         List<TicketsDto> res=new ArrayList<>();
         String[] companies= ticketRepository.getCompany();
         String[] departureCities= ticketRepository.getDepartureCities();
         String[] arrivalCities= ticketRepository.getArrivalCities();
         String[] arrivalAirports=ticketRepository.getArrivalAirports();
         String[] departureAirports= ticketRepository.getDepartureAirports();
         String[] departureDates= ticketRepository.getDepartureDates();
         String[] arrivalDates= ticketRepository.getArrivalDates();
         String[] departureHours=ticketRepository.getDepartureHours();
         String[] arrivalHours= ticketRepository.getArrivalHours();
         String[] economicPrices= ticketRepository.getEconomyPrices();
         String[] firstPrices= ticketRepository.getFirstPrices();
         String[] secondPrices= ticketRepository.getSecondPrices();
         String[] bussinessPrices=ticketRepository.getBussinessPrices();
         String[] luggagePrices= ticketRepository.getLuggagePrices();
         String[] stopovers=ticketRepository.getStopovers();
         String[] periods= ticketRepository.getFlightTimes();
         String[] seats=ticketRepository.getSeats();
         String[] ids=ticketRepository.getTicketsId();
         for(int i=0;i<seats.length;i++)
         {   TicketsDto t=new TicketsDto();
             if(form.getPassangers().equals("")) {
                 if (Integer.parseInt(seats[i]) > 0) {
                     int finalPrice = 0;
                     t = new TicketsDto(Integer.parseInt(ids[i]), Integer.parseInt(seats[i]), companies[i], departureCities[i], arrivalCities[i], departureAirports[i], arrivalAirports[i], departureDates[i], arrivalDates[i], departureHours[i], arrivalHours[i], Integer.parseInt(economicPrices[i]), Integer.parseInt(firstPrices[i]), Integer.parseInt(secondPrices[i]), Integer.parseInt(bussinessPrices[i]), stopovers[i], Integer.parseInt(periods[i]), Integer.parseInt(luggagePrices[i]), finalPrice);
                 }
             }
             else
             {
                 int finalPrice=0,numarBagajCala=0,people=0,peoplePrice=0;
                 people=Integer.parseInt(form.getPassangers());
                 if(form.getNumberOfLuggages()!="")
                 {
                     numarBagajCala=Integer.parseInt(form.getNumberOfLuggages());
                 }
                 if(form.getClasa()!="")
                 {
                     if(form.getClasa().equals("economic"))
                     {
                         peoplePrice=Integer.parseInt(economicPrices[i]);
                     }
                     if(form.getClasa().equals("first"))
                     {
                         peoplePrice=Integer.parseInt(firstPrices[i]);
                     }
                     if(form.getClasa().equals("second"))
                     {
                         peoplePrice=Integer.parseInt(secondPrices[i]);
                     }
                     if(form.getClasa().equals("business"))
                     {
                         peoplePrice=Integer.parseInt(bussinessPrices[i]);
                     }
                 }
                 finalPrice=numarBagajCala*Integer.parseInt(luggagePrices[i])+people*peoplePrice;
                  t = new TicketsDto(Integer.parseInt(ids[i]), Integer.parseInt(seats[i]), companies[i], departureCities[i], arrivalCities[i], departureAirports[i], arrivalAirports[i], departureDates[i], arrivalDates[i], departureHours[i], arrivalHours[i], Integer.parseInt(economicPrices[i]), Integer.parseInt(firstPrices[i]), Integer.parseInt(secondPrices[i]), Integer.parseInt(bussinessPrices[i]), stopovers[i], Integer.parseInt(periods[i]), Integer.parseInt(luggagePrices[i]), finalPrice);
             }
             res.add(t);
         }
         for(int i=0;i<res.size();i++)
         {
             if(form.getDeparture()!="")
             {
                 if(!Objects.equals(res.get(i).getDepartureCity(), form.getDeparture()))
                 {
                     res.remove(i);
                 }
             }
             if(form.getArrival()!="")
             {
                 if(!Objects.equals(res.get(i).getArrivalCity(), form.getArrival()))
                 {
                     res.remove(i);
                 }
             }
             if(form.getDateDeparture()!="")
             {
                 if(!Objects.equals(res.get(i).getDepartureDate(), form.getDateDeparture()))
                 {
                     res.remove(i);
                 }
             }
             if(Objects.equals(form.getDirect(), "da"))
             {
                 if(!Objects.equals(res.get(i).getStopoverCity(), "nu are"))
                 {
                     res.remove(i);
                 }
             }
             if(Objects.equals(form.getStopover(), "da"))
             {
                 if(Objects.equals(res.get(i).getStopoverCity(), "nu are"))
                 {
                     res.remove(i);
                 }
             }
             if(form.getDuration()!="")
             {
                 int max=Integer.parseInt(form.getDuration());
                 if(res.get(i).getFlightTime()>max)
                 {
                     res.remove(i);
                 }
             }
             if(form.getAirline()!="")
             {
                 if(!Objects.equals(res.get(i).getCompany(), form.getAirline()))
                 {
                     res.remove(i);
                 }
             }
             if(form.getTransit()!="")
             {
                 if(!Objects.equals(res.get(i).getStopoverCity(), form.getTransit()))
                 {
                     res.remove(i);
                 }
             }
         }
         return res;
     }

  public List<TicketsDto> findAllTicketsList()
  {
      List<TicketsDto> res=new ArrayList<>();
      String[] companies= ticketRepository.getCompany();
      String[] departureCities= ticketRepository.getDepartureCities();
      String[] arrivalCities= ticketRepository.getArrivalCities();
      String[] arrivalAirports=ticketRepository.getArrivalAirports();
      String[] departureAirports= ticketRepository.getDepartureAirports();
      String[] departureDates= ticketRepository.getDepartureDates();
      String[] arrivalDates= ticketRepository.getArrivalDates();
      String[] departureHours=ticketRepository.getDepartureHours();
      String[] arrivalHours= ticketRepository.getArrivalHours();
      String[] economicPrices= ticketRepository.getEconomyPrices();
      String[] firstPrices= ticketRepository.getFirstPrices();
      String[] secondPrices= ticketRepository.getSecondPrices();
      String[] bussinessPrices=ticketRepository.getBussinessPrices();
      String[] luggagePrices= ticketRepository.getLuggagePrices();
      String[] stopovers=ticketRepository.getStopovers();
      String[] periods= ticketRepository.getFlightTimes();
      String[] seats=ticketRepository.getSeats();
      String[] ids=ticketRepository.getTicketsId();
      System.out.println(Arrays.toString(ids));
      for(int i=0;i<ids.length;i++)
      {
          System.out.println(ids[i]+ " "+seats[i]+" "+economicPrices[i]+" "+firstPrices[i]+" "+secondPrices[i]+" "+bussinessPrices[i]+" "+periods[i]+" "+luggagePrices[i]);
          TicketsDto t=new TicketsDto(Integer.parseInt(ids[i]), Integer.parseInt(seats[i]), companies[i], departureCities[i], arrivalCities[i], departureAirports[i], arrivalAirports[i], departureDates[i], arrivalDates[i], departureHours[i], arrivalHours[i], Integer.parseInt(economicPrices[i]), Integer.parseInt(firstPrices[i]), Integer.parseInt(secondPrices[i]), Integer.parseInt(bussinessPrices[i]), stopovers[i], Integer.parseInt(periods[i]), Integer.parseInt(luggagePrices[i]),0);
          res.add(t);
      }
      return res;
  }
    public void removeTicket(TicketInfo ticketInfo)
    {
        //usersRepository.deleteUsersByFirstName(infoUser.getId());
        ticketRepository.deleteById(ticketInfo.getId());
    }

    public void addTicket(TicketInfo ticketInfo) {
        if (ticketInfo.getSeats() > 0) {
            TicketsModel ticketsModel = new TicketsModel();
            ticketsModel.setSeats(ticketInfo.getSeats());
            ticketsModel.setId(ticketInfo.getId());

            List<CompaniesModel> companiesModelList = companyRepo.findAll();
            boolean isCompany = false;
            for (CompaniesModel companiesModel : companiesModelList) {
                if (Objects.equals(companiesModel.getName(), ticketInfo.getCompany())) {
                    ticketsModel.setId_companie(companiesModel.getId());
                    isCompany = true;
                }
            }
            if (!isCompany) {
                CompaniesModel c = new CompaniesModel();
                c.setName(ticketInfo.getCompany());
                companyRepo.save(c);
                companiesModelList = companyRepo.findAll();
                for (CompaniesModel companiesModel : companiesModelList) {
                    if (Objects.equals(companiesModel.getName(), ticketInfo.getCompany())) {
                        ticketsModel.setId_companie(companiesModel.getId());
                    }
                }
            }

            boolean isAirport = false;
            List<AirportsModel> airportsModelList = airportsRepo.findAll();
            for (AirportsModel airportsModel : airportsModelList) {
                if (Objects.equals(airportsModel.getDepartureAirport(), ticketInfo.getDepartureAirport()) && Objects.equals(airportsModel.getArrivalAirport(), ticketInfo.getArrivalAirport())) {
                    ticketsModel.setId_aeroport(airportsModel.getId());
                    isAirport = true;
                }
            }
            if (!isAirport) {
                AirportsModel c = new AirportsModel();
                c.setArrivalAirport(ticketInfo.getArrivalAirport());
                c.setDepartureAirport(ticketInfo.getDepartureAirport());
                airportsRepo.save(c);
                airportsModelList = airportsRepo.findAll();
                for (AirportsModel airportsModel : airportsModelList) {
                    if (Objects.equals(airportsModel.getDepartureAirport(), ticketInfo.getDepartureAirport()) && Objects.equals(airportsModel.getArrivalAirport(), ticketInfo.getArrivalAirport())) {
                        ticketsModel.setId_aeroport(airportsModel.getId());
                    }
                }
            }

            boolean isCity = false;
            List<CitiesModel> citiesModelList = cityRepo.findAll();
            for (CitiesModel c : citiesModelList) {
                if (Objects.equals(c.getArrivalCity(), ticketInfo.getArrivalCity()) && Objects.equals(c.getDepartureCity(), ticketInfo.getDepartureCity())) {
                    ticketsModel.setId_oras(c.getId());
                    isCity = true;
                }
            }
            if (!isCity) {
                CitiesModel a = new CitiesModel();
                a.setDepartureCity(ticketInfo.getDepartureCity());
                a.setArrivalCity(ticketInfo.getArrivalCity());
                cityRepo.save(a);
                citiesModelList = cityRepo.findAll();
                for (CitiesModel c : citiesModelList) {
                    if (Objects.equals(c.getArrivalCity(), ticketInfo.getArrivalCity()) && Objects.equals(c.getDepartureCity(), ticketInfo.getDepartureCity())) {
                        ticketsModel.setId_oras(c.getId());
                    }
                }
            }

            boolean isHour = false;
            List<HoursModel> hoursModelList = hourRepo.findAll();
            for (HoursModel c : hoursModelList) {
                if (Objects.equals(c.getArrival(), ticketInfo.getArrivalHour()) && Objects.equals(c.getDeparture(), ticketInfo.getDepartureHour())) {
                    ticketsModel.setId_ora(c.getId());
                    isHour = true;
                }
            }
            if (!isHour) {
                HoursModel a = new HoursModel();
                a.setArrival(ticketInfo.getArrivalHour());
                a.setDeparture(ticketInfo.getDepartureHour());
                hourRepo.save(a);
                hoursModelList = hourRepo.findAll();
                for (HoursModel c : hoursModelList) {
                    if (Objects.equals(c.getArrival(), ticketInfo.getArrivalHour()) && Objects.equals(c.getDeparture(), ticketInfo.getDepartureHour())) {
                        ticketsModel.setId_ora(c.getId());
                    }
                }
            }

            boolean isData = false;
            List<DatesModel> datesModelList = dateRepo.findAll();
            for (DatesModel c : datesModelList) {
                if (Objects.equals(c.getDepartureDate(), ticketInfo.getDepartureDate()) && Objects.equals(c.getArrivalDate(), ticketInfo.getArrivalDate())) {
                    ticketsModel.setId_data(c.getId());
                    isData = true;
                }
            }
            if (!isData) {
                DatesModel d = new DatesModel();
                d.setArrivalDate(ticketInfo.getArrivalDate());
                d.setDepartureDate(ticketInfo.getDepartureDate());
                dateRepo.save(d);
                datesModelList = dateRepo.findAll();
                for (DatesModel c : datesModelList) {
                    if (Objects.equals(c.getDepartureDate(), ticketInfo.getDepartureDate()) && Objects.equals(c.getArrivalDate(), ticketInfo.getArrivalDate())) {
                        ticketsModel.setId_data(c.getId());
                    }
                }
            }

            boolean isClasa = false;
            List<ClassesModel> classesModelList = classRepo.findAll();
            for (ClassesModel c : classesModelList) {
                if (Objects.equals(c.getEconomic(), ticketInfo.getEconomicPrice()) && Objects.equals(c.getFirst(), ticketInfo.getFirstPrice()) && Objects.equals(c.getSecond(), ticketInfo.getSecondPrice()) && Objects.equals(c.getBussiness(), ticketInfo.getBussinessPrice())) {
                    ticketsModel.setId_clasa(c.getId());
                    isClasa = true;
                }
            }
            if (!isClasa) {
                ClassesModel a = new ClassesModel();
                a.setFirst(ticketInfo.getFirstPrice());
                a.setBussiness(ticketInfo.getBussinessPrice());
                a.setEconomic(ticketInfo.getEconomicPrice());
                a.setSecond(ticketInfo.getSecondPrice());
                classRepo.save(a);
                classesModelList = classRepo.findAll();
                for (ClassesModel c : classesModelList) {
                    if (Objects.equals(c.getEconomic(), ticketInfo.getEconomicPrice()) && Objects.equals(c.getFirst(), ticketInfo.getFirstPrice()) && Objects.equals(c.getSecond(), ticketInfo.getSecondPrice()) && Objects.equals(c.getBussiness(), ticketInfo.getBussinessPrice())) {
                        ticketsModel.setId_clasa(c.getId());
                    }
                }
            }

            boolean isBaggage = false;
            List<BaggageModel> baggageModelList = luggageRepo.findAll();
            for (BaggageModel c : baggageModelList) {
                if (Objects.equals(c.getCheckedBaggagePrice(), ticketInfo.getCheckBaggagePrice())) {
                    ticketsModel.setId_checkedbaggage(c.getId());
                    isBaggage = true;
                }
            }
            if (!isBaggage) {
                BaggageModel b = new BaggageModel();
                b.setCheckedBaggagePrice(ticketInfo.getCheckBaggagePrice());
                luggageRepo.save(b);
                baggageModelList = luggageRepo.findAll();
                for (BaggageModel c : baggageModelList) {
                    if (Objects.equals(c.getCheckedBaggagePrice(), ticketInfo.getCheckBaggagePrice())) {
                        ticketsModel.setId_checkedbaggage(c.getId());
                    }
                }
            }

            boolean isStop = false;
            List<StopoverModel> stopoverModelList = stopRepo.findAll();
            for (StopoverModel c : stopoverModelList) {
                if (Objects.equals(c.getStopoverCity(), ticketInfo.getStopoverCity())) {
                    ticketsModel.setId_escala(c.getId());
                    isStop = true;
                }
            }
            if (!isStop) {
                StopoverModel s = new StopoverModel();
                s.setStopoverCity(ticketInfo.getStopoverCity());
                stopRepo.save(s);
                stopoverModelList = stopRepo.findAll();
                for (StopoverModel c : stopoverModelList) {
                    if (Objects.equals(c.getStopoverCity(), ticketInfo.getStopoverCity())) {
                        ticketsModel.setId_escala(c.getId());
                    }
                }
            }

            boolean isTime = false;
            List<TimeModel> timeModelList = timeRepo.findAll();
            for (TimeModel t : timeModelList) {
                if (Objects.equals(t.getFlightTime(), ticketInfo.getFlightTime())) {
                    ticketsModel.setId_durata(t.getId());
                    isTime = true;
                }
            }
            if (!isTime) {
                TimeModel d = new TimeModel();
                d.setFlightTime(ticketInfo.getFlightTime());
                timeRepo.save(d);
                timeModelList = timeRepo.findAll();
                for (TimeModel t : timeModelList) {
                    if (Objects.equals(t.getFlightTime(), ticketInfo.getFlightTime())) {
                        ticketsModel.setId_durata(t.getId());
                    }
                }
            }
            ticketRepository.save(ticketsModel);

        }
    }

}
