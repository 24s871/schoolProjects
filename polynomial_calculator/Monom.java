package Proiect;
public class Monom {
    private double coeficient;
    private int exponent;

    Monom(double x,int y)
    {
        this.coeficient=x;
        if(y>=0)
            this.exponent=y;
        else this.exponent=0;
    }
    public void setCoeficient(double c)
    {
        this.coeficient=c;
    }

    public void setExponent(int e)
    {
        this.exponent=e;
    }

    public double getCoeficient()
    {
        return this.coeficient;
    }

    public int getExponent()
    {
        return exponent;
    }
}
