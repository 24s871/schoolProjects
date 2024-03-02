package Proiect;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

        Model model = new Model();
        View view = new View(model);
        Controller controller = new Controller(model, view);

        view.setVisible(true);

    }
}