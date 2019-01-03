import com.pncomp.lifegame.ISimulationListener;
import com.pncomp.lifegame.SimulationEvent;
import com.pncomp.lifegame.SimulationEventType;
import com.pncomp.lifegame.helpers.LifeAreaHelper;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Objects;

public class MainForm extends JFrame implements IConfigParamsHolder, ISimulationListener {

    JPanel  pnPanParams;
    JTextField tfNEpochs;
    JTextField tfLfprob;
    JTextField maxFood, tfAreaSize;
    JCheckBox chckBoxShowFood;
    JLabel lblNEpochs, lblNOrg;
    JButton btBtnRun;
    Component pnCanvasSim;
    JComboBox<ProliferatorsEnum> cmbProliferator;

    int n_epoch=0;


    public MainForm(final String versionNumber){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setTitle("Life game simulator "+ versionNumber);

        setPreferredSize(new Dimension(1400, 1200));
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

        JPanel panNOrg = new JPanel();
        lblNOrg = new JLabel("0");
        panNOrg.add(new JLabel("Organisms: "));
        panNOrg.add(lblNOrg);
        statsPanel.add(panNOrg);

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
        pnPanParams.add(createProliferatorsCombo());

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
            params.setAreaSize(!Objects.equals(tfAreaSize.getText(), "") ? Integer.valueOf(tfAreaSize.getText()) : 0);
            params.setProliferatorsEnum((ProliferatorsEnum)cmbProliferator.getSelectedItem());
        } catch (Exception ex){

        }

        return params;
    }

    @Override
    public void simulationChanged(SimulationEvent simulationEvent) {
        n_epoch++;
            if (SimulationEventType.EPOCH_RUN.equals(simulationEvent.getType())) {
                lblNOrg.setText(String.valueOf(LifeAreaHelper.numberOfOrganisms(simulationEvent.getArea())));
            } else if (SimulationEventType.START.equals(simulationEvent.getType())) {
                n_epoch = 0;
            } else if(SimulationEventType.FINISHED.equals(simulationEvent.getType())){
                if(btBtnRun!=null){
                    btBtnRun.setText("Run");
                }
            }
        lblNEpochs.setText(String.valueOf(n_epoch));
    }

    private Component createProliferatorsCombo(){
        cmbProliferator = new JComboBox<>();
        cmbProliferator.setRenderer((list, value, index, isSelected, cellHasFocus) -> new JLabel(value.description));
        cmbProliferator.addItem(ProliferatorsEnum.DEFAULT_PROLIFERATOR);
        cmbProliferator.addItem(ProliferatorsEnum.PAIR_PROLIFERATOR);
        return cmbProliferator;
    }
}
