package presentation;
import model.Client;
import model.Product;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

/**
 * this class creates the frame from which the user can select a specific page for the desired action
 */
public class FirstPage extends JFrame implements ActionListener {
    private JButton client=new JButton("client operations");
    private JButton product=new JButton("product operations");
    private JButton order=new JButton("create product order");
    public FirstPage()
    {   this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(100,100,600,500);
        this.setTitle("Choose a page");
        JPanel one=new JPanel(new GridLayout(3,1));
        one.add(client);
        one.add(product);
        one.add(order);
        client.addActionListener(this);
        product.addActionListener(this);
        order.addActionListener(this);
        this.add(one);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==client)
        {
            ClientPage page=new ClientPage();
            page.setVisible(true);
        }
        if(e.getSource()==product)
        {
            ProductPage page=new ProductPage();
            page.setVisible(true);
        }
        if(e.getSource()==order)
        {
            OrderPage page=new OrderPage();
            page.setVisible(true);
        }
    }
}
