package dataAccesLayer;

import model.Client;
import model.Product;
import presentation.ClientPage;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * this class uses sql syntax to update the Product table
 */

public class ProductDao {
    protected static final Logger LOGGER = Logger.getLogger(ClientDao.class.getName());
    private static final String insertStatementString = "INSERT INTO Product (name,pricePerUnit,cantitateTotal)"
            + " VALUES (?,?,?)";
    private final static String deleteStatementString = "DELETE FROM Product WHERE name = ?";
    public final static String viewStatementString="select * from Product";
    private final static String updateStatementString="UPDATE Product SET name=?,pricePerUnit=?,cantitateTotal=? WHERE name=?";

    public static int insert(Product product) {
        Connection dbConnection = ConnectDataBase.getConnection();
        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, product.getName());
            insertStatement.setString(2,product.getPrice());
            insertStatement.setString(3,product.getCantitate());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDao:insert " + e.getMessage());
        } finally {
            ConnectDataBase.close(insertStatement);
            ConnectDataBase.close(dbConnection);
        }
        return insertedId;
    }

    public static void delete(String name) {
        Connection dbConnection = ConnectDataBase.getConnection();
        PreparedStatement deleteStatement = null;
        ResultSet rs = null;
        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString);
            deleteStatement.setString(1, name);
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDao:delete " + e.getMessage());
        } finally {
            ConnectDataBase.close(deleteStatement);
            ConnectDataBase.close(dbConnection);
            ConnectDataBase.close(rs);
        }
    }

    public static int edit(String oldName,String newName,String newPrice,String newQuantity) {
        Connection dbConnection = ConnectDataBase.getConnection();
        PreparedStatement editStatement = null;
        int insertedId = -1;
        try {
            editStatement = dbConnection.prepareStatement(updateStatementString, Statement.RETURN_GENERATED_KEYS);
            editStatement.setString(1, newName );
            editStatement.setString(2, newPrice );
            editStatement.setString(3, newQuantity );
            editStatement.setString(4,oldName);
            editStatement.executeUpdate();

            ResultSet rs = editStatement.getGeneratedKeys();
            if (rs.next()) {
                insertedId = rs.getInt(1);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDao:edit " + e.getMessage());
        } finally {
            ConnectDataBase.close(editStatement);
            ConnectDataBase.close(dbConnection);
        }
        return insertedId;
    }

    public static int view( DefaultTableModel model) {
        Connection dbConnection = ConnectDataBase.getConnection();
        PreparedStatement viewStatement = null;
        int i=0; String name= "";String price="";String quantity="";
        try {
            viewStatement = dbConnection.prepareStatement(viewStatementString);
            ResultSet rs = viewStatement.executeQuery();
            while (rs.next()) {
                name = rs.getString("name");
                price=rs.getString("pricePerUnit");
                quantity=rs.getString("cantitateTotal");
                model.addRow(new Object[]{ name,price,quantity});
                i++;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDao:view " + e.getMessage());
        } finally {
            ConnectDataBase.close(viewStatement);
            ConnectDataBase.close(dbConnection);
        }
        return i;
    }
    public static String[] getProducts( ) {
        Connection dbConnection = ConnectDataBase.getConnection();
        PreparedStatement getStatement = null;
        String[] dataArray =new String[20];
        try {
            getStatement = dbConnection.prepareStatement(viewStatementString);
            ResultSet rs = getStatement.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int numColumns = metaData.getColumnCount();
            int row = 0;
            while (rs.next()) {
                String name=rs.getString("name");
                  int price=rs.getInt("pricePerUnit");
                  int q=rs.getInt("cantitateTotal");
                  Product a=new Product(name,price,q);
                  dataArray[row]=a.getName();
                row++;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDao:get " + e.getMessage());
        } finally {
            ConnectDataBase.close(getStatement);
            ConnectDataBase.close(dbConnection);
        }
        return  dataArray;
    }

    public static List<Product> getListProducts()
    {
        Connection dbConnection = ConnectDataBase.getConnection();
        PreparedStatement getStatement = null;
        List<Product> dataArray =new LinkedList<Product>();
        try {
            getStatement = dbConnection.prepareStatement(viewStatementString);
            ResultSet rs = getStatement.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            int numColumns = metaData.getColumnCount();
            int row = 0;
            while (rs.next()) {
                String name=rs.getString("name");
                int price=rs.getInt("pricePerUnit");
                int q=rs.getInt("cantitateTotal");
                Product a=new Product(name,price,q);
                dataArray.add(a);
                row++;
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ProductDao:get " + e.getMessage());
        } finally {
            ConnectDataBase.close(getStatement);
            ConnectDataBase.close(dbConnection);
        }
        return  dataArray;
    }


}
