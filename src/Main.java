import com.pncomp.lifegamegui.components.MainForm;

import javax.swing.*;

public class Main {

    final static String version = "2.1";

    public static void main(String[] args){

        final JFrame window = new MainForm(version);

        SwingUtilities.invokeLater(()-> {
            window.setVisible(true);
        });
    }
}
