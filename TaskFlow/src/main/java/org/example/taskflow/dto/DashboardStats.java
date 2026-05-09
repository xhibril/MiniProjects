package org.example.taskflow.dto;

public class DashboardStats {

    private int tasks;
    private int highPriority;

    private int active;
    private int dueToday;

    private int completed;
    private double completionPercentage;

    private double weeklyTrend;
    private int tasksCompletedThisWeek;


    public int getTasks() {
        return tasks;
    }

    public void setTasks(int tasks) {
        this.tasks = tasks;
    }


    public int getHighPriority() {
        return highPriority;
    }

    public void setHighPriority(int highPriority) {
        this.highPriority = highPriority;
    }


    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }


    public int getDueToday() {
        return dueToday;
    }

    public void setDueToday(int dueToday) {
        this.dueToday = dueToday;
    }


    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }


    public double getCompletionPercentage() {
        return completionPercentage;
    }

    public void setCompletionPercentage(double completionPercentage) {
        this.completionPercentage = completionPercentage;
    }


    public double getWeeklyTrend() {
        return weeklyTrend;
    }

    public void setWeeklyTrend(double weeklyTrend) {
        this.weeklyTrend = weeklyTrend;
    }


    public int getTasksCompletedThisWeek() {
        return tasksCompletedThisWeek;
    }

    public void setTasksCompletedThisWeek(int tasksCompletedThisWeek) {
        this.tasksCompletedThisWeek = tasksCompletedThisWeek;
    }
}