package com.example.tasks;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class TaskBuilderTest {

    @Test
    public void messageTest() {
        Task task = new TaskBuilder().message("Hello World").build();
        assertEquals("Hello World", task.getMessage());
    }

    @Test
    public void completedTest() {
        Task task = new TaskBuilder().completed(true).build();
        assertTrue(task.isCompleted());
    }
}
