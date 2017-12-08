package ui;

import java.awt.Cursor;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import core.Steg;
import core.StegAlgoInterface;
import window.Content;

public abstract class StegTemplateContent extends Content {

    private static final long serialVersionUID = 1L;

    protected Content previouspane;

    JFileChooser fc = new JFileChooser();

    StegAlgoInterface algo = new Steg();

    public StegTemplateContent(Content previous) {
        previouspane = previous;
    }

    protected void setupButton(JButton button, JPanel element) {
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        element.add(button);
    }

    protected String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

}
