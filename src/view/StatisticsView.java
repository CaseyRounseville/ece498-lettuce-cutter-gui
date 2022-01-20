package view;

import util.ConcurrentObservable;
import util.ConcurrentObserver;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.SwingUtilities;

import javax.swing.border.TitledBorder;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StatisticsView extends JPanel {
    // observable raft harvest counts
    private ConcurrentObservable<Integer> lettuceRaftCount;
    private ConcurrentObservable<Integer> microgreenRaftCount;

    // observers to observe changes in the raft counts
    private ConcurrentObserver<Integer> lettuceRaftCountObserver;
    private ConcurrentObserver<Integer> microgreenRaftCountObserver;

    // labels
    private JLabel lettuceRaftCountLabel;
    private JLabel microgreenRaftCountLabel;

    // buttons
    private JButton clearStatisticsButton;

    public StatisticsView(
        ConcurrentObservable<Integer> lettuceRaftCount,
        ConcurrentObservable<Integer> microgreenRaftCount
    ) {
        this.lettuceRaftCount = lettuceRaftCount;
        this.microgreenRaftCount = microgreenRaftCount;

        // create labels
        lettuceRaftCountLabel = new JLabel();
        microgreenRaftCountLabel = new JLabel();

        // set labels to initial values
        lettuceRaftCountLabel.setText(lettuceRaftCount.getValue().toString());
        microgreenRaftCountLabel.setText(microgreenRaftCount.getValue().toString());

        // change lettuce raft count label when the count changes
        lettuceRaftCountObserver = new ConcurrentObserver<Integer>() {
            @Override
            public void onChange(Integer newLettuceRaftCount) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        lettuceRaftCountLabel.setText(newLettuceRaftCount.toString());
                    }
                });
            }
        };
        lettuceRaftCount.addObserver(lettuceRaftCountObserver);

        // change microgreen raft count label when the count changes
        microgreenRaftCountObserver = new ConcurrentObserver<Integer>() {
            @Override
            public void onChange(Integer newMicrogreenRaftCount) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        microgreenRaftCountLabel.setText(newMicrogreenRaftCount.toString());
                    }
                });
            }
        };
        microgreenRaftCount.addObserver(microgreenRaftCountObserver);

        // create clear statistics button
        clearStatisticsButton = new JButton("Clear");

        // wire up clear statistics button
        clearStatisticsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lettuceRaftCount.setValue(0);
                microgreenRaftCount.setValue(0);
            }
        });

        // set the border of this jpanel
        TitledBorder border = BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.black),
            "Rafts Harvested"
        );
        border.setTitleJustification(TitledBorder.CENTER);
        this.setBorder(border);

        // set the layout of this jpanel
        setLayout(new GridBagLayout());
        GridBagConstraints constr = new GridBagConstraints();

        JLabel lettuceText = new JLabel("Lettuce");
        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 0;
        constr.gridy = 0;
        constr.insets = new Insets(5, 5, 5, 5);
        this.add(lettuceText, constr);

        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 1;
        constr.gridy = 0;
        constr.insets = new Insets(5, 5, 5, 5);
        this.add(lettuceRaftCountLabel, constr);

        JLabel microgreenText = new JLabel("Microgreen");
        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 0;
        constr.gridy = 1;
        constr.insets = new Insets(5, 5, 5, 5);
        this.add(microgreenText, constr);

        constr.fill = GridBagConstraints.HORIZONTAL;
        constr.gridx = 1;
        constr.gridy = 1;
        constr.insets = new Insets(5, 5, 5, 5);
        this.add(microgreenRaftCountLabel, constr);

        constr.fill = GridBagConstraints.BOTH;
        constr.gridx = 2;
        constr.gridy = 0;
        constr.gridheight = 2;
        constr.insets = new Insets(5, 5, 5, 5);
        this.add(clearStatisticsButton, constr);
    }
}
