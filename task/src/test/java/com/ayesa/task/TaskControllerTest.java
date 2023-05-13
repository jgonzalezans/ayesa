package com.ayesa.task;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ayesa.task.controller.TaskController;
import com.ayesa.task.model.Task;
import com.ayesa.task.service.TaskService;

class TaskControllerTest {

	private MockMvc mockMvc;

	@Mock
	private TaskService taskService;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		TaskController taskController = new TaskController(taskService);
		mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
	}

	@Test
	void getAllTasks_ShouldReturnListOfTasks() throws Exception {
		// Arrange
		Task task1 = new Task(1L, "Task 1", "Description 1", "Pending");
		Task task2 = new Task(2L, "Task 2", "Description 2", "Completed");
		List<Task> tasks = Arrays.asList(task1, task2);

		when(taskService.getAllTasks()).thenReturn(tasks);

		// Act & Assert
		mockMvc.perform(MockMvcRequestBuilders.get("/tasks")).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id").value(1L)).andExpect(jsonPath("$[0].name").value("Task 1"))
				.andExpect(jsonPath("$[0].description").value("Description 1"))
				.andExpect(jsonPath("$[0].status").value("Pending")).andExpect(jsonPath("$[1].id").value(2L))
				.andExpect(jsonPath("$[1].name").value("Task 2"))
				.andExpect(jsonPath("$[1].description").value("Description 2"))
				.andExpect(jsonPath("$[1].status").value("Completed"));
	}

	@Test
	void createTask_ShouldReturnCreatedTask() throws Exception {
		// Arrange
		Task task = new Task(1L, "Task 1", "Description 1", "Pending");
		when(taskService.createTask(any(Task.class))).thenReturn(task);

		// Act & Assert
		mockMvc.perform(MockMvcRequestBuilders.post("/tasks").contentType(MediaType.APPLICATION_JSON).content(
				"{\"id\": 1, \"name\": \"Task 1\", \"description\": \"Description 1\", \"status\": \"Pending\"}"))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.name").value("Task 1"))
				.andExpect(jsonPath("$.description").value("Description 1"))
				.andExpect(jsonPath("$.status").value("Pending"));
	}

	@Test
	void updateTask_ShouldReturnUpdatedTask() throws Exception {
		// Arrange
		Task task = new Task(1L, "Task 1", "Description 1", "Pending");
		when(taskService.updateTaskStatus(any(Long.class), any(String.class))).thenReturn(task);

		// Act & Assert
		mockMvc.perform(MockMvcRequestBuilders.put("/tasks/{id}", 1L).contentType(MediaType.APPLICATION_JSON)
				.content("{\"status\": \"Completed\"}")).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(1L)).andExpect(jsonPath("$.name").value("Task 1"))
				.andExpect(jsonPath("$.description").value("Description 1"))
				.andExpect(jsonPath("$.status").value("Pending"));
	}
	
}