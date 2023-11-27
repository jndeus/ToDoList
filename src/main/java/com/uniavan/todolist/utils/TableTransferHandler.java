package com.uniavan.todolist.utils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class TableTransferHandler extends TransferHandler {
    public boolean importData(TransferSupport info) {
        Object[] data = null;

        if (!canImport(info)) {
            return false;
        }

        JTable list = (JTable) info.getComponent();
        DefaultTableModel model = (DefaultTableModel) list.getModel();

        try {
            data = (Object[]) info.getTransferable().getTransferData(info.getDataFlavors()[0]);

        } catch (UnsupportedFlavorException ufe) {
            System.out.println("importData: unsupported data flavor");
            return false;
        } catch (IOException ioe) {
            System.out.println("importData: I/O exception");
            return false;
        }


        if (info.isDrop()) {
            JTable.DropLocation dl = (JTable.DropLocation) info.getDropLocation();
            int index = dl.getRow();
            model.insertRow(index, data);
            return true;
        }

        return true;
    }

    /**
     * Bundle up the data for export.
     */
    protected Transferable createTransferable(JComponent c) {
        JTable table = (JTable) c;
        int index = table.getSelectedRow();
        int columnCount = table.getColumnCount();
        Object[] value = new Object[columnCount];
        for (int i = 0; i < columnCount; i++) {
            value[i] = table.getValueAt(index, i);
        }
        return new ObjectArrayTransferable(value);
    }

    /**
     * The list handles both copy and move actions.
     */
    public int getSourceActions(JComponent c) {
        return MOVE;
    }

    /**
     * When the export is complete, remove the old list entry if the
     * action was a move.
     */
    protected void exportDone(JComponent c, Transferable data, int action) {
        if (action != MOVE) return;
        JTable list = (JTable) c;
        DefaultTableModel model = (DefaultTableModel) list.getModel();
        int index = list.getSelectedRow();
        model.removeRow(index);
    }

    /**
     * We only support importing strings.
     */
    public boolean canImport(TransferSupport support) {
        return support.isDrop();
    }
}
