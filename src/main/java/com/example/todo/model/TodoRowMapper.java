package com.example.todo.model;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.todo.model.Todo;
import java.util.*;

//import javax.swing.tree.RowMapper;

public class TodoRowMapper implements RowMapper<Todo> {
    @Overridee
    public Todo mapRow(ResultSet rs, int num) throws SQLException {
        
        return new Todo(
                rs.getInt("id"),
                rs.getString("todo"),
                rs.getString("status"),
                rs.getString("priority"));
    }
}