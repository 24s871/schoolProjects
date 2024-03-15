package bussinesLayer;

import dataAccesLayer.Populate;
import model.Client;
import model.OrderDone;
import model.Product;

import java.util.ArrayList;

/**
 * this class implements the method that populates tables
 */

public class PopulateBll {
    static ArrayList<Product> arr = new ArrayList<Product>();
    static ArrayList<Client> vec=new ArrayList<Client>();
    static Product p1=new Product("Ananas",10,100) ;
    static Product p2=new Product("Masina",2000,20);
    static Product p3=new Product("Barca",2300,35);
    static Product p4=new Product("Calculator",1200,40);
    static Product p5=new Product("Mouse",30,300);
    static Client c1=new Client("Pop Vlad");
    static Client c2=new Client("Jimi Lewis");
    static Client c3=new Client("Shane Rush");
    static Client c4=new Client("Carissa Tash");
    static Client c5=new Client("Lalia Chambers");

    public static void populateClient()
    {
        vec.add(c1);
        vec.add(c2);
        vec.add(c3);
        vec.add(c4);
        vec.add(c5);
    }
    public static void populateProduct()
    {
        arr.add(p1);
        arr.add(p2);
        arr.add(p3);
        arr.add(p4);
        arr.add(p5);
    }

    public static void populateTableClient()
    {
        populateClient();
        Populate.populateDataBase(vec.toArray());
    }
    public static void populateTableProduct()
    {
        populateProduct();
        Populate.populateDataBase(arr.toArray());
    }
}
