package ui;

import java.awt.BorderLayout;
import java.awt.Cursor;
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
import javax.swing.SwingConstants;

import core.BytesStreamsAndFiles;
import core.Steg;
import window.Content;

public class CreateContent extends Content {

    private static final long serialVersionUID = 1L;

    JPanel buttonPanel = new JPanel();
    JPanel infoPanel = new JPanel();
    JPanel errorPanel = new JPanel();

    JButton openimg = new JButton("Open Image");
    JButton opendat = new JButton("Open Data");
    JButton save = new JButton("Save image");

    JLabel error = new JLabel();

    BufferedImage img;
    byte[] dat;

    JFileChooser fc = new JFileChooser();

    @Override
    protected void create() {
        setLayout(new BorderLayout());

        add(buttonPanel, BorderLayout.NORTH);
        add(errorPanel, BorderLayout.SOUTH);

        setupButton(openimg);
        setupButton(opendat);
        setupButton(save);
        save.setEnabled(false);

        errorPanel.add(error);
        error.setHorizontalTextPosition(SwingConstants.CENTER);

        openimg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadimg();
            }
        });

        opendat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loaddat();
            }
        });

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveData();
            }
        });

        windowFrame.pack();
        windowFrame.setSize(new Dimension(440, 230));
    }

    private void loadimg() {
        int returnVal = fc.showOpenDialog(CreateContent.this);
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

    private void loaddat() {
        int returnVal = fc.showOpenDialog(CreateContent.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            dat = BytesStreamsAndFiles.read(file);

            checkifavailable();
        }
    }

    private void checkifavailable() {
        save.setEnabled(false);
        error.setText("");

        if (dat != null && img != null) {
            if (dat.length < Math.pow(2, Steg.infobytes * 8)) {
                if (dat.length - Steg.infobytes <= img.getHeight() * img.getWidth()) {
                    save.setEnabled(true);
                } else {
                    error.setText("Data file too big for image");
                }
            } else {
                error.setText("Data beyond supported size");
            }
        }
    }

    private void saveData() {
        int returnVal = fc.showSaveDialog(CreateContent.this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();

            if (getExtension(file) == null) {
                file = new File(file.getAbsolutePath() + ".png");
            }

            BufferedImage out = Steg.generate(img, dat);

            try {

                ImageIO.write(out, "png", file);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            error.setText("Done");
        }
    }

    private void setupButton(JButton button) {
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonPanel.add(button);
    }

    private String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }
}
