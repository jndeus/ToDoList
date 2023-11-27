package com.uniavan.todolist.layout;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class SaveButton {
    private final JButton button;

    public SaveButton(ActionListener actionClick) {
        var resourceSaveIcon = getClass().getResource("/save_icon.png");
        var resourceSaveIconPressed = getClass().getResource("/save_icon_press.png");
        BufferedImage imageSaveIcon, imageSaveIconPressed;
        try {
            imageSaveIcon = ImageIO.read(resourceSaveIcon);
            imageSaveIconPressed = ImageIO.read(resourceSaveIconPressed);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var imageScaled = imageSaveIcon.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        var imageScaledPressed = imageSaveIconPressed.getScaledInstance(20, 20, Image.SCALE_SMOOTH);

        ImageIcon saveIcon = new ImageIcon(imageScaled);
        ImageIcon saveIconGray = new ImageIcon(imageScaledPressed);

        button = new JButton(saveIcon);

        button.setPressedIcon(saveIconGray);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setMargin(new Insets(0, 0, 0, 0));
        // Cria um ActionListener para mudar a cor do fundo do botão
        button.addActionListener(actionClick);

        // Cria um MouseListener para mudar o ponteiro do mouse
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Muda o ponteiro do mouse para uma ampulheta
                button.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Muda o ponteiro do mouse para o padrão
                button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setCursor(Cursor.getDefaultCursor());
            }
        });
    }

    public JButton getButton() {
        return button;
    }
}
