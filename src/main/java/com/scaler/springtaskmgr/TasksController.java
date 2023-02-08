package com.scaler.springtaskmgr;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class TasksController {
    private final List<Task> taskList;
    private AtomicInteger taskId = new AtomicInteger(0);

    public TasksController() {
        taskList = new ArrayList<>();
        taskList.add(new Task(taskId.incrementAndGet(), "Task 1", "Description 1", new Date()));
        taskList.add(new Task(taskId.incrementAndGet(), "Task 2", "Description 2", new Date()));
        taskList.add(new Task(taskId.incrementAndGet(), "Task 3", "Description 3", new Date()));
    }

    /*
    ASSIGNMENT: Finish all the TODOS below
    You do not need to persist the tasks in a database. Just keep them in memory.
     */

    /**
     * Show all existing tasks
     * GET /tasks
     * @return List of tasks
     */
    @GetMapping("/tasks")
    List<Task> getTasks() {
        return taskList;
    }

    /**
     * Create a new task
     * POST /tasks
     * Body:
     *  <pre>
     *      {
     *          "title": "Task 4",
     *          "description": "Description 4",
     *          "dueDate": "2021-01-01"
     *      }
     *  </pre>
     * @param task Task object sent by client
     * @return Task object created
     */
    @PostMapping("/tasks")
    Task createTask(@RequestBody Task task) {
        var newTask = new Task(taskId.incrementAndGet(), task.getTitle(), task.getDescription(), task.getDueDate());
        taskList.add(newTask);
        return newTask;
    }

    /**
     * Get a task by id
     * @param id
     * @return Task object
     */
    @GetMapping("/tasks/{id}")
    ResponseEntity<?> getTask(@PathVariable("id") Integer id) {
        // TODO: Implement this method
        // TODO: BONUS: Return 404 if task not found
        for (Task task : taskList) {
            if (task.getId() == id) {
                return new ResponseEntity<>(task, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Task Id not found", HttpStatus.NOT_FOUND);

    }

    /**
     * Delete a task by given id
     * @param id Task id to delete
     * @return the deleted task
     */
    @DeleteMapping("/tasks/{id}")
    ResponseEntity<?> deleteTask(@PathVariable("id") Integer id) {
        // TODO: Implement this method
        // TODO: BONUS: Return 404 if task not found
            Iterator<Task> iterator= taskList.iterator();
            while(iterator.hasNext()){
                Task task=iterator.next();
                if(task.getId()==id){
                iterator.remove();
                return new ResponseEntity<>(task, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Task Id not found", HttpStatus.NOT_FOUND);
    }

    /**
     * Update a task by given id
     * @param id Task id to update
     * @param task Task object sent by client
     * @return the updated task
     */
    @PatchMapping("/tasks/{id}")
    ResponseEntity<?> updateTask(@PathVariable("id") Integer id, @RequestBody Task task) {
        // TODO: BONUS: Update the task with given id
        // Request body might have only title, description or dueDate (not necessarily all fields)
        Iterator<Task> iterator= taskList.iterator();
        int i=0;
        while(iterator.hasNext()){
            Task tasks=iterator.next();
            if(tasks.getId()==id){
                taskList.set(i,new Task(task.getId(),task.getTitle(),task.getDescription(),task.dueDate));
                return new ResponseEntity<>(task, HttpStatus.OK);
            }
            ++i;
        }
        return null;
    }
}
