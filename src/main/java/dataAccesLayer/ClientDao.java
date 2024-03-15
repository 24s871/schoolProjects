package dataAccesLayer;

import model.Client;
import presentation.ClientPage;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * this class is used to do insert,edit,delete and view for the Client table in the data base
 * it uses sql syntax to update tha data base
 */

public class ClientDao {
    protected static final Logger LOGGER = Logger.getLogger(ClientDao.class.getName());
    private static final String insertStatementString = "INSERT INTO Client (name)"
            + " VALUES (?)";
    private final static String deleteStatementString = "DELETE FROM Client WHERE name = ?";
    public final static String viewStatementString="select * from Client";
    private final static String updateStatementString="UPDATE Client SET name=? WHERE name=?";


    public static int insert(Client client) {
        Connection dbConnection = ConnectDataBase.getConnection();

        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, client.getName());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDao:insert " + e.getMessage());
        } finally {
            ConnectDataBase.close(insertStatement);
            ConnectDataBase.close(dbConnection);
        }
        return insertedId;
    }
    public static void delete(Client client) {
        Connection dbConnection = ConnectDataBase.getConnection();
        PreparedStatement deleteStatement = null;
        ResultSet rs = null;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString);
            deleteStatement.setString(1, client.getName());
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDao:delete " + e.getMessage());
        } finally {
            ConnectDataBase.close(deleteStatement);
            ConnectDataBase.close(dbConnection);
            ConnectDataBase.close(rs);
        }
    }

    public static int edit(Client client) {
        Connection dbConnection = ConnectDataBase.getConnection();
        PreparedStatement editStatement = null;
        int insertedId = -1;
        try {
            editStatement = dbConnection.prepareStatement(updateStatementString, Statement.RETURN_GENERATED_KEYS);
            editStatement.setString(1,ClientPage.getNewName() );
            editStatement.setString(2,client.getName() );
            editStatement.executeUpdate();

            ResultSet rs = editStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDao:edit " + e.getMessage());
        } finally {
            ConnectDataBase.close(editStatement);
            ConnectDataBase.close(dbConnection);
        }
        return insertedId;
    }

    public static int view( DefaultTableModel model) {
        Connection dbConnection = ConnectDataBase.getConnection();
        PreparedStatement viewStatement = null;
        int i=0; String name= "";
        try {
            viewStatement = dbConnection.prepareStatement(viewStatementString);
            ResultSet rs = viewStatement.executeQuery();
            while (rs.next()) {
                name = rs.getString("name");
                model.addRow(new Object[]{ name});
                i++;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDao:view " + e.getMessage());
        } finally {
            ConnectDataBase.close(viewStatement);
            ConnectDataBase.close(dbConnection);
        }
        return i;
    }

    public static String[] getClients( ) {
        Connection dbConnection = ConnectDataBase.getConnection();
        PreparedStatement getStatement = null;
        String[] dataArray = new String[20];
        try {
            getStatement = dbConnection.prepareStatement(viewStatementString);
            ResultSet rs = getStatement.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int numColumns = metaData.getColumnCount();
            int row = 0;
            while (rs.next()) {
                  String name=rs.getString("name");
                  Client a=new Client(name);
                  dataArray[row]=a.getName();
                row++;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDao:get " + e.getMessage());
        } finally {
            ConnectDataBase.close(getStatement);
            ConnectDataBase.close(dbConnection);
        }
        return  dataArray;
    }

    public static List<Client> getListClient()
    {
        Connection dbConnection = ConnectDataBase.getConnection();
        PreparedStatement getStatement = null;
        List<Client> dataArray = new LinkedList<Client>();
        try {
            getStatement = dbConnection.prepareStatement(viewStatementString);
            ResultSet rs = getStatement.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int numColumns = metaData.getColumnCount();
            int row = 0;
            while (rs.next()) {
                String name=rs.getString("name");
                Client a=new Client(name);
                  dataArray.add(a);
                row++;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDao:get " + e.getMessage());
        } finally {
            ConnectDataBase.close(getStatement);
            ConnectDataBase.close(dbConnection);
        }
        return  dataArray;
    }

}
