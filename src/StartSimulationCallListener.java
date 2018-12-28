import com.pncomp.lifegame.LifeManager;
import com.pncomp.lifegame.initiators.DefaultRandomAreaInitiator;
import com.pncomp.lifegame.initiators.RandomLifeInitiator;
import com.pncomp.lifegame.proliferators.SimpleProliferator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartSimulationCallListener implements ActionListener {

    final SimulationGUIPresenter presenter;
    final IConfigParamsHolder configParamsHolder;


    public StartSimulationCallListener(final JPanel panel, final IConfigParamsHolder configParamsHolder){
        this.presenter=new SimulationGUIPresenter(panel);
        this.configParamsHolder=configParamsHolder;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ConfigParams params = configParamsHolder.createConfigParams();
        if(params.validate()){
            try {
                presenter.resetPanel();
                presenter.setShowFood(params.isShowFood());
                LifeManager lifeManager=new LifeManager();
                lifeManager.init(new RandomLifeInitiator(params.getLifeProbability()), new DefaultRandomAreaInitiator(params.getMaxFood()));
                lifeManager.setPresenter(presenter);
                lifeManager.run(params.getEpochs(), new SimpleProliferator());
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        } else {
            presenter.showValidationErrorMessage();
        }
    }
}
