package FrontEnd;

import Core.Session;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class GameLoader extends JDialog implements ChangeListener {
    private JPanel contentPane;
    private JLabel AppTitle;
    private JTabbedPane tabbedPane1;
    private JButton btnStartGame;
    private JLabel lblInstructions;
    private JLabel lblPlayer1;
    private JLabel lblPlayer2;
    private JLabel lblPlayer3;
    private JLabel lblPlayer4;
    private JLabel lblPlayer5;
    private JLabel lblPlayer6;
    private JCheckBox chbPlayer1Human;
    private JCheckBox chbPlayer2Human;
    private JCheckBox chbPlayer3Enabled;
    private JCheckBox chbPlayer4Enabled;
    private JCheckBox chbPlayer5Enabled;
    private JCheckBox chbPlayer6Enabled;
    private JTextField txtPlayer1Name;
    private JTextField txtPlayer2Name;
    private JTextField txtPlayer3Name;
    private JTextField txtPlayer4Name;
    private JTextField txtPlayer5Name;
    private JTextField txtPlayer6Name;
    private JLabel lnlInitialCash;
    private JSlider sldrInitialCash;
    private JTextField txtInitialCash;
    private JLabel lblRoundLimit;
    private JTextField txtRoundLimit;
    private JSlider sldrRoundLimit;
    private Properties prop;

    public GameLoader() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(btnStartGame);

        btnStartGame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int playerCount = 2;
                String[] playerNames = new String[6];
                playerNames[0] = txtPlayer1Name.getText();
                playerNames[1] = txtPlayer2Name.getText();

                prop.setProperty("player.0", txtPlayer1Name.getText());
                prop.setProperty("player.1", txtPlayer2Name.getText());
                if (chbPlayer3Enabled.isSelected()) {
                    playerNames[playerCount] = txtPlayer3Name.getText();
                    playerCount++;
                }
                if (chbPlayer4Enabled.isSelected()) {
                    playerNames[playerCount] = txtPlayer4Name.getText();
                    playerCount++;
                }
                if (chbPlayer5Enabled.isSelected()) {
                    playerNames[playerCount] = txtPlayer5Name.getText();
                    playerCount++;
                }
                if (chbPlayer6Enabled.isSelected()) {
                    playerNames[playerCount] = txtPlayer6Name.getText();
                    playerCount++;
                }
                for (int i = 0; i < playerCount; i++) {

                    prop.setProperty("player." + i, playerNames[i]);
                }
                prop.setProperty("game.players", String.valueOf(playerCount));
                while (playerCount < 6) {
                    prop.setProperty("player." + playerCount, "Player " + (playerCount + 1));
                    playerCount++;
                }
                prop.setProperty("initial.cash", txtInitialCash.getText());
                prop.setProperty("round.limit", txtRoundLimit.getText());
                onStart();
            }
        });

        sldrInitialCash.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();

                txtInitialCash.setText(String.valueOf(source.getValue()));
            }
        });

        sldrRoundLimit.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();

                txtRoundLimit.setText(source.getValue() + "");
            }
        });

        InputVerifier textIV = new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField txtField = (JTextField) input;
                if (txtField.getText().isEmpty()) {
                    txtField.setBackground(Color.RED);
                    btnStartGame.setEnabled(false);
                    return false;
                }
                txtField.setBackground(Color.WHITE);
                btnStartGame.setEnabled(true);
                return true;
            }
        };

        txtPlayer1Name.setInputVerifier(textIV);
        txtPlayer2Name.setInputVerifier(textIV);
        txtPlayer3Name.setInputVerifier(textIV);
        txtPlayer4Name.setInputVerifier(textIV);
        txtPlayer5Name.setInputVerifier(textIV);
        txtPlayer6Name.setInputVerifier(textIV);
        chbPlayer3Enabled.addChangeListener(this);
        chbPlayer4Enabled.addChangeListener(this);
        chbPlayer5Enabled.addChangeListener(this);
        chbPlayer6Enabled.addChangeListener(this);

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onClose();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onClose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        loadGameProperties();
    }


    @Override
    public void stateChanged(ChangeEvent e) {
        txtPlayer3Name.setEnabled(chbPlayer3Enabled.isSelected());
        txtPlayer4Name.setEnabled(chbPlayer4Enabled.isSelected());
        txtPlayer5Name.setEnabled(chbPlayer5Enabled.isSelected());
        txtPlayer6Name.setEnabled(chbPlayer6Enabled.isSelected());

        if (!sldrInitialCash.getValueIsAdjusting()) {
            sldrInitialCash.setValue(Integer.valueOf(txtInitialCash.getText()));
        }
        if (!sldrRoundLimit.getValueIsAdjusting()) {
            sldrRoundLimit.setValue(Integer.valueOf(txtRoundLimit.getText()));
        }
    }

    private void onStart() {
// add your code here
        dispose();

        setGameProperties();
        Session.run();
    }

    private void loadGameProperties() {
        this.prop = new Properties();
        try {
            prop.load(Session.class.getResourceAsStream("Game\\game.properties"));
            txtPlayer1Name.setText(prop.getProperty("player.0"));
            txtPlayer2Name.setText(prop.getProperty("player.1"));
            txtPlayer3Name.setText(prop.getProperty("player.2"));
            txtPlayer4Name.setText(prop.getProperty("player.3"));
            txtPlayer5Name.setText(prop.getProperty("player.4"));
            txtPlayer6Name.setText(prop.getProperty("player.5"));
            txtInitialCash.setText(prop.getProperty("initial.cash"));
            txtRoundLimit.setText(prop.getProperty("round.limit"));

        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (NullPointerException npe) {
            npe.printStackTrace();
        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        }
    }

    private void setGameProperties() {
        try {
            prop.store(new FileOutputStream(System.getProperty("user.dir") + "\\src\\Core\\Game\\game.properties"), null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void onClose() {
        dispose();
    }

    public static void initFrontEnd() {
        GameLoader dialog = new GameLoader();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        contentPane = new JPanel();
        contentPane.setLayout(new GridLayoutManager(1, 1, new Insets(5, 5, 5, 5), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        AppTitle = new JLabel();
        AppTitle.setFont(new Font(AppTitle.getFont().getName(), AppTitle.getFont().getStyle(), 22));
        AppTitle.setText("AIPoker");
        AppTitle.setToolTipText("v010907 (Neil Paul)");
        panel1.add(AppTitle, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        tabbedPane1 = new JTabbedPane();
        panel1.add(tabbedPane1, new GridConstraints(2, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(11, 4, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Play", panel2);
        btnStartGame = new JButton();
        btnStartGame.setText("Start Game");
        btnStartGame.setToolTipText("Starts a game with the above settings");
        panel2.add(btnStartGame, new GridConstraints(10, 0, 1, 4, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel2.add(spacer1, new GridConstraints(9, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        lblInstructions = new JLabel();
        lblInstructions.setText("Please enter the following settings and press 'Start Game'");
        panel2.add(lblInstructions, new GridConstraints(0, 0, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblPlayer1 = new JLabel();
        lblPlayer1.setFont(new Font(lblPlayer1.getFont().getName(), lblPlayer1.getFont().getStyle(), 14));
        lblPlayer1.setText("Player 1");
        panel2.add(lblPlayer1, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblPlayer2 = new JLabel();
        lblPlayer2.setFont(new Font(lblPlayer2.getFont().getName(), lblPlayer2.getFont().getStyle(), 14));
        lblPlayer2.setText("Player 2");
        panel2.add(lblPlayer2, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblPlayer3 = new JLabel();
        lblPlayer3.setFont(new Font(lblPlayer3.getFont().getName(), lblPlayer3.getFont().getStyle(), 14));
        lblPlayer3.setText("Player 3");
        panel2.add(lblPlayer3, new GridConstraints(5, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblPlayer4 = new JLabel();
        lblPlayer4.setFont(new Font(lblPlayer4.getFont().getName(), lblPlayer4.getFont().getStyle(), 14));
        lblPlayer4.setText("Player 4");
        panel2.add(lblPlayer4, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblPlayer5 = new JLabel();
        lblPlayer5.setFont(new Font(lblPlayer5.getFont().getName(), lblPlayer5.getFont().getStyle(), 14));
        lblPlayer5.setText("Player 5");
        panel2.add(lblPlayer5, new GridConstraints(7, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblPlayer6 = new JLabel();
        lblPlayer6.setFont(new Font(lblPlayer6.getFont().getName(), lblPlayer6.getFont().getStyle(), 14));
        lblPlayer6.setText("Player 6");
        panel2.add(lblPlayer6, new GridConstraints(8, 0, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        chbPlayer1Human = new JCheckBox();
        chbPlayer1Human.setSelected(true);
        chbPlayer1Human.setText("Human?");
        panel2.add(chbPlayer1Human, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        chbPlayer2Human = new JCheckBox();
        chbPlayer2Human.setActionCommand("Human?");
        chbPlayer2Human.setSelected(false);
        chbPlayer2Human.setText("Human?");
        panel2.add(chbPlayer2Human, new GridConstraints(4, 1, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        chbPlayer3Enabled = new JCheckBox();
        chbPlayer3Enabled.setSelected(true);
        chbPlayer3Enabled.setText("Enabled?");
        panel2.add(chbPlayer3Enabled, new GridConstraints(5, 1, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        chbPlayer4Enabled = new JCheckBox();
        chbPlayer4Enabled.setSelected(true);
        chbPlayer4Enabled.setText("Enabled?");
        panel2.add(chbPlayer4Enabled, new GridConstraints(6, 1, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        chbPlayer5Enabled = new JCheckBox();
        chbPlayer5Enabled.setSelected(true);
        chbPlayer5Enabled.setText("Enabled?");
        panel2.add(chbPlayer5Enabled, new GridConstraints(7, 1, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        chbPlayer6Enabled = new JCheckBox();
        chbPlayer6Enabled.setSelected(true);
        chbPlayer6Enabled.setText("Enabled?");
        panel2.add(chbPlayer6Enabled, new GridConstraints(8, 1, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtPlayer1Name = new JTextField();
        txtPlayer1Name.setText("Player 1");
        txtPlayer1Name.setToolTipText("Enter player name here");
        panel2.add(txtPlayer1Name, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtPlayer2Name = new JTextField();
        txtPlayer2Name.setText("Player 2");
        txtPlayer2Name.setToolTipText("Enter player name here");
        panel2.add(txtPlayer2Name, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtPlayer3Name = new JTextField();
        txtPlayer3Name.setText("Player 3");
        txtPlayer3Name.setToolTipText("Enter player name here");
        panel2.add(txtPlayer3Name, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtPlayer4Name = new JTextField();
        txtPlayer4Name.setText("Player 4");
        txtPlayer4Name.setToolTipText("Enter player name here");
        panel2.add(txtPlayer4Name, new GridConstraints(6, 2, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtPlayer5Name = new JTextField();
        txtPlayer5Name.setText("Player 5");
        txtPlayer5Name.setToolTipText("Enter player name here");
        panel2.add(txtPlayer5Name, new GridConstraints(7, 2, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        txtPlayer6Name = new JTextField();
        txtPlayer6Name.setText("Player 6");
        txtPlayer6Name.setToolTipText("Enter player name here");
        panel2.add(txtPlayer6Name, new GridConstraints(8, 2, 1, 1, GridConstraints.ANCHOR_NORTHWEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lnlInitialCash = new JLabel();
        lnlInitialCash.setText("Initial Cash");
        lnlInitialCash.setToolTipText("Players will start with this amount");
        panel2.add(lnlInitialCash, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sldrInitialCash = new JSlider();
        sldrInitialCash.setMajorTickSpacing(100);
        sldrInitialCash.setMaximum(500);
        sldrInitialCash.setMinimum(50);
        sldrInitialCash.setMinorTickSpacing(50);
        sldrInitialCash.setOrientation(0);
        sldrInitialCash.setPaintLabels(false);
        sldrInitialCash.setPaintTicks(false);
        sldrInitialCash.setPaintTrack(true);
        sldrInitialCash.setSnapToTicks(true);
        sldrInitialCash.setValue(100);
        sldrInitialCash.setValueIsAdjusting(false);
        panel2.add(sldrInitialCash, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtInitialCash = new JTextField();
        txtInitialCash.setEditable(false);
        txtInitialCash.setText("100");
        panel2.add(txtInitialCash, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        lblRoundLimit = new JLabel();
        lblRoundLimit.setText("Round Limit");
        lblRoundLimit.setToolTipText("Play will end after a number of rounds");
        panel2.add(lblRoundLimit, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        txtRoundLimit = new JTextField();
        txtRoundLimit.setEditable(false);
        txtRoundLimit.setHorizontalAlignment(2);
        txtRoundLimit.setText("0");
        txtRoundLimit.setToolTipText("0 for unlimited");
        panel2.add(txtRoundLimit, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        sldrRoundLimit = new JSlider();
        sldrRoundLimit.setMajorTickSpacing(50);
        sldrRoundLimit.setMaximum(200);
        sldrRoundLimit.setMinorTickSpacing(20);
        sldrRoundLimit.setPaintLabels(false);
        sldrRoundLimit.setPaintTicks(false);
        sldrRoundLimit.setSnapToTicks(true);
        sldrRoundLimit.setValue(0);
        sldrRoundLimit.setValueIsAdjusting(false);
        panel2.add(sldrRoundLimit, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Network", panel3);
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane1.addTab("Testing", panel4);
        final Spacer spacer2 = new Spacer();
        panel1.add(spacer2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
