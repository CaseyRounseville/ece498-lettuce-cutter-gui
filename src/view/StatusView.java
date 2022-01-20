package view;

import model.BladeStatus;
import model.PusherStatus;
import model.ProximitySensorStatus;

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
import java.awt.Insets;
import java.awt.Dimension;

public class StatusView extends JPanel {
    // observable statuses
    private ConcurrentObservable<BladeStatus> bladeStatus;
    private ConcurrentObservable<PusherStatus> pusherStatus;
    private ConcurrentObservable<ProximitySensorStatus> proximitySensorStatus;
    private ConcurrentObservable<Double> proximitySensorDistanceCm;

    // observers to observe changes in the statuses
    private ConcurrentObserver<BladeStatus> bladeStatusObserver;
    private ConcurrentObserver<PusherStatus> pusherStatusObserver;
    private ConcurrentObserver<ProximitySensorStatus> proximitySensorStatusObserver;
    private ConcurrentObserver<Double> proximitySensorDistanceCmObserver;

    // labels
    private JLabel bladeStatusLabel;
    private JLabel pusherStatusLabel;
    private JLabel proximitySensorStatusLabel;
    private JLabel proximitySensorDistanceCmLabel;

    public StatusView(
        ConcurrentObservable<BladeStatus> bladeStatus,
        ConcurrentObservable<PusherStatus> pusherStatus,
        ConcurrentObservable<ProximitySensorStatus> proximitySensorStatus,
        ConcurrentObservable<Double> proximitySensorDistanceCm
    ) {
        this.bladeStatus = bladeStatus;
        this.pusherStatus = pusherStatus;
        this.proximitySensorStatus = proximitySensorStatus;
        this.proximitySensorDistanceCm = proximitySensorDistanceCm;

        bladeStatusLabel = new JLabel();
        pusherStatusLabel = new JLabel();
        proximitySensorStatusLabel = new JLabel();
        proximitySensorDistanceCmLabel = new JLabel();

        // set size of labels
        bladeStatusLabel.setPreferredSize(new Dimension(75, 20));
        pusherStatusLabel.setPreferredSize(new Dimension(75, 20));
        proximitySensorStatusLabel.setPreferredSize(new Dimension(75, 20));
        proximitySensorDistanceCmLabel.setPreferredSize(new Dimension(75, 20));

        // set labels to initial values
        bladeStatusLabel.setText(bladeStatus.getValue().name());
        pusherStatusLabel.setText(pusherStatus.getValue().name());
        proximitySensorStatusLabel.setText(proximitySensorStatus.getValue().name());
        proximitySensorDistanceCmLabel.setText(String.format("%.2f", proximitySensorDistanceCm.getValue()) + " cm");

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

        // change proximity sensor status label when the proximity sensor status changes
        proximitySensorStatusObserver = new ConcurrentObserver<ProximitySensorStatus>() {
            @Override
            public void onChange(ProximitySensorStatus newProximitySensorStatus) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        proximitySensorStatusLabel.setText(proximitySensorStatus.getValue().name());
                    }
                });
            }
        };
        proximitySensorStatus.addObserver(proximitySensorStatusObserver);

        // change proximity sensor distance label when the proximity sensor distance changes
        proximitySensorDistanceCmObserver = new ConcurrentObserver<Double>() {
            @Override
            public void onChange(Double newDistanceCm) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        proximitySensorDistanceCmLabel.setText(String.format("%.2f", newDistanceCm) + " cm");
                    }
                });
            }
        };
        proximitySensorDistanceCm.addObserver(proximitySensorDistanceCmObserver);

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
        constr.insets = new Insets(5, 5, 5, 5);
        this.add(bladeStatusText, constr);

        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 1;
        constr.gridy = 0;
        constr.insets = new Insets(5, 5, 5, 5);
        this.add(bladeStatusLabel, constr);

        JLabel pusherStatusText = new JLabel("Pusher");
        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 0;
        constr.gridy = 1;
        constr.insets = new Insets(5, 5, 5, 5);
        this.add(pusherStatusText, constr);

        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 1;
        constr.gridy = 1;
        constr.insets = new Insets(5, 5, 5, 5);
        this.add(pusherStatusLabel, constr);

        JLabel proximitySensorStatusText = new JLabel("Prox_S");
        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 0;
        constr.gridy = 2;
        constr.insets = new Insets(5, 5, 5, 5);
        this.add(proximitySensorStatusText, constr);

        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 1;
        constr.gridy = 2;
        constr.insets = new Insets(5, 5, 5, 5);
        this.add(proximitySensorStatusLabel, constr);

        JLabel proximitySensorDistanceCmText = new JLabel("Prox_D");
        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 0;
        constr.gridy = 3;
        constr.insets = new Insets(5, 5, 5, 5);
        this.add(proximitySensorDistanceCmText, constr);

        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 1;
        constr.gridy = 3;
        constr.insets = new Insets(5, 5, 5, 5);
        this.add(proximitySensorDistanceCmLabel, constr);
    }
}
