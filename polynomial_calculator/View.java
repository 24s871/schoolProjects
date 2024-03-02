package Proiect;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame{
    private Model m_model;
    private JTextField rez=new JTextField(200);
    private JTextField poli1=new JTextField(200);
    private JTextField poli2=new JTextField(200);
    private JButton adunare=new JButton("+");
    private JButton scadere=new JButton("-");
    private JButton inmultire=new JButton("*");
    private JButton impartire=new JButton("/");
    private JButton egal=new JButton("=");
    private JButton sterge_un_element=new JButton("C");
    private JButton sterge_tot=new JButton("CE");
    private JButton derivare=new JButton("derivare");
    private JButton integrare=new JButton("integrare");

    //*Constructor*//

    View (Model model)
    {
        m_model = model;
        m_model.setValuePolinom1(model.INITIAL_VALUE);
        m_model.setValuePolinom2(model.INITIAL_VALUE);
        rez.setText(m_model.getValue());
        rez.setEditable(false);

        //components
        this.setTitle("Calculator Polinomial");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBounds(100,100,400,500);
        JPanel content=new JPanel();
        JPanel rezultat=new JPanel();
        JPanel polinom1=new JPanel();
        JPanel polinom2=new JPanel();
        rezultat.add(rez);
        polinom1.add(poli1);
        polinom2.add(poli2);
        rezultat.setBackground(Color.white);
        polinom1.setBackground(Color.white);
        polinom2.setBackground(Color.white);
        content.setLayout(new BoxLayout(content,BoxLayout.Y_AXIS));
        content.add(rez);
        content.add(poli1);
        content.add(poli2);
        JPanel operatii1=new JPanel();
        operatii1.setLayout(new FlowLayout());
        adunare.setBackground(Color.cyan);
        scadere.setBackground(Color.cyan);
        sterge_un_element.setBackground(Color.cyan);
        sterge_tot.setBackground(Color.cyan);
        operatii1.add(adunare);
        operatii1.add(scadere);
        operatii1.add(sterge_un_element);
        operatii1.add(sterge_tot);
        content.add(operatii1);
        JPanel operatii2=new JPanel();
        operatii2.setLayout(new FlowLayout());
        inmultire.setBackground(Color.cyan);
        impartire.setBackground(Color.cyan);
        derivare.setBackground(Color.cyan);
        integrare.setBackground(Color.cyan);
        operatii2.add(inmultire);
        operatii2.add(impartire);
        operatii2.add(integrare);
        operatii2.add(derivare);
        content.add(operatii2);
        egal.setSize(280,50);
        content.add(egal);
        this.add(content);
    }
    void reset() {
      rez.setText(m_model.INITIAL_VALUE);
      poli1.setText(m_model.INITIAL_VALUE);
      poli2.setText(m_model.INITIAL_VALUE);
    }
    String getUserInput1() {

        return poli1.getText();
    }
    String getUserInput2() {

        return poli2.getText();
    }
    void setTotal(String newTotal) {
        rez.setText(newTotal);
    }
    void showError(String errMessage) {

        JOptionPane.showMessageDialog(this, errMessage);
    }
    void addSumaListener(ActionListener mal) {

        adunare.addActionListener(mal);
    }
    void addScadereaListener(ActionListener mal) {

        scadere.addActionListener(mal);
    }
    void addMultiplicareListener(ActionListener mal) {

        inmultire.addActionListener(mal);
    }
    void addImpartireListener(ActionListener mal) {

        impartire.addActionListener(mal);
    }
    void addDerivareListener(ActionListener mal) {

        derivare.addActionListener(mal);
    }
    void addIntegrareListener(ActionListener mal) {

        integrare.addActionListener(mal);
    }
    void addClearListener(ActionListener cal) {

        sterge_tot.addActionListener(cal);
    }
}

