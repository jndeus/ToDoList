package com.uniavan.todolist.database;

import com.uniavan.todolist.model.Task;
import com.uniavan.todolist.model.TaskStatus;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DBFile {
    private static final String DBName = "mydb.csv";

    public static List<Task> readTasks() {
        try {
            List<Task> toDoList = new ArrayList<>();

            File dbFile = new File(DBName);
            Scanner reader = new Scanner(dbFile);

            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                var splitLines = data.split(";");
                var task = new Task();

                task.setPriority(Integer.parseInt(splitLines[0]));
                task.setId(Integer.parseInt(splitLines[1]));
                task.setStatus(TaskStatus.getEnum(splitLines[2]));
                task.setDescription(splitLines[3]);

                toDoList.add(task);
            }
            reader.close();
            return toDoList;
        } catch (FileNotFoundException exception) {
            return new ArrayList<>();
        }
    }

    public static void writeTasks(List<Task> tasks) {
        try {
            FileWriter writer = new FileWriter(DBName);
            for (Task task : tasks) {
                writer.write(String.format("%d;%d;%s;%s%n", task.getPriority(), task.getId(), task.getStatus(), task.getDescription()));
            }
            writer.close();
        } catch (IOException ex) {
            System.out.println("Erro ao salvar o arquivo do banco de dados.");
            System.out.println(ex.getMessage());
        }
    }
}
