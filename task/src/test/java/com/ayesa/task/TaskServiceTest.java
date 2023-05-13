package com.ayesa.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ayesa.task.model.Task;
import com.ayesa.task.repository.TaskRepository;
import com.ayesa.task.service.TaskService;

class TaskServiceTest {

	private TaskService taskService;

	@Mock
	private TaskRepository taskRepository;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		taskService = new TaskService(taskRepository);
	}

	@Test
	void getAllTasks_ShouldReturnListOfTasks() {
		// Arrange

		Task task1 = new Task(1L, "Task 1", "Description 1", "Pending");
		Task task2 = new Task(2L, "Task 2", "Description 2", "Completed");
		List<Task> tasks = Arrays.asList(task1, task2);

		when(taskRepository.findAll()).thenReturn(tasks);

		// Act
		List<Task> result = taskService.getAllTasks();

		// Assert
		assertEquals(2, result.size());
		assertEquals(task1, result.get(0));
		assertEquals(task2, result.get(1));
	}

	@Test
	void createTask_ShouldReturnCreatedTask() {
		// Arrange
		Task task = new Task(1L, "Task 1", "Description 1", "Pending");
		when(taskRepository.save(any(Task.class))).thenReturn(task);

		// Act
		Task result = taskService.createTask(task);

		// Assert
		assertEquals(task, result);
	}

	@Test
	void updateTaskStatus_ShouldReturnUpdatedTask() {
		// Arrange
		Long taskId = 1L;
		Task task = new Task(taskId, "Task 1", "Description 1", "Pending");
		when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
		when(taskRepository.save(any(Task.class))).thenReturn(task);

		// Act
		Task result = taskService.updateTaskStatus(taskId, "Completed");

		// Assert
		assertEquals("Completed", result.getStatus());
	}
	
}