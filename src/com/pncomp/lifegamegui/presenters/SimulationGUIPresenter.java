package com.pncomp.lifegamegui.presenters;

import com.pncomp.lifegame.SimulationEvent;
import com.pncomp.lifegame.SimulationEventType;
import com.pncomp.lifegame.domain.LifeArea;
import com.pncomp.lifegame.domain.LifeField;
import com.pncomp.lifegame.presenters.AreaPresenter;
import com.pncomp.lifegamegui.domain.Cell;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class SimulationGUIPresenter extends AreaPresenter{

    public Component getPanel() {
        return panel;
    }

    final private Component panel;
    final private int margin = 1;
    private boolean outlineDrawn;
    private int maxMaxFood;
    private BufferStrategy strategy;
    int singleWidth, singleHeight;

    public void setShowFood(boolean showFood) {
        this.showFood = showFood;
    }

    private boolean showFood;

    public SimulationGUIPresenter(Component panel){
        this.panel = panel;
    }


    @Override
    public void printArea(final LifeArea area) {

        final int n = area.getLifeFields().length;
        if(panel instanceof  Canvas){
            Canvas cv = (Canvas)panel;
            if(strategy==null){
                cv.createBufferStrategy(2);
                strategy = cv.getBufferStrategy();
            }
            Graphics g=null;
            try {
                g =strategy.getDrawGraphics();
                renderArea(g, area, n);
            } finally {
                if(g!=null){
                    g.dispose();
                }
            }
            strategy.show();
        } else {
            renderArea(panel.getGraphics(), area, n);
        }

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {

        }
    }

    private void renderArea(Graphics graphics, LifeArea area, int n) {

        int width=panel.getWidth()-1, height=panel.getHeight()-1;
        singleWidth = width/n; singleHeight=height/n;

        if(!outlineDrawn){
            drawAreaOutlines(n, graphics, singleWidth, singleHeight);
            outlineDrawn=true;
        }

        drawAreaCellsDetails(area, n, graphics);

        panel.invalidate();
    }


    public void showValidationErrorMessage(){
        Graphics g = panel.getGraphics();
        g.setColor(Color.red);
        g.drawString("Validation error. Please check the values of the parameters entered.", 10, 50);
    }

    private void drawAreaCellsDetails(LifeArea area, int n, final Graphics graphics) {
        maxMaxFood = findMaxFoodOnArea(area);
        for(int i=0; i<area.getLifeFields().length; i++){
            for(int j=0; j<area.getLifeFields()[0].length; j++){
                drawCellDetails(i,j,n, area.getLifeFields()[i][j], graphics);
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

    private void drawAreaOutlines(int n, Graphics graphics, int singleWidth, int singleHeight) {
        graphics.setColor(Color.black);
        graphics.drawRect(0,0, singleWidth*n, singleHeight*n);
        graphics.setColor(Color.white);
        graphics.fillRect(1,1, singleWidth*n-1, singleHeight*n-1);
        graphics.setColor(Color.black);
        for (int i=1; i<n; i++){
            final int x =i*singleWidth, y = i*singleHeight;
            graphics.drawLine(x, 0, x, singleHeight*n);
            graphics.drawLine(0, y, singleWidth*n, y);
        }
    }


    protected void drawCellDetails(final int x, final int y, final int n, final LifeField field, final Graphics graphics){

        Cell c = calcGraphicsCoordinates(x, y);
        final int m = 1;

        if(showFood){
            graphics.setColor(cellFoodColor(field, maxMaxFood));
            graphics.fillRect(c.getX1()+1,c.getY1()+1, c.getCellWidth()-m, c.getCellHeight()-m);
        }

        if(field.getOrg()!=null){
            graphics.setColor(Color.black);
        } else if(!showFood){
            graphics.setColor(Color.white);
        }
        drawOrClearOrganism(graphics, c);
    }

    private void drawOrClearOrganism(Graphics graphics, Cell c) {
        final int arcRadius = 3*Math.min((c.getX()-c.getX1()),(c.getY()-c.getY1()))/4;
        graphics.fillArc(c.getX()-arcRadius, c.getY()-arcRadius, 2*arcRadius, 2*arcRadius, 0, 360);
    }

    private Cell calcGraphicsCoordinates(final int x, final  int y){
        Cell r = new Cell();
        int xr = singleWidth;
        int yr = singleHeight;
        r.setX1(x*xr);
        r.setY1(y*yr);
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

    public void resetPanel(){

        outlineDrawn=false;

        if(strategy!=null){
            strategy.dispose();
            strategy=null;
        }

        Graphics g = panel.getGraphics();
        g.setColor(Color.white);
        g.drawRect(0,0, panel.getWidth()-1, panel.getHeight()-1);
        panel.invalidate();
    }

}
