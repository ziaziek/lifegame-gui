import com.pncomp.lifegame.domain.LifeArea;
import com.pncomp.lifegame.domain.LifeField;
import com.pncomp.lifegame.presenters.AreaPresenter;

import javax.swing.*;
import java.awt.*;

public class SimulationGUIPresenter extends AreaPresenter{

    public JPanel getPanel() {
        return panel;
    }

    final private JPanel panel;
    final private int margin = 10;
    private boolean outlineDrawn;
    private int maxMaxFood;

    public void setShowFood(boolean showFood) {
        this.showFood = showFood;
    }

    private boolean showFood;

    public SimulationGUIPresenter(JPanel panel){
        this.panel = panel;
    }


    @Override
    public void printArea(final LifeArea area) {

        final int n = area.getLifeFields().length;
        Graphics graphics = panel.getGraphics();
        final int width=panel.getWidth()-margin, height=panel.getHeight()-margin;
        final int singleWidth = width/n, singleHeight=height/n;

        graphics.setColor(Color.black);
        graphics.drawRect(margin,margin, panel.getWidth()-2*margin, panel.getHeight()-2*margin);

        if(!outlineDrawn){
            drawAreaOutlines(n, graphics, width, height, singleWidth, singleHeight);
            outlineDrawn=true;
        }

        drawAreaCellsDetails(area, n);

        panel.invalidate();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void resetPanel() {
        panel.getGraphics().clearRect(0,0, panel.getWidth(), panel.getHeight());
        outlineDrawn=false;
        showFood=false;
    }

    public void showValidationErrorMessage(){
        Graphics g = panel.getGraphics();
        g.setColor(Color.red);
        g.drawString("Validation error. Please check the values of the parameters entered.", 10, 50);
    }

    private void drawAreaCellsDetails(LifeArea area, int n) {
        maxMaxFood = findMaxFoodOnArea(area);
        for(int i=0; i<area.getLifeFields().length; i++){
            for(int j=0; j<area.getLifeFields()[0].length; j++){
                drawCellDetails(i,j,n, area.getLifeFields()[i][j]);
            }
        }
    }

    private int findMaxFoodOnArea(LifeArea area) {
        int mx = 0;
        for(int i=0; i<area.getLifeFields().length; i++){
            for(int j=0; j<area.getLifeFields()[0].length; j++){
                mx = Math.max(mx, area.getLifeFields()[i][j].getMaxFood());
            }
        }
        return mx;
    }

    private void drawAreaOutlines(int n, Graphics graphics, int width, int height, int singleWidth, int singleHeight) {
        for (int i=0; i<n; i++){
            final int x = margin+i*singleWidth, y = margin+i*singleHeight;
            if(i>0){
                graphics.drawLine(x, margin, x, height);
                graphics.drawLine(margin, y, width, y);
            }
        }
    }


    protected void drawCellDetails(final int x, final int y, final int n, final LifeField field){

        Graphics graphics = panel.getGraphics();
        Cell c = calcGraphicsCoordinates(x, y, n);
        final int m = 1;

        if(showFood){
            graphics.setColor(cellFoodColor(field, maxMaxFood));
            graphics.fillRect(c.getX1()+1,c.getY1()+1, c.getCellWidth()-m, c.getCellHeight()-m);
        }

        if(field.getOrg()!=null){
            graphics.setColor(Color.black);
            final int arcRadius = 3*Math.min((c.getX()-c.getX1()),(c.getY()-c.getY1()))/4;
            graphics.fillArc(c.getX()-arcRadius, c.getY()-arcRadius, 2*arcRadius, 2*arcRadius, 0, 360);
        }

    }

    private Cell calcGraphicsCoordinates(final int x, final  int y, int n){
        Cell r = new Cell();
        int xr = (panel.getWidth()-margin)/n;
        int yr = (panel.getHeight()-margin)/n;
        r.setX1(margin+x*xr);
        r.setY1(margin + y*yr);
        r.setX2(r.getX1()+xr);
        r.setY2(r.getY1()+yr);
        r.setX((r.getX2()+r.getX1())/2);
        r.setY((r.getY1()+r.getY2())/2);
        return r;
    }

    protected Color cellFoodColor(final LifeField field, final int maxFood){

        final Color[] colors = {Color.white, Color.pink, Color.YELLOW, Color.GREEN};
        final int nscale = Math.min(maxFood, 3);

        return colors[nscale * field.getFood()/maxFood];

    }

}
