package Proiect;
import java.math.BigInteger;
public class Model {
    static final String INITIAL_VALUE = "1";
    private String m_total_1;
    private String m_total_2;
    private String m_rezultat;

    Model() {

        reset();
    }
    public void reset() {
        String reset=new String(INITIAL_VALUE);
        m_total_1 = reset;
        m_total_2 = reset;
        m_rezultat=reset;
    }
    public void setValuePolinom1(String value) {
        m_total_1 = new String(value);}
    public void setValuePolinom2(String value)
    {
        m_total_2=new String(value);
    }

    public String getValue() {

        return m_rezultat.toString();
    }
    public void addBy(String operand1,String operand2) {
        Polinom p1=new Polinom(operand1);
        Polinom p2=new Polinom(operand2);
        Polinom p3=Logica.suma(p1,p2);
        String intermediar="";
        for(int i:p3.getPolinom().keySet())
        {
            intermediar=intermediar+"("+p3.getPolinom().get(i).getCoeficient()+"x"+p3.getPolinom().get(i).getExponent()+")"+"+";
        }
         m_rezultat=intermediar;
    }

    public void substractBy(String operand1,String operand2) {
        Polinom p1=new Polinom(operand1);
        Polinom p2=new Polinom(operand2);
        Polinom p3=Logica.diferenta(p1,p2);
        String intermediar="";
        for(int i:p3.getPolinom().keySet())
        {
            intermediar=intermediar+"("+p3.getPolinom().get(i).getCoeficient()+"x"+p3.getPolinom().get(i).getExponent()+")"+"+";
        }
        m_rezultat=intermediar;
    }

    public void multiplyBy(String operand1,String operand2) {
        Polinom p1=new Polinom(operand1);
        Polinom p2=new Polinom(operand2);
        Polinom p3=Logica.inmultire(p1,p2);
        String intermediar="";
        for(int i:p3.getPolinom().keySet())
        {
            intermediar=intermediar+"("+p3.getPolinom().get(i).getCoeficient()+"x"+p3.getPolinom().get(i).getExponent()+")"+"+";
        }
        m_rezultat=intermediar;
    }

    public void derivateBy(String operand1) {
        Polinom p1=new Polinom(operand1);
        Polinom p3=Logica.derivare(p1);
        String intermediar="";
        for(int i:p3.getPolinom().keySet())
        {
            intermediar=intermediar+"("+p3.getPolinom().get(i).getCoeficient()+"x"+p3.getPolinom().get(i).getExponent()+")"+"+";
        }
        m_rezultat=intermediar;
    }
    public void integrateBy(String operand1) {
        Polinom p1=new Polinom(operand1);
        Polinom p3=Logica.integrare(p1);
        String intermediar="";
        for(int i:p3.getPolinom().keySet())
        {
            intermediar=intermediar+"("+p3.getPolinom().get(i).getCoeficient()+"x"+p3.getPolinom().get(i).getExponent()+")"+"+";
        }
        m_rezultat=intermediar;
    }

}
