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
import javax.swing.SwingConstants;

import core.BytesStreamsAndFiles;
import window.Content;

public class CreateContent extends StegTemplateContent {

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

    public CreateContent(Content previous) {
        super(previous);
    }

    @Override
    protected void create() {
        setLayout(new BorderLayout());

        add(buttonPanel, BorderLayout.NORTH);
        add(infoPanel);
        add(errorPanel, BorderLayout.SOUTH);

        setupButton(openimg, buttonPanel);
        setupButton(opendat, buttonPanel);
        setupButton(save, buttonPanel);
        setupButton(back, infoPanel);
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

            if (algo.isDataWithinSupportedSize(file.length())) {
                try {
                    dat = BytesStreamsAndFiles.read(file);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                checkifavailable();
            }
        }
    }

    private void checkifavailable() {
        save.setEnabled(false);
        error.setText("");

        if (dat != null && img != null) {
            if (algo.isWorkable(img, dat.length)) {
                save.setEnabled(true);
            } else {
                error.setText("Data file too big for image");
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

            BufferedImage out = algo.generate(img, dat);

            try {

                ImageIO.write(out, "png", file);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }

            error.setText("Done");
        }
    }
}
