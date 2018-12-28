import com.pncomp.lifegame.domain.LifeArea;
import com.pncomp.lifegame.domain.LifeField;
import com.pncomp.lifegame.presenters.AreaPresenter;

import javax.swing.*;
import java.awt.*;

public class SimulationGUIPresenter extends AreaPresenter{

    public JPanel getPanel() {
        return panel;
    }

    final JPanel panel;
    final int margin = 10;
    boolean outlineDrawn;

    public SimulationGUIPresenter(JPanel panel){
        this.panel = panel;
    }


    @Override
    public void printArea(final LifeArea area) {
        //cleanPanel();
        final int n = area.getLifeFields().length;
        Graphics graphics = panel.getGraphics();
        final int width=panel.getWidth()-margin, height=panel.getHeight()-margin;
        graphics.setColor(Color.black);
        graphics.drawRect(margin,margin, panel.getWidth()-2*margin, panel.getHeight()-2*margin);
        final int singleWidth = width/n, singleHeight=height/n;
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

    public void cleanPanel() {
        panel.getGraphics().clearRect(0,0, panel.getWidth(), panel.getHeight());
        outlineDrawn=false;
    }

    public void showValidationErrorMessage(){
        Graphics g = panel.getGraphics();
        g.setColor(Color.red);
        g.drawString("Validation error. Please check the values of the parameters entered.", 10, 50);
    }

    private void drawAreaCellsDetails(LifeArea area, int n) {
        for(int i=0; i<area.getLifeFields().length; i++){
            for(int j=0; j<area.getLifeFields()[0].length; j++){
                drawCellDetails(i,j,n, area.getLifeFields()[i][j]);
            }
        }
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


    public void drawCellDetails(final int x, final int y, final int n, LifeField field){
        Graphics graphics = panel.getGraphics();
        Cell c = calcGraphicsCoordinates(x, y, n);
        if(field.getOrg()!=null){
            graphics.setColor(Color.black);
            final int arcRadius = 3*Math.min((c.getX()-c.getX1()),(c.getY()-c.getY1()))/4;
            graphics.fillArc(c.getX()-arcRadius, c.getY()-arcRadius, 2*arcRadius, 2*arcRadius, 0, 360);
        } else {
            final int m = 1;
            graphics.clearRect(c.getX1()+1,c.getY1()+1, c.getCellWidth()-m, c.getCellHeight()-m);
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

}
