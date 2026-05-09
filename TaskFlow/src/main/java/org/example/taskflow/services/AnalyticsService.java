package org.example.taskflow.services;
import org.example.taskflow.dto.DashboardStats;
import org.example.taskflow.dto.TaskLists;
import org.example.taskflow.model.Task;
import org.example.taskflow.utils.Session;
import java.time.LocalDate;
import java.util.List;

public class AnalyticsService {

    private final TaskService taskService = new TaskService();

    public DashboardStats getStats(){
        TaskLists taskLists = taskService.getTasks(Session.getUserId());

        List<Task> toDoTasks = taskLists.getTodo();
        List<Task> inProgressTasks = taskLists.getInProgress();

        Integer tasks = taskLists.getTodo().size();
        Integer highPrio = 0;

        Integer active = taskLists.getInProgress().size();
        Integer dueToday = 0;

        Integer totalTasks = taskLists.getTodo().size() + taskLists.getInProgress().size() + taskLists.getDone().size();

        Integer completed = taskLists.getDone().size();
        double completion = ((double) completed / totalTasks) * 100;

        for (Task task : toDoTasks) {
            if (task.getStatus().equals("TODO") && task.getPriority().equals("HIGH")) {
                highPrio++;
            }
        }

        for (Task task : inProgressTasks) {
            if (task.getStatus().equals("IN_PROGRESS") && task.getDueDate().equals(LocalDate.now().toString())) {
                dueToday++;
            }
        }

        DashboardStats stats = new DashboardStats();
        stats.setTasks(tasks);
        stats.setHighPriority(highPrio);
        stats.setActive(active);
        stats.setDueToday(dueToday);
        stats.setCompleted(completed);
        stats.setCompletionPercentage(completion);

        return stats;
    }


    public DashboardStats getTrend(){
        TaskLists taskLists = taskService.getTasks(Session.getUserId());

        List<Task> doneTasks = taskLists.getDone();

        LocalDate today = LocalDate.now();
        LocalDate startOfThisWeek = today.minusDays(6);

        LocalDate startOfLastWeek = today.minusDays(13);
        LocalDate endOfLastWeek = today.minusDays(7);

        int thisWeek = 0;
        int lastWeek = 0;

        for (Task task : doneTasks) {
            if (task.getCompletedAt() == null) {
                continue;
            }

            LocalDate completedDate = LocalDate.parse(task.getCompletedAt());
            if (completedDate.isAfter(startOfThisWeek.minusDays(1)) && completedDate.isBefore(today.plusDays(1))) {
                thisWeek++;
            }

            if (completedDate.isAfter(startOfLastWeek.minusDays(1)) && completedDate.isBefore(endOfLastWeek.plusDays(1))) {
                lastWeek++;
            }
        }

        double trend;
        if (lastWeek == 0) {
            trend = thisWeek * 100;
        } else {
            trend = ((double) (thisWeek - lastWeek) / lastWeek) * 100;
        }

        DashboardStats stats = new DashboardStats();
        stats.setTasksCompletedThisWeek(thisWeek);
        stats.setWeeklyTrend((int)trend);
        return stats;
    }
}
