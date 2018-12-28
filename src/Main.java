import javax.swing.*;

public class Main {

    public static void main(String[] args){

        final JFrame window = new MainForm();

        SwingUtilities.invokeLater(()-> {
            window.setVisible(true);
        });
    }
}
