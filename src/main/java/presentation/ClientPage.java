package presentation;

import dataAccesLayer.ClientDao;
import model.Client;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Document;
import javax.swing.text.Element;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import static presentation.Controller.viewClient;

/**
 * this class creates the frame for the client page which contains all the objects necessary for adding,editing and deleting a client
 */
public class ClientPage extends JFrame implements ActionListener {
    public JButton add=new JButton("add new client");
    public JButton edit=new JButton("edit client");
    public JButton delete=new JButton("delete client");
    public JButton view=new JButton("view all clients");
    public static JTextField nameAdd=new JTextField(20);
    public static JTextField nameOld=new JTextField(20);
    public static JTextField nameNew=new JTextField(20);
    public static JTextField nameDelete=new JTextField(20);
    public static JTable viewClients=new JTable();
    public ClientPage(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(100,100,600,600);
        this.setTitle("Client Operations");
        add.setBounds(120,120,50,50);
        add.addActionListener(this);
        delete.addActionListener(this);
        edit.addActionListener(this);
        view.addActionListener(this);
        JPanel contentPane=new JPanel();
        JPanel addClient=new JPanel();
        JLabel name=new JLabel("name");
        addClient.add(add);
        addClient.add(name);
        addClient.add(nameAdd);
        addClient.setLayout(new FlowLayout());
        JPanel editClient=new JPanel();
        edit.setBounds(140,140,50,50);
        editClient.add(edit);
        JLabel old=new JLabel("old name:");
        JLabel newN=new JLabel("new name:");
        editClient.add(old);
        editClient.add(nameOld);
        editClient.add(newN);
        editClient.add(nameNew);
        editClient.setLayout(new FlowLayout());
        JPanel deleteClient=new JPanel();
        deleteClient.add(delete);
        JLabel sterge=new JLabel("name");
        deleteClient.add(sterge);
        deleteClient.add(nameDelete);
        deleteClient.setLayout(new FlowLayout());
        JPanel viewClient=new JPanel();
        viewClient.add(view);
        JLabel post=new JLabel("press to see the clients");
        viewClient.add(post);
        viewClient.add(viewClients);
        viewClient.setLayout(new FlowLayout());
        contentPane.add(addClient);
        contentPane.add(editClient);
        contentPane.add(deleteClient);
        contentPane.add(viewClient);
        contentPane.setLayout(new GridLayout(5,3));
        this.add(contentPane);
    }
   public static String getNameToAdd()
   {
       return nameAdd.getText();
   }
    public static String getNameToDelete() {
        return nameDelete.getText();
    }
    public static String getNewName()
    {
        return nameNew.getText();
    }

    public static String getOldName() {
        return nameOld.getText();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==add)
        {
            System.out.println("ADD");
            Client client=Controller.setClient1();
            System.out.println(client.getName());
            Controller.insertClient(client);
        }
        else if(e.getSource()==delete)
        {
            System.out.println("DELETE");

            Client client=Controller.setClient2();
            Controller.deleteClient(client);
        }
        else if(e.getSource()==edit)
        {
            Client client=Controller.setClient3();
            Controller.editClient(client);
        }
        else if(e.getSource()==view)
        {
           viewClient();
        }
    }

}
