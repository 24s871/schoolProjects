package Proiect;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Controller {
    private final Model m_model;
    private final View m_view;

    //Constructor//
    Controller(Model model,View view)
    {
        m_model=model;
        m_view=view;
        view.addClearListener(new ClearListener());
        view.addSumaListener(new SumaListener());
        view.addScadereaListener(new ScadereaListener());
        view.addMultiplicareListener(new MultiplicareListener());
        //view.addImpartireListener(new ImpartireListener());
        view.addDerivareListener(new DerivareListener());
        view.addIntegrareListener(new IntegrareListener());
    }
    class ClearListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            m_model.reset();
            m_view.reset();
        }
    }// end inner class ClearListener

    class SumaListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String userInput1 = "";
            String userInput2="";
            try {
                userInput1 = m_view.getUserInput1();
                userInput2= m_view.getUserInput2();
                m_model.addBy(userInput1,userInput2);
                m_view.setTotal(m_model.getValue());

            } catch (NumberFormatException nfex) {
                m_view.showError("Bad input: '" + userInput1 + "'"+userInput2);
            }
        }
    }

    class ScadereaListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String userInput1 = "";
            String userInput2="";
            try {
                userInput1 = m_view.getUserInput1();
                userInput2= m_view.getUserInput2();
                m_model.substractBy(userInput1,userInput2);
                m_view.setTotal(m_model.getValue());

            } catch (NumberFormatException nfex) {
                m_view.showError("Bad input: '" + userInput1 + "'"+userInput2);
            }
        }
    }

    class MultiplicareListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String userInput1 = "";
            String userInput2="";
            try {
                userInput1 = m_view.getUserInput1();
                userInput2= m_view.getUserInput2();
                m_model.multiplyBy(userInput1,userInput2);
                m_view.setTotal(m_model.getValue());

            } catch (NumberFormatException nfex) {
                m_view.showError("Bad input: '" + userInput1 + "'"+userInput2);
            }
        }
    }

    class DerivareListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String userInput1 = "";
            //String userInput2="";
            try {
                userInput1 = m_view.getUserInput1();
                //userInput2= m_view.getUserInput2();
                m_model.derivateBy(userInput1);
                m_view.setTotal(m_model.getValue());

            } catch (NumberFormatException nfex) {
                m_view.showError("Bad input: '" + userInput1 );
            }
        }
    }

    class IntegrareListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String userInput1 = "";
            //String userInput2="";
            try {
                userInput1 = m_view.getUserInput1();
                //userInput2= m_view.getUserInput2();
                m_model.integrateBy(userInput1);
                m_view.setTotal(m_model.getValue());

            } catch (NumberFormatException nfex) {
                m_view.showError("Bad input: '" + userInput1 );
            }
        }
    }


}
