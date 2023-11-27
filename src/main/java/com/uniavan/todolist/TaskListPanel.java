package com.uniavan.todolist;

import com.uniavan.todolist.database.DBFile;
import com.uniavan.todolist.model.Task;
import com.uniavan.todolist.utils.TableTransferHandler;
import com.uniavan.todolist.utils.tableModel.TaskTableModel;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Comparator;
import java.util.List;

public class TaskListPanel {
    private JPanel panel;

    private List<Task> Tasks;
    private TaskTableModel tasksModel;
    private JTable tableList;
    private boolean isEdited;

    public TaskListPanel() {
        panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = getConstraints();

        Tasks = DBFile.readTasks();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;

        createTaskListTable();
        panel.add(new JScrollPane(tableList), gbc);

        var descTextField = new JTextField(50);
        descTextField.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask(descTextField);
            }
        });
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Nova task:"), gbc);
        gbc.gridx = 1;
        panel.add(descTextField, gbc);

        var addButton = new JButton("Adicionar");
        gbc.gridx = 2;
        panel.add(addButton, gbc);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask(descTextField);
            }

        });

    }

    private void addTask(JTextField descTextField) {
        isEdited = true;
        var description = descTextField.getText();
        var newTask = new Task();

        newTask.setPriority(Tasks.size());
        newTask.setDescription(description.replace(";", ""));
        newTask.setId(getNextTaskId());
        Tasks.add(newTask);
        reloadTable();
        descTextField.setText("");
    }

    private void reloadTable() {
        tasksModel.setRowCount(0); // Limpa todas as linhas da tabela
        tasksModel = fetchTasks(); // Recarrega os dados
        tableList.setModel(tasksModel);
        columnsWidth();
        addListeners();
    }

    private TaskTableModel fetchTasks() {
        Tasks.sort(Comparator.comparingInt(Task::getPriority));
        return new TaskTableModel(Tasks);
    }

    private void columnsWidth() {
        final TableColumnModel columnModel = tableList.getColumnModel();

        columnModel.getColumn(0).setPreferredWidth(30);
        columnModel.getColumn(1).setPreferredWidth(30);
        columnModel.getColumn(2).setPreferredWidth(100);
        columnModel.getColumn(3).setPreferredWidth(558);
    }

    private void createTaskListTable() {
        tableList = new JTable();

        tasksModel = fetchTasks();
        tableList.setModel(tasksModel);

        var lh = new TableTransferHandler();
        tableList.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableList.setDragEnabled(true);
        tableList.setTransferHandler(lh);
        tableList.setDropMode(DropMode.INSERT_ROWS);
        tableList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        columnsWidth();

        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Deletar");
        popupMenu.add(deleteItem);

        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isEdited = true;
                TaskTableModel model = (TaskTableModel) tableList.getModel();
                int row = tableList.getSelectedRow();
                if (row < 0) return;
                model.removeRow(row);
                Tasks = model.getTasks();
            }
        });

        tableList.setComponentPopupMenu(popupMenu);
        addListeners();

    }

    private void addListeners() {
        TableColumn checkColumn = tableList.getColumnModel().getColumn(0);
        TableColumn descriptionColumn = tableList.getColumnModel().getColumn(3);

        DefaultCellEditor checkboxEditor = new DefaultCellEditor(new JCheckBox());
        DefaultCellEditor textEditor = new DefaultCellEditor(new JTextField());

        CellEditorListener listenerIsEditCell = new CellEditorListener() {
            @Override
            public void editingStopped(ChangeEvent e) {
                isEdited = true;
            }

            @Override
            public void editingCanceled(ChangeEvent e) {
            }
        };

        checkboxEditor.addCellEditorListener(listenerIsEditCell);
        checkColumn.setCellEditor(checkboxEditor);

        textEditor.addCellEditorListener(listenerIsEditCell);
        descriptionColumn.setCellEditor(textEditor);

        PropertyChangeListener propertyListener = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                // Verifica se a propriedade transferHandler mudou
                if (evt.getPropertyName().equals("dropLocation")) {
                    isEdited = true;
                }
            }
        };

        tableList.addPropertyChangeListener(propertyListener);
    }

    public static GridBagConstraints getConstraints() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        return gbc;
    }


    public JPanel getPanel() {
        return this.panel;
    }

    public void saveData() {
        var tasks = tasksModel.getTasks();
        DBFile.writeTasks(tasks);
        isEdited = false;
    }

    private int getNextTaskId() {
        if (Tasks.isEmpty()) return 1;

        Tasks.sort(Comparator.comparingInt(Task::getId).reversed());

        return Tasks.get(0).getId() + 1;
    }

    public boolean isEdited() {
        return isEdited;
    }
}
