package com.uniavan.todolist.model;

public enum TaskStatus {
    PENDING("Pendente"),
    COMPLETED("Completo");

    private final String description;
    TaskStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString () {
        return this.getDescription();
    }

    public static TaskStatus getEnum(String value) {
        if (value == null)
            throw new IllegalArgumentException ();

        for (TaskStatus v : values())
            if (value.equalsIgnoreCase (v.getDescription()))
                return v;

        throw new IllegalArgumentException();
    }
}
