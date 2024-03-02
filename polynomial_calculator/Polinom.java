package Proiect;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polinom {
    private HashMap<Integer,Monom> polinom;
    Polinom(HashMap<Integer,Monom> x)
    {
        this.polinom=x;
    }
    Polinom (String input){
        HashMap<Integer,Monom> poli=new HashMap<>();
        Pattern pattern=Pattern.compile("([-+][0-9]+)(x)([0-9]+)",Pattern.CASE_INSENSITIVE);
        Matcher matcher=pattern.matcher(input);
        while(matcher.find())
        {
            String s=matcher.group(1).toString();
            double coef=Double.parseDouble(s);
            String a=matcher.group(3).toString();
            int exp=Integer.parseInt(a);
            Monom m=new Monom(coef,exp);
            poli.put(exp,m);
        }
       this.polinom=poli;
    }

    public void setPolinom(HashMap<Integer,Monom> p)
    {
        this.polinom=p;
    }

    public HashMap<Integer,Monom> getPolinom()
    {
        return this.polinom;
    }

    public int getMaxExponent()
    {
        int max=0;
        for(int i:this.getPolinom().keySet())
        {
            if(max<=i)
                max=i;
        }
        return max;
    }
    public static void initPolinom(int nr,HashMap<Integer,Monom> init)
    {
        for(int i=0;i<=nr;i++)
        {
            Monom m=new Monom(0,i);
            init.put(i,m);
        }
    }


}

