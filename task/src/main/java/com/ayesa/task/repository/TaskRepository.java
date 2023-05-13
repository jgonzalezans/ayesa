package com.ayesa.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayesa.task.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

}