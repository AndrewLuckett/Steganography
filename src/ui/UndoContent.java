package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import core.BytesStreamsAndFiles;
import core.Steg;
import window.Content;

public class UndoContent extends StegTemplateContent {

    private static final long serialVersionUID = 1L;

    JPanel buttonPanel = new JPanel();
    JPanel infoPanel = new JPanel();
    JPanel errorPanel = new JPanel();

    JButton opendat = new JButton("Open Image");
    JButton save = new JButton("Save Data");
    JButton back = new JButton("Back");

    JLabel error = new JLabel();

    BufferedImage img;

    JFileChooser fc = new JFileChooser();

    public UndoContent(Content previous) {
        super(previous);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void create() {
        add(buttonPanel, BorderLayout.NORTH);
        add(infoPanel);
        add(errorPanel, BorderLayout.SOUTH);

        setupButton(opendat, buttonPanel);
        setupButton(save, buttonPanel);
        setupButton(back, infoPanel);
        save.setEnabled(false);

        opendat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadimg();
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveData();
            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                windowFrame.setContent(previouspane);
            }
        });

        windowFrame.pack();
        windowFrame.setSize(new Dimension(440, 230));

    }

    private void loadimg() {
        int returnVal = fc.showOpenDialog(UndoContent.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            if (getExtension(file).equals("png")) {
                try {
                    img = ImageIO.read(file);
                } catch (IOException eg) {
                    eg.printStackTrace();
                }

            } else {
                error.setText("Image needs to be a .png");
            }
            checkifavailable();
        }
    }

    private void saveData() {
        int returnVal = fc.showSaveDialog(UndoContent.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            if (getExtension(file) == null) {
                file = new File(file.getAbsolutePath() + ".png");
            }

            byte[] out = Steg.retrieve(img);

            try {
                BytesStreamsAndFiles.write(out, file);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            error.setText("Done");
        }
    }

    private void checkifavailable() {
        save.setEnabled(false);
        if (img != null) {
            save.setEnabled(true);
        }
    }
}
