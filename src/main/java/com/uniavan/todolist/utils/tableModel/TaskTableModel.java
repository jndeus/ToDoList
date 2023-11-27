package com.uniavan.todolist.utils.tableModel;
import com.uniavan.todolist.model.Task;
import com.uniavan.todolist.model.TaskStatus;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class TaskTableModel extends DefaultTableModel
{
    private final String[] columnNames = new String[] {
            "", "Id", "Status", "Descrição"
    };
    private final Class[] columnClass = new Class[] {
            Boolean.class, String.class, String.class, String.class
    };

    public TaskTableModel(List<Task> taskList)
    {
        // Define os nomes das colunas
        setColumnIdentifiers(columnNames);
        // Adiciona os dados da lista de tarefas na tabela
        for (Task task : taskList) {
            addRow(new Object[] {
                    task.getStatus() == TaskStatus.COMPLETED,
                    task.getId(),
                    task.getStatus(),
                    task.getDescription()
            });
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return columnClass[columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return switch (columnIndex) {
            case 0, 3 -> true;
            default -> false;
        };
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        if (columnIndex == 3){
            aValue = ((String)aValue).replace(";", "");
        }
        super.setValueAt(aValue, rowIndex, columnIndex);
        if (columnIndex == 0) {
            super.setValueAt((boolean)aValue ? TaskStatus.COMPLETED : TaskStatus.PENDING, rowIndex, 2);
        }
    }

    public List<Task> getTasks() {
        var tasks = new ArrayList<Task>();

        for (int row = 0; row < getRowCount(); row++) {
            var task = new Task();
            var status = (Boolean) getValueAt(row, 0) ? TaskStatus.COMPLETED : TaskStatus.PENDING;

            task.setStatus(status);
            task.setId((Integer) getValueAt(row, 1));
            task.setPriority(row);
            task.setDescription((String) getValueAt(row, 3));

            tasks.add(task);
        }

        return tasks;
    }

}