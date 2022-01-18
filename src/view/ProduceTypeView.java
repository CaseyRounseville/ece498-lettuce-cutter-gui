package view;

import model.ProduceType;

import util.ConcurrentObservable;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BorderFactory;

import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ProduceTypeView extends JPanel {
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
        lettuceButton = new JButton("Lettuce");
        microgreenButton = new JButton("Microgreen");

        // wire up lettuce button
        lettuceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                produceType.setValue(ProduceType.LETTUCE);
                produceTypeLabel.setText(ProduceType.LETTUCE.name());
            }
        });

        // wire up microgreen button
        microgreenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                produceType.setValue(ProduceType.MICROGREEN);
                produceTypeLabel.setText(ProduceType.MICROGREEN.name());
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
        this.add(selectedText, constr);

        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 1;
        constr.gridy = 0;
        this.add(produceTypeLabel, constr);

        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 0;
        constr.gridy = 1;
        this.add(lettuceButton, constr);

        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 1;
        constr.gridy = 1;
        this.add(microgreenButton, constr);
    }
}
