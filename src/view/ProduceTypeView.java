package view;

import model.ProduceType;

import util.ConcurrentObservable;

import serial.ArduinoCmder;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProduceTypeView extends JPanel {
    // images
    private static final ImageIcon lettuceImage;
    private static final ImageIcon microgreenImage;
    static {
        lettuceImage = new ImageIcon(new ImageIcon("assets/images/lettuce.jpeg").getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
        microgreenImage = new ImageIcon(new ImageIcon("assets/images/grass.png").getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
    }

    // observable produce type
    private ConcurrentObservable<ProduceType> produceType;

    // label
    private JLabel produceTypeLabel;

    // buttons
    private JButton lettuceButton;
    private JButton microgreenButton;

    public ProduceTypeView(
        ConcurrentObservable<ProduceType> produceType
    ) {
        this.produceType = produceType;

        // create label
        produceTypeLabel = new JLabel();

        // set label to initial value
        produceTypeLabel.setText(produceType.getValue().name());

        // create buttons
        lettuceButton = new JButton("Lettuce", lettuceImage);
        lettuceButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        lettuceButton.setHorizontalTextPosition(SwingConstants.CENTER);
        microgreenButton = new JButton("Microgreen", microgreenImage);
        microgreenButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        microgreenButton.setHorizontalTextPosition(SwingConstants.CENTER);

        // wire up lettuce button
        lettuceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                produceType.setValue(ProduceType.LETTUCE);
                produceTypeLabel.setText(ProduceType.LETTUCE.name());

                // issue command CMD_00 to arduino with data [1, 0, 0, ...]
                byte cmdId = ArduinoCmder.CMD_00;
                byte[] cmdData = new byte[ArduinoCmder.CMD_DATA_LEN];
                cmdData[0] = 1;
                ArduinoCmder.getInstance().sendCmd(cmdId, cmdData);
            }
        });

        // wire up microgreen button
        microgreenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                produceType.setValue(ProduceType.MICROGREEN);
                produceTypeLabel.setText(ProduceType.MICROGREEN.name());

                // issue command CMD_00 to arduino with data [0, 0, 0, ...]
                byte cmdId = ArduinoCmder.CMD_00;
                byte[] cmdData = new byte[ArduinoCmder.CMD_DATA_LEN];
                cmdData[0] = 0;
                ArduinoCmder.getInstance().sendCmd(cmdId, cmdData);
            }
        });

        // set the border of this jpanel
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.black),
            "Produce Type"
        );
        border.setTitleJustification(TitledBorder.CENTER);
        this.setBorder(border);

        // set the layout of this jpanel
        setLayout(new GridBagLayout());
        GridBagConstraints constr = new GridBagConstraints();

        JLabel selectedText = new JLabel("Selected");
        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 0;
        constr.gridy = 0;
        constr.insets = new Insets(5, 5, 5, 5);
        this.add(selectedText, constr);

        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 1;
        constr.gridy = 0;
        constr.insets = new Insets(5, 5, 5, 5);
        this.add(produceTypeLabel, constr);

        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 0;
        constr.gridy = 1;
        constr.insets = new Insets(5, 5, 5, 5);
        this.add(lettuceButton, constr);

        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 1;
        constr.gridy = 1;
        constr.insets = new Insets(5, 5, 5, 5);
        this.add(microgreenButton, constr);
    }
}
