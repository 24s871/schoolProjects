package Proiect;
import java.util.HashMap;

public class Logica {

    public static Polinom derivare(Polinom p)
    {
        for(Integer i:p.getPolinom().keySet()) {
            Monom m = p.getPolinom().get(i);
            m.setCoeficient(m.getCoeficient() * m.getExponent());
            m.setExponent(m.getExponent() - 1);
            if(m.getExponent()<0)
            {
                m.setCoeficient(0);
                m.setExponent(0);
            }
        }
        return p;
    }

    public static Polinom integrare(Polinom p)
    {
        for(Integer i:p.getPolinom().keySet()) {
            Monom m = p.getPolinom().get(i);
            m.setCoeficient(m.getCoeficient() / (m.getExponent()+1));
            m.setExponent(m.getExponent() + 1);
        }
        return p;
    }
    //29 linii de cod
    public static Polinom suma(Polinom p1,Polinom p2) {
        HashMap<Integer,Monom> rez=new HashMap<>();
        int max=p1.getMaxExponent();
        int max2=p2.getMaxExponent();
        int maxpol=max<max2 ? max2:max;
        Polinom.initPolinom(maxpol,rez);
        for (Integer i : rez.keySet()) {
            for (Integer j : p1.getPolinom().keySet()) {
                if (i.equals(j)) {
                    Monom m1 = p1.getPolinom().get(j);
                    Monom m2 = rez.get(i);
                    m2.setCoeficient(m1.getCoeficient() + m2.getCoeficient());
                    break;
                }
            }
        }
        for (Integer i : rez.keySet()) {
            for (Integer j : p2.getPolinom().keySet()) {
                if (i.equals(j)) {
                    Monom m1 = rez.get(i);
                    Monom m2 = p2.getPolinom().get(j);
                    m1.setCoeficient(m1.getCoeficient() + m2.getCoeficient());
                    break;
                }
            }
        }
        Polinom rezultat=new Polinom(rez);
        return rezultat;
    }
    //29 linii de cod
    public static Polinom diferenta(Polinom p1,Polinom p2)
    {   HashMap<Integer,Monom> rez=new HashMap<>();
        int max=p1.getMaxExponent();
        int max2=p2.getMaxExponent();
        int maxpol=max<max2 ? max2:max;
        Polinom.initPolinom(maxpol,rez);
        for (Integer i : rez.keySet()) {
            for (Integer j : p1.getPolinom().keySet()) {
                if (i.equals(j)) {
                    Monom m1 = p1.getPolinom().get(i);
                    Monom m2 = rez.get(i);
                    m2.setCoeficient(m1.getCoeficient() - m2.getCoeficient());
                    break;
                }
            }
        }
        for (Integer i : rez.keySet()) {
            for (Integer j : p2.getPolinom().keySet()) {
                if (i.equals(j)) {
                    Monom m1 = rez.get(i);
                    Monom m2 = p2.getPolinom().get(j);
                    m1.setCoeficient(m1.getCoeficient() - m2.getCoeficient());
                    break;
                }
            }
        }
        Polinom rezultat=new Polinom(rez);
        return rezultat;
    }
   //29 linii de cod
    public static Polinom inmultire(Polinom p1,Polinom p2)
    {   Polinom[] poliAdunat=new Polinom[20];
        for(int j=0;j<20; j++)
        {
            HashMap<Integer,Monom> x=new HashMap<>();
            poliAdunat[j]=new Polinom(x);
        }
        int k=0;
        for(Integer i:p1.getPolinom().keySet())
        {
            Monom m1=p1.getPolinom().get(i);
            HashMap<Integer,Monom> poliNou=new HashMap<>();
            for(Integer j:p2.getPolinom().keySet())
            {
                Monom m2=p2.getPolinom().get(j);
                Monom nou=new Monom(m1.getCoeficient()*m2.getCoeficient(),m1.getExponent()+m2.getExponent());
                poliNou.put(m1.getExponent()+m2.getExponent(),nou);
            }
            poliAdunat[k].setPolinom(poliNou);
            k++;
        }
        Polinom inmultire=Logica.suma(poliAdunat[1],poliAdunat[0]);
        for(int x=2;x< poliAdunat.length;x++)
        {
            inmultire=Logica.suma(inmultire,poliAdunat[x]);
        }
        return inmultire;
    }
}
