package view;

import model.PowerStatus;

import util.ConcurrentObservable;
import util.ConcurrentObserver;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Dimension;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PowerView extends JPanel {
    // images
    private static final ImageIcon powerGreenImage;
    private static final ImageIcon powerRedImage;
    static {
        powerGreenImage = new ImageIcon(new ImageIcon("assets/images/power-green.jpeg").getImage().getScaledInstance(70, 50, Image.SCALE_DEFAULT));
        powerRedImage = new ImageIcon(new ImageIcon("assets/images/power-red.jpeg").getImage().getScaledInstance(70, 50, Image.SCALE_DEFAULT));
    }

    // observable statuses
    private ConcurrentObservable<PowerStatus> powerStatus;

    // observers to observe changes in the statuses
    private ConcurrentObserver<PowerStatus> powerStatusObserver;

    // labels
    private JLabel powerStatusLabel;

    // buttons
    private JButton powerButton;

    public PowerView(
        ConcurrentObservable<PowerStatus> powerStatus
    ) {
        this.powerStatus = powerStatus;

        powerStatusLabel = new JLabel();
        powerButton = new JButton("PWR", powerGreenImage);
        powerButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        powerButton.setHorizontalTextPosition(SwingConstants.CENTER);

        // set size of labels
        powerStatusLabel.setPreferredSize(new Dimension(100, 20));

        // set labels to initial values
        powerStatusLabel.setText(powerStatus.getValue().name());

        // set size of button
        powerButton.setPreferredSize(new Dimension(100, 100));

        // change power status label when the power status changes,
        // as whether the button can be pushed
        powerStatusObserver = new ConcurrentObserver<PowerStatus>() {
            @Override
            public void onChange(PowerStatus newPowerStatus) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        powerStatusLabel.setText(powerStatus.getValue().name());

                        switch (newPowerStatus) {
                            case TURNING_ON:
                                powerButton.setText("One sec...");
                                powerButton.setEnabled(false);
                                break;
                            case ON:
                                powerButton.setText("Turn OFF");
                                powerButton.setEnabled(true);
                                powerButton.setIcon(powerRedImage);
                                break;
                            case TURNING_OFF:
                                powerButton.setText("One sec...");
                                powerButton.setEnabled(false);
                                break;
                            case OFF:
                                powerButton.setText("Turn ON");
                                powerButton.setEnabled(true);
                                powerButton.setIcon(powerGreenImage);
                                break;
                        }
                    }
                });
            }
        };
        powerStatus.addObserver(powerStatusObserver);

        // wire up the power buttton
        powerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PowerStatus powerStatusValue = powerStatus.getValue();
                if (powerStatusValue == PowerStatus.OFF) {
                    powerStatus.setValue(PowerStatus.TURNING_ON);
                } else if (powerStatusValue == PowerStatus.ON) {
                    powerStatus.setValue(PowerStatus.TURNING_OFF);
                }
            }
        });

        // set the border of this jpanel
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.black),
            "Power"
        );
        border.setTitleJustification(TitledBorder.CENTER);
        this.setBorder(border);

        // set the layout of this jpanel
        // gridbaglayout reference:
        // https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
        setLayout(new GridBagLayout());
        GridBagConstraints constr = new GridBagConstraints();

        JLabel powerStatusText = new JLabel("Power");
        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 0;
        constr.gridy = 0;
        constr.insets = new Insets(5, 5, 5, 5);
        this.add(powerStatusText, constr);

        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 1;
        constr.gridy = 0;
        constr.insets = new Insets(5, 5, 5, 5);
        this.add(powerStatusLabel, constr);

        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 1;
        constr.gridy = 1;
        constr.insets = new Insets(5, 5, 5, 5);
        this.add(powerButton, constr);
    }
}
