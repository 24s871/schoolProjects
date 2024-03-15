package dataAccesLayer;

import model.Bill;
import model.Client;
import model.OrderDone;
import model.Product;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BillDao {
    protected static final Logger LOGGER = Logger.getLogger(ClientDao.class.getName());
    public final static String viewStatementString="select * from Bill";
    private static final String insertStatementString = "INSERT INTO Bill (idBill,idOrder,price)"
            + " VALUES (?,?,?)";
    private static final String findMaxId = "SELECT * FROM Bill";


    public static int findBill()
    {
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
    public static int insert(Bill bill) {
        Connection dbConnection = ConnectDataBase.getConnection();
        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, String.valueOf(bill.idBill()));
            insertStatement.setString(2, String.valueOf(bill.idOrder()));
            insertStatement.setString(3, String.valueOf(bill.price()));
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "BillDao:insert " + e.getMessage());
        } finally {
            ConnectDataBase.close(insertStatement);
            ConnectDataBase.close(dbConnection);
        }
        return insertedId;
    }
    public static List<Bill> getListBill()
    {
        Connection dbConnection = ConnectDataBase.getConnection();
        PreparedStatement getStatement = null;
        List<Bill> dataArray = new LinkedList<Bill>();
        try {
            getStatement = dbConnection.prepareStatement(viewStatementString);
            ResultSet rs = getStatement.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int numColumns = metaData.getColumnCount();
            int row = 0;
            while (rs.next()) {
               int idBill=rs.getInt("idBill");
               int idOrder=rs.getInt("idOrder");
               int price=rs.getInt("price");
               Bill a=new Bill(idBill,idOrder,price);
                dataArray.add(a);
                row++;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "BillDao:get " + e.getMessage());
        } finally {
            ConnectDataBase.close(getStatement);
            ConnectDataBase.close(dbConnection);
        }
        return  dataArray;
    }
}
