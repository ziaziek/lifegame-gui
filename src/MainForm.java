import com.pncomp.lifegame.ISimulationListener;
import com.pncomp.lifegame.SimulationEvent;
import com.pncomp.lifegame.SimulationEventType;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class MainForm extends JFrame implements IConfigParamsHolder, ISimulationListener {

    JPanel  pnPanParams;
    JTextField tfNEpochs;
    JTextField tfLfprob;
    JTextField maxFood, tfAreaSize;
    JCheckBox chckBoxShowFood;
    JLabel lblNEpochs;
    JButton btBtnRun;
    Component pnCanvasSim;
    int n_epoch=0;


    public MainForm(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setTitle("Life game simulator");

        setPreferredSize(new Dimension(1350, 1200));
        add(getSimulationPanel(), BorderLayout.CENTER);
        add(getHeaderPanel(), BorderLayout.NORTH);
        pack();
    }

    JPanel getHeaderPanel(){
        JPanel hp = new JPanel();
        hp.setLayout(new BorderLayout());
        hp.add(getParamsCommandPanel(), BorderLayout.CENTER);
        hp.add(getStatsPanel(), BorderLayout.EAST);
        return hp;
    }
    JPanel getSimulationPanel(){
        JPanel pnPanSim = new JPanel();
        pnCanvasSim = new Canvas();
        pnCanvasSim.setSize(999, 999);
        pnCanvasSim.setBackground(Color.white);
        pnPanSim.setBorder( BorderFactory.createTitledBorder( "Simulation" ) );
        pnPanSim.setLayout(new BorderLayout());
        pnPanSim.setSize(1000, 1000);
        pnPanSim.add(pnCanvasSim, BorderLayout.CENTER);
        return pnPanSim;
    }

    JPanel getStatsPanel(){
        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(2, 1));
        statsPanel.setBorder(BorderFactory.createTitledBorder("Statistics"));
        JPanel panNEpochs = new JPanel();
        lblNEpochs = new JLabel("0");
        panNEpochs.add(new JLabel("Epoch: "));
        panNEpochs.add(lblNEpochs);
        statsPanel.add(panNEpochs);

        statsPanel.add(new JPanel());

        return statsPanel;
    }
    JPanel getParamsCommandPanel(){
        JLabel lbLabel0;
        JLabel lbLabel1;

        pnPanParams = new JPanel();
        pnPanParams.setBorder( BorderFactory.createTitledBorder( "Parameters" ) );
        pnPanParams.setSize(new Dimension(1000, 200));
        pnPanParams.setLayout(new GridLayout(2, 5));

        //1
        JPanel probPan = new JPanel();
        lbLabel0 = new JLabel( "Life probability:"  );
        tfLfprob = new MyTextField();
        probPan.add(lbLabel0);
        probPan.add(tfLfprob);
        pnPanParams.add(probPan);


        //2
        JPanel maxFoodPan = new JPanel();
        maxFood=new MyTextField();
        maxFoodPan.add(new JLabel("Max food: "));
        maxFoodPan.add(maxFood);
        pnPanParams.add(maxFoodPan);

        //3
        JPanel epochsPan = new JPanel();
        lbLabel1 = new JLabel( "Number of epochs:"  );
        epochsPan.add( lbLabel1 );
        tfNEpochs = new MyTextField();
        epochsPan.add( tfNEpochs );
        pnPanParams.add(epochsPan);

        //4
        JPanel showFood = new JPanel();
        chckBoxShowFood = new JCheckBox("Show food");
        showFood.add(chckBoxShowFood);
        pnPanParams.add(showFood);

        //5
        pnPanParams.add(new JPanel());

        //1
        JPanel panSize = new JPanel();
        tfAreaSize =new MyTextField();
        panSize.add(new JLabel("Area size: "));
        panSize.add(tfAreaSize);
        pnPanParams.add(panSize);

        //2
        pnPanParams.add(new JPanel());

        //3
        btBtnRun = new JButton( "Run"  );
        pnPanParams.add(btBtnRun);
        btBtnRun.addActionListener(new StartSimulationCallListener(pnCanvasSim, this, Arrays.asList(this)));


        //4
        pnPanParams.add(new JPanel());

        //5
        pnPanParams.add(new JPanel());


        return pnPanParams;
    }

    @Override
    public ConfigParams createConfigParams() {
        ConfigParams params = new ConfigParams();
        try {
            params.setEpochs(Integer.valueOf(tfNEpochs.getText()));
            params.setLifeProbability(Integer.valueOf(tfLfprob.getText()));
            params.setMaxFood(Integer.valueOf(maxFood.getText()));
            params.setShowFood(chckBoxShowFood.isSelected());
            params.setAreaSize(tfAreaSize.getText()!="" ? Integer.valueOf(tfAreaSize.getText()) : 0);
        } catch (Exception ex){

        }

        return params;
    }

    @Override
    public void simulationChanged(SimulationEvent simulationEvent) {
        n_epoch++;
            if (SimulationEventType.EPOCH_RUN.equals(simulationEvent.getType())) {
            } else if (SimulationEventType.START.equals(simulationEvent.getType())) {
                n_epoch = 0;
            } else if(SimulationEventType.FINISHED.equals(simulationEvent.getType())){
                if(btBtnRun!=null){
                    btBtnRun.setText("Run");
                }
            }
        lblNEpochs.setText(String.valueOf(n_epoch));
    }
}
