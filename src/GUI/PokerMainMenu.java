package GUI;

import Core.Game;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PokerMainMenu extends JDialog implements ChangeListener {
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

    public PokerMainMenu() {
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
                return true;  //To change body of implemented methods use File | Settings | File Templates.
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
        Game.run();
    }

    private void loadGameProperties() {
        this.prop = new Properties();
        try {
            prop.load(Game.class.getResourceAsStream("BaseLogic\\game.properties"));
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
            prop.store(new FileOutputStream(System.getProperty("user.dir") + "\\src\\Core\\BaseLogic\\game.properties"), null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void onClose() {
        dispose();
    }

    public static void initFrontEnd() {
        PokerMainMenu dialog = new PokerMainMenu();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

}
