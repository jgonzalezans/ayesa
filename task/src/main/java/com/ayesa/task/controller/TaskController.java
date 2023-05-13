package com.ayesa.task.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayesa.task.model.Task;
import com.ayesa.task.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	private final TaskService taskService;

	@Autowired
	public TaskController(TaskService taskService) {
		this.taskService = taskService;
	}

	@GetMapping
	public List<Task> getAllTasks() {
		return taskService.getAllTasks();
	}

	@PostMapping
	public Task createTask(@RequestBody TaskRequest taskRequest) {
		Task task = new Task();
		task.setName(taskRequest.getName());
		task.setDescription(taskRequest.getDescription());
		task.setStatus("Pending");
		return taskService.createTask(task);
	}

	@PutMapping("/{id}")
	public Task updateTaskStatus(@PathVariable Long id, @RequestBody String status) {
		return taskService.updateTaskStatus(id, status);
	}
}