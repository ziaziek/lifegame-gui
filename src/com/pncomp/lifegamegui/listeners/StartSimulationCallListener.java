package com.pncomp.lifegamegui.listeners;

import com.pncomp.lifegame.ISimulationListener;
import com.pncomp.lifegame.LifeManager;
import com.pncomp.lifegame.helpers.LifeManagerFactory;
import com.pncomp.lifegamegui.config.ConfigParams;
import com.pncomp.lifegamegui.config.IConfigParamsHolder;
import com.pncomp.lifegamegui.enums.ProliferatorsEnum;
import com.pncomp.lifegamegui.presenters.SimulationGUIPresenter;

import javax.swing.*;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StartSimulationCallListener implements ActionListener {

    final SimulationGUIPresenter presenter;
    final IConfigParamsHolder configParamsHolder;
    final List<ISimulationListener> simulationListeners;
    Thread simulationThread;
    LifeManager lifeManager;

    public StartSimulationCallListener(final Component panel, final IConfigParamsHolder configParamsHolder, List<ISimulationListener> simulationListenerList){
        this.presenter=new SimulationGUIPresenter(panel);
        this.configParamsHolder=configParamsHolder;
        this.simulationListeners=simulationListenerList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(simulationThread!=null && simulationThread.isAlive() && lifeManager!=null){
            lifeManager.setGoSimulation(false);
            changeButtonText(e, "Run");
        } else {
            ConfigParams params = configParamsHolder.createConfigParams();
            if(params.validate()){
                changeButtonText(e, "Stop");
                presenter.resetPanel();
                presenter.setShowFood(params.isShowFood());
                try {
                    lifeManager = LifeManagerFactory.createLifeManager(params.getAreaSize(), params.getLifeProbability(), params.getMaxFood());
                    lifeManager.addSimulationListener(presenter);
                    simulationListeners.forEach(s -> lifeManager.addSimulationListener(s));
                    simulationThread=new Thread(()-> lifeManager.run(params.getEpochs(), ProliferatorsEnum.Factory.createProliferator(params.getProliferatorsEnum())));
                    simulationThread.start();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            } else {
                presenter.showValidationErrorMessage();
            }
        }
        }

    private void changeButtonText(ActionEvent e, final String text) {
        if(e.getSource() instanceof JButton){
            ((JButton)e.getSource()).setText(text);
        }
    }

}
