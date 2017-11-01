package ui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import window.Content;

public class HomeContent extends Content {

    private static final long serialVersionUID = 1L;

    JButton create = new JButton("Create");
    JButton retrieve = new JButton("Retrieve");

    public HomeContent() {
        setupButton(create);
        setupButton(retrieve);

        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                windowFrame.setContent(new CreateContent(HomeContent.this));
            }
        });

        retrieve.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                windowFrame.setContent(new UndoContent(HomeContent.this));
            }
        });

    }

    private void setupButton(JButton button) {
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(button);
    }

    @Override
    protected void create() {
        windowFrame.pack();
        windowFrame.setSize(new Dimension(440, 230));
    }

}
