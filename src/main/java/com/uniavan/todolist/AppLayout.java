package com.uniavan.todolist;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class AppLayout extends JFrame {
    private static JPanel contentPanel;
    private static CardLayout cardLayout;

    public AppLayout() {
        setTitle("ToDo List App");


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);


        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);
        contentPanel.setBorder(BorderFactory.createTitledBorder("Suas tasks"));

        var listPanel = new TaskListPanel();

        var panel = listPanel.getPanel();
        contentPanel.add(panel, "todoListTasks");

        // Configurar o menu superior
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);


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

        JButton saveButton = new JButton(saveIcon);

        saveButton.setPressedIcon(saveIconGray);
        saveButton.setOpaque(false);
        saveButton.setContentAreaFilled(false);
        saveButton.setBorderPainted(false);
        saveButton.setMargin(new Insets(0, 0, 0, 0));
        menuBar.add(saveButton);

        // Cria um ActionListener para mudar a cor do fundo do botão
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listPanel.saveData();
            }
        });

        // Cria um MouseListener para mudar o ponteiro do mouse
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Muda o ponteiro do mouse para uma ampulheta
                saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Muda o ponteiro do mouse para o padrão
                saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                saveButton.setCursor(Cursor.getDefaultCursor());
            }
        });


        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if (listPanel.isEdited()) {


                    int confirmed = JOptionPane.showConfirmDialog(null,
                            "Você não salvou as alterações na ToDo List, fechar mesmo assim?", "Fechar ToDo List",
                            JOptionPane.YES_NO_OPTION);

                    if (confirmed == JOptionPane.YES_OPTION) {
                        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    } else {
                        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                    }
                } else {
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
            }
        });

        getContentPane().add(contentPanel);
        pack();
    }

    public static void changeScreen(String id) {
        cardLayout.show(contentPanel, id);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AppLayout().setVisible(true);
            }
        });
    }
}
