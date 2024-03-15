package dataAccesLayer;


import model.OrderDone;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * this class uses sql syntax to update the Order table in the data base according to the homework requirements
 */
public class OrderDao {
    protected static final Logger LOGGER = Logger.getLogger(ClientDao.class.getName());
    private static final String insertStatementString = "INSERT INTO OrderDone (id,clientWhoOrdered,orderedProduct,desiredQuantity)"
            + " VALUES (?,?,?,?)";
    private static final String getQuantityString = "SELECT * FROM Product WHERE name=?";
    private static final String getPrice = "SELECT * FROM Product WHERE name=?";
    private static final String updateQuantityString = "UPDATE Product SET cantitateTotal=? WHERE name=?";
    private static final String findMaxId = "SELECT * FROM OrderDone";
    private static  final String deleteThatClient="DELETE  FROM OrderDone where clientWhoOrdered=?";
    private static final String deleteThatProduct="DELETE  FROM OrderDone where orderedProduct=?";
    public static int maxQuantityProduct(String name) {
        Connection dbConnection = ConnectDataBase.getConnection();
        PreparedStatement getQuantityStatement = null;
        int maxQuantity = -1;
        try {
            getQuantityStatement = dbConnection.prepareStatement(getQuantityString, Statement.RETURN_GENERATED_KEYS);
            getQuantityStatement.setString(1, name);
            ResultSet rs = getQuantityStatement.executeQuery();
            if (rs.next()) {
                maxQuantity = rs.getInt(3);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDao:getMaxQuantity " + e.getMessage());
        } finally {
            ConnectDataBase.close(getQuantityStatement);
            ConnectDataBase.close(dbConnection);
        }
        System.out.println(maxQuantity);
        return maxQuantity;
    }

    public static int getPriceProduct(String name)
    {
        Connection dbConnection = ConnectDataBase.getConnection();
        PreparedStatement getQuantityStatement = null;
        int price = -1;
        try {
            getQuantityStatement = dbConnection.prepareStatement(getPrice, Statement.RETURN_GENERATED_KEYS);
            getQuantityStatement.setString(1, name);
            ResultSet rs = getQuantityStatement.executeQuery();
            if (rs.next()) {
                price = rs.getInt(2);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDao:getPriceProduct " + e.getMessage());
        } finally {
            ConnectDataBase.close(getQuantityStatement);
            ConnectDataBase.close(dbConnection);
        }

        return price;
    }

    public static int insert(OrderDone order) {
        Connection dbConnection = ConnectDataBase.getConnection();
        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, Integer.toString(order.getId()));
            insertStatement.setString(2, order.getClient());
            insertStatement.setString(3, order.getOrderedProduct());
            insertStatement.setString(4, order.getDesiredQuantity());
            insertStatement.executeUpdate();
            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);

            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDao:insert " + e.getMessage());
        } finally {
            ConnectDataBase.close(insertStatement);
            ConnectDataBase.close(dbConnection);
        }
        return insertedId;
    }

    public static int updateProduct(String Name, String newQuantity) {
        Connection dbConnection = ConnectDataBase.getConnection();
        PreparedStatement editStatement = null;
        int insertedId = -1;
        try {
            editStatement = dbConnection.prepareStatement(updateQuantityString, Statement.RETURN_GENERATED_KEYS);
            editStatement.setString(1, newQuantity);
            editStatement.setString(2, Name);
            editStatement.executeUpdate();

            ResultSet rs = editStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDao:edit " + e.getMessage());
        } finally {
            ConnectDataBase.close(editStatement);
            ConnectDataBase.close(dbConnection);
        }
        return insertedId;
    }
    public static int finId( ) {
        Connection dbConnection = ConnectDataBase.getConnection();
        PreparedStatement viewStatement = null;
        int i=-1;
        try {
            viewStatement = dbConnection.prepareStatement(findMaxId);
            ResultSet rs = viewStatement.executeQuery();
            while (rs.next()) {
                i=rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDao:view " + e.getMessage());
        } finally {
            ConnectDataBase.close(viewStatement);
            ConnectDataBase.close(dbConnection);
        }
        return i;
    }

    public static void deletePrduct(String name) {
        Connection dbConnection = ConnectDataBase.getConnection();
        PreparedStatement deleteStatement = null;
        ResultSet rs = null;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteThatProduct);
            deleteStatement.setString(1, name);
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDao:delete product" + e.getMessage());
        } finally {
            ConnectDataBase.close(deleteStatement);
            ConnectDataBase.close(dbConnection);
            ConnectDataBase.close(rs);
        }
    }
    public static void deleteClient(String name) {
        Connection dbConnection = ConnectDataBase.getConnection();
        PreparedStatement deleteStatement = null;
        ResultSet rs = null;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteThatClient);
            deleteStatement.setString(1, name);
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "OrderDao:delete client" + e.getMessage());
        } finally {
            ConnectDataBase.close(deleteStatement);
            ConnectDataBase.close(dbConnection);
            ConnectDataBase.close(rs);
        }
    }
}

