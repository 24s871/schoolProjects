package presentation;

import model.Client;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static presentation.Controller.viewClient;
import static presentation.Controller.viewProduct;

/**
 * this class creates a frame that has all the necessary objects for adding,editing and deleting a product
 */
public class ProductPage extends JFrame implements ActionListener {
    private JButton add=new JButton("add product");
    private JButton edit=new JButton("edit product");
    private JButton delete=new JButton("delete product");
    private JButton view=new JButton("view all products");
    private static JTextField name=new JTextField(20);
    private static JTextField pricePerUnitText=new JTextField(20);
    private static JTextField cantitateTotalText=new JTextField(20);
    private static JTextField nameEdit=new JTextField(20);
    private static JTextField pricePerUnitTextEdit=new JTextField(20);
    private static JTextField cantitateTotalTextEdit=new JTextField(20);
    private static JTextField nameDelete=new JTextField(20);
    private static JTextField nameOld=new JTextField(20);
    public static JTable show=new JTable();

    public ProductPage()
    {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(100,100,600,600);
        this.setTitle("Product Operations");
        JPanel contentPane=new JPanel();
        add.addActionListener(this);
        edit.addActionListener(this);
        delete.addActionListener(this);
        view.addActionListener(this);
        JPanel addProduct=new JPanel();
        addProduct.add(add);
        JLabel n=new JLabel("name");
        JLabel p=new JLabel("price per unit");
        JLabel c=new JLabel("quantity");
        JLabel nE=new JLabel("new name");
        JLabel pE=new JLabel("new price per unit");
        JLabel cE=new JLabel("new quantity");
        addProduct.add(n);
        addProduct.add(name);
        addProduct.add(p);
        addProduct.add(pricePerUnitText);
        addProduct.add(c);
        addProduct.add(cantitateTotalText);
        addProduct.setLayout(new FlowLayout());
        JPanel editProduct=new JPanel();
        JLabel oldName=new JLabel("product name to change");
        editProduct.add(edit);
        editProduct.add(oldName);
        editProduct.add(nameOld);
        editProduct.add(nE);
        editProduct.add(nameEdit);
        editProduct.add(pE);
        editProduct.add(pricePerUnitTextEdit);
        editProduct.add(cE);
        editProduct.add(cantitateTotalTextEdit);
        editProduct.setLayout(new FlowLayout());
        JPanel deleteProduct=new JPanel();
        deleteProduct.add(delete);
        JLabel nD=new JLabel("name");
        deleteProduct.add(nD);
        deleteProduct.add(nameDelete);
        deleteProduct.setLayout(new FlowLayout());
        JPanel viewProduct=new JPanel();
        viewProduct.add(view);
        JLabel showLabel=new JLabel("press to see the products");
        viewProduct.add(showLabel);
        viewProduct.add(show);
        viewProduct.setLayout(new FlowLayout());
        contentPane.add(addProduct);
        contentPane.add(editProduct);
        contentPane.add(deleteProduct);
        contentPane.add(viewProduct);
        contentPane.setLayout(new GridLayout(4,7));
        this.add(contentPane);
    }

    public static String getNameToAdd()
    {
        return name.getText();
    }
    public static String getPriceToAdd()
    {
        return pricePerUnitText.getText();
    }
    public static String getQuantityToAdd()
    {
        return cantitateTotalText.getText();
    }
    public static String getOldName()
    {
        return nameOld.getText();
    }
    public static String getNewName()
    {
        return nameEdit.getText();
    }
    public static String getNewPrice()
    {
        return pricePerUnitTextEdit.getText();
    }
    public static String getNewQuantity()
    {
        return cantitateTotalTextEdit.getText();
    }
    public static String getNmaeDelete()
    {
        return nameDelete.getText();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==add)
        {
            Product product=Controller.setProduct1();
            Controller.insertProduct(product);
        }
        else if(e.getSource()==delete)
        {
            Product product=Controller.setProduct2();
            Controller.deleteProduct(product);
        }
        else if(e.getSource()==edit)
        {
            Product product=Controller.setProduct3();
            Controller.editProduct(product);
        }
        else if(e.getSource()==view)
        {
            viewProduct();
        }

    }
}
