package com.example.todo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;
import com.example.todo.model.TodoRowMapper;
import com.example.todo.model.Todo;
import com.example.todo.repository.TodoRepository;

@Service
public class TodoH2Service implements TodoRepository {

  @Autowired
  private JdbcTemplate db;

  @Override
  public ArrayList<Todo> getTodos() {
    List<Todo> todolist = db.query("select * from TODOLIST", new TodoRowMapper());
    ArrayList<Todo> todos = new ArrayList<>(todolist);
    return todos;
  }

  @Override
  public Todo getTodoById(int id) {
    try {
      Todo todo = db.queryForObject("Select * from TODOLIST where id = ?", new TodoRowMapper(), id);
      return todo;
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public Todo addTodo(Todo todo) {
    db.update("insert into TODOLIST(todo, status, priority) values(?, ?, ?)", todo.getTodo(), todo.getStatus(),
        todo.getPriority());
    Todo savedTodo = db.queryForObject("select * from TODOLIST where todo = ?", new TodoRowMapper(),
        todo.getTodo());
    return savedTodo;
  }

  @Override
  public Todo updateTodo(int id, Todo todo) {
    try {
      if (todo.getTodo() != null) {
        db.update("update TODOLIST set todo = ? where id = ?", todo.getTodo(), id);
      }
      if (todo.getStatus() != null) {
        db.update("update TODOLIST set status= ? where id = ?", todo.getStatus(), id);
      }
      if (todo.getPriority() != null) {
        db.update("update TODOLIST set priority= ? where id = ?", todo.getPriority(), id);
      }
      return getTodoById(id);
    } catch (Exception e) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
  }

  @Override
  public void deleteTodo(int id) {
    db.update("delete from TODOLIST where id = ?", id);
  }

}