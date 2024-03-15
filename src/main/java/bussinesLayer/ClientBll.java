package bussinesLayer;

import dataAccesLayer.ClientDao;
import dataAccesLayer.OrderDao;
import model.Client;

import javax.swing.table.DefaultTableModel;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * this class implements the methods from ClientDao and check that the inserted client is correct
 */
public class ClientBll {
    private static List<Validator<Client>> validators=new ArrayList<>();

    public ClientBll() {
        validators = new ArrayList<Validator<Client>>();
    }
    public static int insertClient(Client client) {
          for (Validator<Client> v : validators) {
            v.validate(client);
        }
        return ClientDao.insert(client);
    }

    public static void deleteClient(Client client)
    {
        for (Validator<Client> v : validators) {
            v.validate(client);
        }
        OrderDao.deleteClient(client.getName());
         ClientDao.delete(client);
    }
    public static int editClient(Client client)
    {
        for (Validator<Client> v : validators) {
            v.validate(client);
        }
        return ClientDao.edit(client);
    }


}
