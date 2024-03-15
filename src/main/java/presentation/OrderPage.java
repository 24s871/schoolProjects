package presentation;
import bussinesLayer.OrderBll;
import dataAccesLayer.ClientDao;
import dataAccesLayer.ProductDao;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * this class creates the frame that has all the necessary objects for creating an order
 */

public class OrderPage extends JFrame implements ActionListener {
    private JButton makeOrder=new JButton("make order");
     private JLabel product=new JLabel("select product:");
     private JLabel client=new JLabel("select client:");
     private JLabel quantity=new JLabel("select quantity");
    private static String[] products =  ProductDao.getProducts();
    private static String[] clients = ClientDao.getClients();
    private static JComboBox selectProduct=new JComboBox(products);
    private static JComboBox selectClient=new JComboBox<>(clients);
     private static JTextField selectQuantity=new JTextField(10);
   private JButton view=new JButton("show bill table");
    public  OrderPage()
    {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(100,100,600,600);
        this.setTitle("Order Operations");
        JPanel contentPane=new JPanel();
        makeOrder.addActionListener(this);
        view.addActionListener(this);
        JPanel action=new JPanel();
        action.add(makeOrder);
        action.add(product);
        action.add(selectProduct);
        action.add(client);
        action.add(selectClient);
        action.add(quantity);
        action.add(selectQuantity);
        JPanel bill=new JPanel();
        bill.add(view);
        bill.setLayout(new FlowLayout());
        action.setLayout(new FlowLayout());
        contentPane.add(action);
        contentPane.add(bill);
        contentPane.setLayout(new GridLayout(2,5));
        this.add(contentPane);
    }

    public static String getQuantityOrder()
    {
        return selectQuantity.getText();
    }
    public static String getProductName()
    {
        return (String) selectProduct.getSelectedItem();
    }
    public static String getClientName()
    {
        return (String) selectClient.getSelectedItem();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==makeOrder)
        {Controller.doOrder(Controller.id);
        Controller.id++;
        }
        else if(e.getSource()==view)
        {   Controller.viewBill();
            OrderBll.printLog();
        }
    }
}
