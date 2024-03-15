package dataAccesLayer;

import model.Client;
import model.Product;

import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;

/**
 * in this class the method that uses reflexion to populate the tables in the data base is made
 */
public class Populate {

    public static void populateDataBase(Object[] objects) {
        for (int i = 0; i < objects.length; i++) {
            Class myObject = objects[i].getClass();
            String className = myObject.getSimpleName();
            System.out.println(className);
                if (className.equals("Product")) {
                    System.out.println("is a product start");
                    insertP(objects, i);
                    System.out.println("is a product");

                } else if (className.equals("Client")) {
                    System.out.println("is a client start");
                    insertC(objects, i);
                    System.out.println("is a client");
                }
        }
    }
    public static DefaultTableModel getData(List<?> list) {
        // header ( String[])
        // data ( o matrice Obiecte)
        Object[][] data = new Object[20][20];
        Object[] header = new String[5];
        int row = 0;
        int col = 0;
        int hed = 0;
        if(list.size()>0) {
        for (Field field : list.get(0).getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                String colum = field.getName();
                header[hed] = colum;
                hed++;
            } catch (Exception e) {
                e.getMessage();
            }
        }
            for (int i = 0; i < list.size(); i++) {
                Class myObject = list.get(i).getClass();
                String className = myObject.getSimpleName();
                col = 0;
                for (Field field : list.get(i).getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    try {
                        Object name = field.get(list.get(i));
                        data[i][col] = name;
                        col++;
                    } catch (IllegalAccessException e) {
                        e.getMessage();
                    }
                }
                row++;
            }
        } else System.out.println("Nu sunt elemente de afisat");
            return new DefaultTableModel(data, header);
    }
    public static void insertP (Object objects[], int i)  {
          String name="";int k=0;int p=0,q=0;
        for (Field field : objects[i].getClass().getDeclaredFields()) {
            field.setAccessible(true);
            System.out.println("set true");
            try {if(k==0)
            {name = (String) field.get(objects[i]);}
                else if(k==1){
                    p= (int) field.get(objects[i]);
            } else { q=(int) field.get(objects[i]);}
                k++;
            }
            catch (IllegalAccessException e){e.getMessage();}
        }
        System.out.println(name+" "+p+" "+q);
        Product prod = new Product(name, p, q);
        ProductDao.insert(prod);
    }


    public static void insertC(Object objects[], int i)  {
        for (Field field : objects[i].getClass().getDeclaredFields()) {
            System.out.println("in");
            field.setAccessible(true);
            System.out.println("set true");
            try{String name = (String) field.get(objects[i]);
                System.out.println(name);
                Client p = new Client(name);
                ClientDao.insert(p);}
            catch (IllegalAccessException e){e.getMessage();}
        }
    }
}