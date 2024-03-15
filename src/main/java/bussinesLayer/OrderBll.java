package bussinesLayer;

import dataAccesLayer.BillDao;
import dataAccesLayer.OrderDao;
import model.Bill;
import model.OrderDone;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * this class has a method that check whether the order can be made or not
 */
public class OrderBll {
    public static int desiredQuantity;
    public static String name;
    public static OrderDone order;
    static PrintWriter writer;
    private static int idBill=BillDao.findBill();
    public static  boolean addOrder()
    {
        if(desiredQuantity <= OrderDao.maxQuantityProduct(name))
        {
            int x=OrderDao.maxQuantityProduct(name)-desiredQuantity;
            String dif=Integer.toString(x);
            OrderDao.insert(order);
            OrderDao.updateProduct(name,dif);
            int price=OrderDao.getPriceProduct(name)*desiredQuantity;
            Bill bill=new Bill(idBill+1, order.getId(), price);
            idBill++;
            BillDao.insert(bill);
            return true;
        }
        else return false;
    }

    public static void printLog()
    {
        try {
            writer = new PrintWriter("logTable.txt", "UTF-8");
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        writer.print("idBll"+"  "+"idOrder"+"    price");
        writer.println();
        List<Bill> data=BillDao.getListBill();
        System.out.println("got bill data");
        writer.checkError();
        Iterator value= data.iterator();
        while(value.hasNext())
        {   writer.flush();
            writer.checkError();
            Bill bill= (Bill) value.next();
            writer.print(bill.idBill()+"        "+bill.idOrder()+"        "+bill.price());
            writer.println();
        }
         writer.close();
    }
}
