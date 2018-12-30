import com.pncomp.lifegame.ISimulationListener;
import com.pncomp.lifegame.SimulationEvent;
import com.pncomp.lifegame.SimulationEventType;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
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

        setPreferredSize(new Dimension(1000, 1200));
        add(getSimulationPanel(), BorderLayout.CENTER);
        add(getEntryPanel(), BorderLayout.NORTH);
        pack();
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

    JPanel getEntryPanel(){
        JLabel lbLabel0;
        JLabel lbLabel1;



        pnPanParams = new JPanel();
        pnPanParams.setBorder( BorderFactory.createTitledBorder( "Parameters" ) );
        pnPanParams.setSize(new Dimension(1000, 200));
        pnPanParams.setLayout(new BorderLayout());

        JPanel probPan = new JPanel();
        probPan.setMinimumSize(new Dimension(500, 100));
        lbLabel0 = new JLabel( "Life probability:"  );
        tfLfprob = new MyTextField();
        probPan.add(lbLabel0);
        probPan.add(tfLfprob);
        pnPanParams.add(probPan, BorderLayout.WEST);

        JPanel epochsPan = new JPanel();
        lbLabel1 = new JLabel( "Number of epochs:"  );
        epochsPan.add( lbLabel1 );

        tfNEpochs = new MyTextField();
        epochsPan.add( tfNEpochs );
        pnPanParams.add(epochsPan, BorderLayout.EAST);
        pnPanParams.add( commandRunPanel(), BorderLayout.SOUTH );

        JPanel maxFoodPan = new JPanel();
        maxFood=new MyTextField();
        maxFoodPan.add(new JLabel("Max food: "));
        maxFoodPan.add(maxFood);
        pnPanParams.add(maxFoodPan, BorderLayout.CENTER);

        return pnPanParams;
    }

    private JPanel commandRunPanel(){
        JPanel panButton = new JPanel();
        GridLayout layout = new GridLayout(1, 5);
        panButton.setLayout(layout);
        btBtnRun = new JButton( "Run"  );
        JPanel showFood = new JPanel();
        chckBoxShowFood = new JCheckBox("Show food");
        showFood.add(chckBoxShowFood);
        panButton.add(showFood);

        panButton.add(new JPanel());
        JPanel panSize = new JPanel();
        tfAreaSize =new MyTextField();
        panSize.add(new JLabel("Area size: "));
        panSize.add(tfAreaSize);
        panButton.add(panSize);

        JPanel panNEpochs = new JPanel();
        lblNEpochs = new JLabel("0");
        panNEpochs.add(new JLabel("Epoch: "));
        panNEpochs.add(lblNEpochs);
        panButton.add(panNEpochs);
        panButton.add(btBtnRun);
        btBtnRun.addActionListener(new StartSimulationCallListener(pnCanvasSim, this, Arrays.asList(this)));
        return  panButton;
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
