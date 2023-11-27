package com.uniavan.todolist.utils;

import java.awt.datatransfer.*;

// Classe que implementa a interface Transferable para um array de objetos
public class ObjectArrayTransferable implements Transferable {
    // Constante que define o tipo de dado transferível
    public static final DataFlavor OBJECT_ARRAY_FLAVOR = new DataFlavor(Object[].class, "Array of Objects");

    // Atributo que armazena o array de objetos
    private Object[] data;

    // Construtor que recebe o array de objetos
    public ObjectArrayTransferable(Object[] data) {
        this.data = data;
    }

    // Implementação da interface Transferable
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        if (flavor.equals(OBJECT_ARRAY_FLAVOR)) {
            return data;
        } else {
            throw new UnsupportedFlavorException(flavor);
        }
    }

    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] { OBJECT_ARRAY_FLAVOR };
    }

    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return flavor.equals(OBJECT_ARRAY_FLAVOR);
    }
}
