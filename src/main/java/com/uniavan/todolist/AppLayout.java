package com.uniavan.todolist;

import com.uniavan.todolist.layout.SaveButton;
import com.uniavan.todolist.layout.TaskListPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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

        var saveButton = new SaveButton(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listPanel.saveData();
            }
        });

        menuBar.add(saveButton.getButton());

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

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
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
