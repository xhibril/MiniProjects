package org.example.taskflow.dto;

import org.example.taskflow.model.Task;
import java.util.List;

public class TaskLists {

    private List<Task> toDo;
    private List<Task> inProgress;
    private List<Task> done;

    public List<Task> getTodo() {
        return toDo;
    }

    public void setTodo(List<Task> toDo) {
        this.toDo = toDo;
    }

    public List<Task> getInProgress() {
        return inProgress;
    }

    public void setInProgress(List<Task> inProgress) {
        this.inProgress = inProgress;
    }

    public List<Task> getDone() {
        return done;
    }

    public void setDone(List<Task> done) {
        this.done = done;
    }
}