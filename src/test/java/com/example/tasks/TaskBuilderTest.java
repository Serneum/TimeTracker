package com.example.tasks;

import com.example.user.TaskUser;
import com.googlecode.objectify.Key;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class TaskBuilderTest {

    // Currently failing because of Key.create() not having an API environment on the thread
//    @Test
//    public void messageTest() {
//        Task task = new TaskBuilder().user("user").dueDate("01/01/2015").description("Hello World").build();
//        assertEquals("Hello World", task.getDescription());
//    }
//
//    @Test
//    public void completedTest() {
//        Task task = new TaskBuilder().user("user").dueDate("01/01/2015").completed(true).build();
//        assertTrue(task.isCompleted());
//    }
}
