package model;

/**
 * this class model the product object
 */
public class Product {

    private String name;
    private int pricePerUnit;
    private int cantitateTotal;

    public Product(String a,int x,int y)
    {

        this.name=a;
        this.pricePerUnit=x;
        this.cantitateTotal=y;
    }
    public Product(String a,String b,String c)
    {
        this.name=a;
        this.pricePerUnit=Integer.parseInt(b);
        this.cantitateTotal=Integer.parseInt(c);
    }

    public String getName()
    {
        return this.name;
    }

    public String getPrice()
    {
        return Integer.toString(this.pricePerUnit);
    }

    public String getCantitate()
    {
        return Integer.toString(this.cantitateTotal);
    }
   public void setCantitate(int x)
   {
       this.cantitateTotal=x;
   }

    public void setName(String nameProduct) {
        this.name=nameProduct;
    }

    public void setPrice(int p) {
        this.pricePerUnit=p;
    }
}
