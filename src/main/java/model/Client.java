package model;

/**
 * this class models the client object
 */

public class Client {

    private String name;

    public Client(String a)
    {
        this.name=a;

    }

    public String getName()
    {
        return this.name;
    }

    public void setName(String a)
    {
         this.name=a;
    }

}
