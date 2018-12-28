import static org.junit.jupiter.api.Assertions.*;

import com.pncomp.lifegame.domain.LifeField;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

public class PresenterTests {

    @Test
    public void colorTests(){
        LifeField lf = new LifeField();
        lf.setMaxFood(4);
        final int maxMaxFood = 4;
        lf.changeFoodPotential(0);
        Presenter p = new Presenter(null);
        assertEquals(Color.white, p.cellFoodColor(lf, maxMaxFood));
        lf.changeFoodPotential(2);
        assertEquals(Color.pink, p.cellFoodColor(lf, maxMaxFood));
        lf.changeFoodPotential(1);
        assertEquals(Color.YELLOW, p.cellFoodColor(lf, maxMaxFood));
        lf.changeFoodPotential(1);
        assertEquals(Color.GREEN, p.cellFoodColor(lf, maxMaxFood));
    }


    class Presenter extends SimulationGUIPresenter{


        public Presenter(JPanel panel) {
            super(panel);
        }

        public Color cellFoodColor(LifeField field, int maxFood) {
            return super.cellFoodColor(field, maxFood);
        }
    }
}
