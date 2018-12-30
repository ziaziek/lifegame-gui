import com.pncomp.lifegame.ISimulationListener;
import com.pncomp.lifegame.LifeManager;
import com.pncomp.lifegame.helpers.LifeManagerFactory;
import com.pncomp.lifegame.proliferators.SimpleProliferator;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class StartSimulationCallListener implements ActionListener {

    final SimulationGUIPresenter presenter;
    final IConfigParamsHolder configParamsHolder;
    final List<ISimulationListener> simulationListeners;

    public StartSimulationCallListener(final Component panel, final IConfigParamsHolder configParamsHolder, List<ISimulationListener> simulationListenerList){
        this.presenter=new SimulationGUIPresenter(panel);
        this.configParamsHolder=configParamsHolder;
        this.simulationListeners=simulationListenerList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        ConfigParams params = configParamsHolder.createConfigParams();
        if(params.validate()){
            try {
                presenter.resetPanel();
                presenter.setShowFood(params.isShowFood());
                LifeManager lifeManager= LifeManagerFactory.createLifeManager(params.getAreaSize(), params.getLifeProbability(), params.getMaxFood());
                lifeManager.addSimulationListener(presenter);
                simulationListeners.forEach(s -> lifeManager.addSimulationListener(s));
                lifeManager.run(params.getEpochs(), new SimpleProliferator());
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        } else {
            presenter.showValidationErrorMessage();
        }
    }
}
