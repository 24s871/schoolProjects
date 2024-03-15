package presentation;

import bussinesLayer.ClientBll;
import bussinesLayer.ProductBll;
import dataAccesLayer.*;
import model.Client;
import model.OrderDone;
import model.Product;
import bussinesLayer.OrderBll;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * this class has the methods that are used for buttons in the interface
 */

public class Controller {

    private static String name=ClientPage.getNameToAdd();
    private static String nameDelete=ClientPage.getNameToDelete();
    private static String nameOld=ClientPage.getOldName();

    private static String nameProduct=ProductPage.getNameToAdd();
    private static String nameOldProduct=ProductPage.getOldName();
    private static String newName=ProductPage.getNewName();
    private static String newPrice=ProductPage.getNewPrice();
    private static String newQuantity=ProductPage.getNewQuantity();
    private static String nameDeleteProduct=ProductPage.getNmaeDelete();
    private static String priceAdd=ProductPage.getPriceToAdd();
    private static String cantitateAdd=ProductPage.getQuantityToAdd();
    public static Client setClient1()
    {
        Client clientAdd=new Client(null);
        clientAdd.setName(ClientPage.getNameToAdd());
        return clientAdd;
    }

    public static Client setClient2() {
        Client clientDelete=new Client(null);
        clientDelete.setName(ClientPage.getNameToDelete());
        return clientDelete;
    }

    public static Client setClient3()
    {
        Client clientEdit=new Client(null);
        clientEdit.setName(ClientPage.getOldName());
        return clientEdit;
    }

    public static Product setProduct1()
    {
        Product productAdd=new Product(null,0,0);
        productAdd.setName(ProductPage.getNameToAdd());
        int p=Integer.parseInt(ProductPage.getPriceToAdd());
        productAdd.setPrice(p);
        int q=Integer.parseInt(ProductPage.getQuantityToAdd());
        productAdd.setCantitate(q);
        return productAdd;
    }
    public static Product setProduct2()
    {
        Product productDelete=new Product(null,0,0);
        productDelete.setName(ProductPage.getNmaeDelete());
        return productDelete;
    }
    public static Product setProduct3()
    {
        Product editProduct=new Product(null,0,0);
        editProduct.setName(ProductPage.getOldName());
        return editProduct;
    }


    public static int insertClient(Client clientAdd) {

        return ClientBll.insertClient(clientAdd);
    }

    public static void deleteClient(Client clientDelete) {
         ClientBll.deleteClient(clientDelete);
    }
    public static void editClient(Client clientEdit) {
        ClientBll.editClient(clientEdit);

    }

    public static void showPane(int i)
    {
        if(i <1)
        {
            JOptionPane.showMessageDialog(null, "No Record Found","Error",
                    JOptionPane.ERROR_MESSAGE);
        }
        if(i ==1)
        {
            System.out.println(i+" Record Found");
        }
        else
        {
            System.out.println(i+" Records Found");
        }
    }

    public static void viewClient() {
        //String[] columnNames = {"name"};
        JFrame frame1 = new JFrame("Database Search Result");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setLayout(new BorderLayout());
       // DefaultTableModel model = new DefaultTableModel();
        DefaultTableModel model = Populate.getData(ClientDao.getListClient());
        //model.setColumnIdentifiers(columnNames);
        ClientPage.viewClients.setModel(model);
        ClientPage.viewClients.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        ClientPage.viewClients.setFillsViewportHeight(true);
        JScrollPane scroll = new JScrollPane(ClientPage.viewClients);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        try
        {  //int i= ClientDao.view(model);
          //showPane(i);
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
        }
        frame1.add(scroll);
        frame1.setVisible(true);
        frame1.setSize(400,300);
    }

    public static int insertProduct(Product productAdd) {

        return ProductBll.insertProduct(productAdd);
    }
    public static void deleteProduct(Product productDelete) {
        ProductBll.deleteProduct(productDelete);
    }
    public static void editProduct(Product editProduct) {
        ProductBll.editProduct(editProduct,ProductPage.getNewName(),ProductPage.getNewPrice(),ProductPage.getNewQuantity());
    }
    public static void viewProduct() {
        //String[] columnNames = {"name","price","quantity"};
        JFrame frame1 = new JFrame("Database Search Result");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setLayout(new BorderLayout());
        //DefaultTableModel model = new DefaultTableModel();
        DefaultTableModel model = Populate.getData((ProductDao.getListProducts()));
       // model.setColumnIdentifiers(columnNames);
        ProductPage.show.setModel(model);
        ProductPage.show.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        ProductPage.show.setFillsViewportHeight(true);
        JScrollPane scroll = new JScrollPane(ProductPage.show);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        try
        {  //int i= ProductDao.view(model);
         //showPane(i);
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
        }
        frame1.add(scroll);
        frame1.setVisible(true);
        frame1.setSize(400,300);
    }
    static int id=OrderDao.finId();
    public static  void doOrder(int id)
    {
        OrderBll.desiredQuantity=Integer.parseInt(OrderPage.getQuantityOrder());
        OrderDone order=new OrderDone(0,null,null,0);
        order.setId(id+1);
        String name=OrderPage.getProductName();
        String client=OrderPage.getClientName();
        String quantity= OrderPage.getQuantityOrder();
        int x=Integer.parseInt(quantity);
        order.setOrderedProduct(name);
        order.setClient(client);
        order.setDesiredQuantity(x);
        OrderBll.name=name;
        OrderBll.order=order;
        boolean work=OrderBll.addOrder();
        if(!work)
        {
            JOptionPane.showMessageDialog(null, "Under-stock Error","Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    public static void viewBill() {
        JFrame frame1 = new JFrame("Database Log Table");
        frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame1.setLayout(new BorderLayout());
        DefaultTableModel model = Populate.getData((BillDao.getListBill()));
        ProductPage.show.setModel(model);
        ProductPage.show.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        ProductPage.show.setFillsViewportHeight(true);
        JScrollPane scroll = new JScrollPane(ProductPage.show);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        try
        {  //int i= ProductDao.view(model);
            //showPane(i);
        }
        catch(Exception ex)
        {
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
        }
        frame1.add(scroll);
        frame1.setVisible(true);
        frame1.setSize(400,300);
    }
}
