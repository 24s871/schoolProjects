package com.example.planetickets.controller;

import com.example.planetickets.dto.*;
import com.example.planetickets.model.CompaniesModel;
import com.example.planetickets.model.SalesModel;
import com.example.planetickets.model.TicketsModel;
import com.example.planetickets.model.UsersModel;
import com.example.planetickets.service.SalesSevice;
import com.example.planetickets.service.TicketService;
import com.example.planetickets.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UsersController {

    @Autowired
    private UsersService usersService;
    private TicketService ticketService;
    private SalesSevice salesSevice;

    public UsersController (UsersService usersService,TicketService ticketService,SalesSevice salesSevice)
    {
        this.usersService=usersService;
        this.ticketService=ticketService;
        this.salesSevice=salesSevice;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model)
    {
        model.addAttribute("registerRequest",new UsersModel());
        return "register_page";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model)
    {
        model.addAttribute("loginRequest",new UsersModel());
        return "login_page";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UsersModel usersModel)
    {
        System.out.println("Register request:"+usersModel);
        UsersModel registeredUser=usersService.registerUser(usersModel.getName(),usersModel.getPassword(),usersModel.getEmail());
        return registeredUser == null ? "error_page" : "redirect:/login";
    }

    @GetMapping("/tickets")
    public String getTicketsPage(Model model)
    {
        model.addAttribute("information",new Information());
        return "tickets_page";
    }

    @PostMapping("/tickets")
    public String ticketsSubmit(Model model,@ModelAttribute Information form)
    {
        model.addAttribute("information",form);
        System.out.println("form object is:"+form.getDeparture()+" "+form.getArrival()+" "+form.getAirline());
        List<TicketsDto> tickets=ticketService.findTickets(form);
        model.addAttribute("tickets",tickets);
        return "tickets_page";
    }

    @GetMapping("/payment")
    public String payment(Model model)
    {
        model.addAttribute("sale",new SalesDto());
        return "payment";
    }

    @PostMapping("/payment")
    public String pay(Model model,@ModelAttribute SalesDto salesDto)
    {
        model.addAttribute("sale",salesDto);
        SalesModel salesModel=salesSevice.registerSale(Integer.parseInt(salesDto.getClientId()),Integer.parseInt(salesDto.getTicketId()),Integer.parseInt(salesDto.getTotal()),salesDto.getNumar_card(),salesDto.getCvc(), salesDto.getExpira(), salesDto.getNume());
        return "payment";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UsersModel usersModel, Model model)
    {
        System.out.println("Login request:"+usersModel);
        UsersModel connected=usersService.authenticate(usersModel.getName(),usersModel.getPassword());
        if(connected != null)
        {   if(connected.getRole().equals("CLIENT")) {
            model.addAttribute("userLogin", connected.getName());
            model.addAttribute("id", connected.getId());
            return getTicketsPage(model);
            }
            else
        {
            model.addAttribute("userLogin", connected.getName());
            return "admin_page";
        }

        }
        else return "error_page";
    }

    @GetMapping("/admin_users")
    public String users(Model model){
        List<UsersModel> users = usersService.findAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("k",new InfoUser());
        return "admin_users";
    }

    @GetMapping("/admin_tickets")
    public String tickets(Model model){
        List<TicketsDto> tickets=ticketService.findAllTicketsList();
        model.addAttribute("tickets", tickets);
        model.addAttribute("info",new TicketInfo());
        return "admin_tickets";
    }

    @PostMapping("/admin_users")
    public String usersDo(Model model, @ModelAttribute InfoUser infoUser){
        model.addAttribute("k",infoUser);
        if(infoUser.getAction().equals("edit"))
        {
            usersService.editUser(infoUser);
        }
        else usersService.removeUser(infoUser);
        List<UsersModel> users = usersService.findAllUsers();
        model.addAttribute("users", users);
        return "admin_users";
    }

    @PostMapping("/admin_tickets")
    public String ticketsDo(Model model,@ModelAttribute TicketInfo ticketInfo){
        model.addAttribute("info",ticketInfo);
        if(ticketInfo.getAction().equals("add"))
        {
            ticketService.addTicket(ticketInfo);
        }
        else ticketService.removeTicket(ticketInfo);
        List<TicketsDto> tickets=ticketService.findAllTicketsList();
        model.addAttribute("tickets", tickets);
        return "admin_tickets";
    }

}
