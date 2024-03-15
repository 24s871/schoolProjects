package model;

/**
 * this class models the order object
 *
 */
public class OrderDone {
    private int id;
    private String clientWhoOrdered;
    private String orderedProduct;
    private int desiredQuantity;

    public OrderDone(int k,String a,String b,int x)
    {
        this.id=k;
        this.clientWhoOrdered=a;
        this.orderedProduct=b;
        this.desiredQuantity=x;
    }

    public String getClient()
    {
        return this.clientWhoOrdered;
    }
    public String getOrderedProduct()
    {
        return this.orderedProduct;
    }
    public String getDesiredQuantity()
    {
        return Integer.toString(this.desiredQuantity);
    }
    public int getId(){return this.id;}

    public void setId(int id) {
        this.id=id;
    }
    public void setClient(String clientWhoOrdered)
    {
         this.clientWhoOrdered=clientWhoOrdered;
    }
    public void setOrderedProduct(String orderedProduct)
    {
         this.orderedProduct=orderedProduct;
    }
    public void setDesiredQuantity(int x)
    {
        this.desiredQuantity=x;
    }

}
