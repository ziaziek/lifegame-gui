import com.pncomp.lifegame.LifeManager;
import com.pncomp.lifegame.domain.LifeArea;
import com.pncomp.lifegame.proliferators.Proliferator;

public class CallingLifeManager extends LifeManager {

    final ISimulationListener listener;

    public CallingLifeManager(ISimulationListener listener){
        super();
        this.listener= listener;
    }

    public CallingLifeManager(LifeArea area, ISimulationListener listener){
        super(area);
        this.listener=listener;
    }


    @Override
    protected void runEpoch(Proliferator proliferator) {
        super.runEpoch(proliferator);
    }
}
