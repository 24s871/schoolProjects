package org.example;
import bussinesLayer.PopulateBll;
import presentation.FirstPage;


public class Main {
    public static void main(String[] args) {
       try{
           FirstPage page=new FirstPage();
           page.setVisible(true);
       }catch (Exception e)
       {
           e.getCause();
       }
       //PopulateBll.populateTableClient();
       //PopulateBll.populateTableProduct();
    }
}