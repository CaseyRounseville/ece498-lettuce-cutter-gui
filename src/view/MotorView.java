package view;

import model.OpenLoopStepperMotorModel;

import controller.OpenLoopStepperMotorController;

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

public class MotorView extends JPanel {
    // motor models
    private OpenLoopStepperMotorModel motorModel1;
    private OpenLoopStepperMotorModel motorModel2;

    // motor controllers
    private OpenLoopStepperMotorController motorController1;
    private OpenLoopStepperMotorController motorController2;

    // labels
    private JLabel lblMotor1;
    private JLabel lblMotor2;

    // buttons
    private JButton btnPwrMotor1;
    private JButton btnPwrMotor2;

    public MotorView(
        OpenLoopStepperMotorModel motorModel1,
        OpenLoopStepperMotorModel motorModel2,
        OpenLoopStepperMotorController motorController1,
        OpenLoopStepperMotorController motorController2
    ) {
        this.motorModel1 = motorModel1;
        this.motorModel2 = motorModel2;
        this.motorController1 = motorController1;
        this.motorController2 = motorController2;

        // create labels
        lblMotor1 = new JLabel("Motor 1");
        lblMotor2 = new JLabel("Motor 2");

        // create buttons
        btnPwrMotor1 = new JButton("Turn On");
        btnPwrMotor2 = new JButton("Turn On");

        // wire up power button for motor 1
        btnPwrMotor1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (motorModel1.isCurrentlyOn()) {
                    motorController1.turnOff();
                    btnPwrMotor1.setText("Turn On");
                } else {
                    motorController1.turnOn();
                    btnPwrMotor1.setText("Turn Off");
                }
            }
        });

        // wire up power button for motor 2
        btnPwrMotor2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (motorModel2.isCurrentlyOn()) {
                    motorController2.turnOff();
                    btnPwrMotor2.setText("Turn On");
                } else {
                    motorController2.turnOn();
                    btnPwrMotor2.setText("Turn Off");
                }
            }
        });

        // set the border of this jpanel
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.black),
            "Motors"
        );
        border.setTitleJustification(TitledBorder.CENTER);
        this.setBorder(border);

        // set the layout of this jpanel
        setLayout(new GridBagLayout());
        GridBagConstraints constr = new GridBagConstraints();

        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 0;
        constr.gridy = 0;
        constr.insets = new Insets(5, 5, 5, 5);
        this.add(lblMotor1, constr);

        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 1;
        constr.gridy = 0;
        constr.insets = new Insets(5, 5, 5, 5);
        this.add(btnPwrMotor1, constr);

        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 0;
        constr.gridy = 1;
        constr.insets = new Insets(5, 5, 5, 5);
        this.add(lblMotor2, constr);

        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 1;
        constr.gridy = 1;
        constr.insets = new Insets(5, 5, 5, 5);
        this.add(btnPwrMotor2, constr);
    }
}
