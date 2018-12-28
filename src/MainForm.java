import javax.swing.*;
import java.awt.*;

public class MainForm extends JFrame implements IConfigParamsHolder {

    JPanel pnPanSim, pnPanParams;
    JTextField tfNEpochs;
    JTextField tfLfprob;
    JTextField maxFood;
    JCheckBox chckBoxShowFood;


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
        pnPanSim = new JPanel();
        pnPanSim.setBorder( BorderFactory.createTitledBorder( "Simulation" ) );
        pnPanSim.setSize(1000, 1000);
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
        JButton btBtnRun;
        GridLayout layout = new GridLayout(1, 5);
        panButton.setLayout(layout);
        btBtnRun = new JButton( "Run"  );
        JPanel showFood = new JPanel();
        chckBoxShowFood = new JCheckBox("Show food");
        showFood.add(chckBoxShowFood);
        panButton.add(showFood);
        panButton.add(new JPanel());
        panButton.add(btBtnRun);
        panButton.add(new JPanel());
        panButton.add(new JPanel());
        btBtnRun.addActionListener(new StartSimulationCallListener(pnPanSim, this));
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
        } catch (Exception ex){

        }

        return params;
    }
}
