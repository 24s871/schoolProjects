package bussinesLayer;

import dataAccesLayer.OrderDao;
import dataAccesLayer.ProductDao;
import model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * this class implements the methods from ProductDao and check that the inserted product is correct
 */

public class ProductBll {
    private static List<Validator<Product>> validators=new ArrayList<>();

    public ProductBll() {
        validators = new ArrayList<Validator<Product>>();
    }
    public static int insertProduct(Product product) {
        for (Validator<Product> v : validators) {
            v.validate(product);
        }
        return ProductDao.insert(product);
    }

    public static void deleteProduct(Product product)
    {
        for (Validator<Product> v : validators) {
            v.validate(product);
        }
        OrderDao.deletePrduct(product.getName());
        ProductDao.delete(product.getName());
    }
    public static int editProduct(Product product,String name,String price,String quantity)
    {
        for (Validator<Product> v : validators) {
            v.validate(product);
        }
        return ProductDao.edit(product.getName(),name,price,quantity);
    }
}
