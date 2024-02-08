package com.example.planetickets.service;

import com.example.planetickets.dto.SalesDto;
import com.example.planetickets.model.SalesModel;
import com.example.planetickets.model.UsersModel;
import com.example.planetickets.repository.SalesRepository;

import org.springframework.stereotype.Service;


@Service
public class SalesSevice {
    private final SalesRepository salesRepository;

    public SalesSevice(SalesRepository salesRepository) {
        this.salesRepository = salesRepository;
    }

    public SalesModel registerSale(int clientId,int ticketId,int total,
    String numar_card,
    String cvc,
    String expira,
    String nume)
    {
        if( clientId>0 && ticketId>=0)
        {
            SalesModel salesModel=new SalesModel();
           salesModel.setCvc(cvc);
           salesModel.setExpira(expira);
           salesModel.setNume(nume);
           salesModel.setTotal(total);
           salesModel.setClientId(clientId);
           salesModel.setNumar_card(numar_card);
           salesModel.setTicketId(ticketId);
            return salesRepository.save(salesModel);
        }
        else
        {
            return null;
        }
    }

    public SalesModel toModel(SalesDto salesDto)
    {
        SalesModel salesModel=new SalesModel();
        salesModel.setTicketId(Integer.parseInt(salesDto.getTicketId()));
        salesModel.setClientId(Integer.parseInt(salesDto.getClientId()));
        salesModel.setNume(salesDto.getNume());
        salesModel.setCvc(salesDto.getCvc());
        salesModel.setExpira(salesDto.getExpira());
        salesModel.setTotal(Integer.parseInt(salesDto.getTotal()));
        salesModel.setNumar_card(salesDto.getNumar_card());
        return salesModel;
    }
}
