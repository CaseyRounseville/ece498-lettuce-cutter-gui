package view;

import model.BladeStatus;
import model.PusherStatus;

import util.ConcurrentObservable;
import util.ConcurrentObserver;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;

import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class StatusView extends JPanel {
    // observable statuses
    private ConcurrentObservable<BladeStatus> bladeStatus;
    private ConcurrentObservable<PusherStatus> pusherStatus;

    // observers to observe changes in the statuses
    private ConcurrentObserver<BladeStatus> bladeStatusObserver;
    private ConcurrentObserver<PusherStatus> pusherStatusObserver;

    // labels
    private JLabel bladeStatusLabel;
    private JLabel pusherStatusLabel;

    public StatusView(
        ConcurrentObservable<BladeStatus> bladeStatus,
        ConcurrentObservable<PusherStatus> pusherStatus
    ) {
        this.bladeStatus = bladeStatus;
        this.pusherStatus = pusherStatus;

        bladeStatusLabel = new JLabel();
        pusherStatusLabel = new JLabel();

        // set labels to initial values
        bladeStatusLabel.setText(bladeStatus.getValue().name());
        pusherStatusLabel.setText(pusherStatus.getValue().name());

        // change blade status label when the blade status changes
        bladeStatusObserver = new ConcurrentObserver<BladeStatus>() {
            @Override
            public void onChange(BladeStatus newBladeStatus) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        bladeStatusLabel.setText(bladeStatus.getValue().name());
                    }
                });
            }
        };
        bladeStatus.addObserver(bladeStatusObserver);

        // change pusher status label when the pusher status changes
        pusherStatusObserver = new ConcurrentObserver<PusherStatus>() {
            @Override
            public void onChange(PusherStatus newPusherStatus) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        pusherStatusLabel.setText(pusherStatus.getValue().name());
                    }
                });
            }
        };
        pusherStatus.addObserver(pusherStatusObserver);

        // set the border of this jpanel
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.black),
            "Status"
        );
        border.setTitleJustification(TitledBorder.CENTER);
        this.setBorder(border);

        // set the layout of this jpanel
        // gridbaglayout reference:
        // https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
        setLayout(new GridBagLayout());
        GridBagConstraints constr = new GridBagConstraints();

        JLabel bladeStatusText = new JLabel("Blade");
        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 0;
        constr.gridy = 0;
        this.add(bladeStatusText, constr);

        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 1;
        constr.gridy = 0;
        this.add(bladeStatusLabel, constr);

        JLabel pusherStatusText = new JLabel("Pusher");
        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 0;
        constr.gridy = 1;
        this.add(pusherStatusText, constr);

        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 1;
        constr.gridy = 1;
        this.add(pusherStatusLabel, constr);
    }
}
