package com.example.domain.tasks;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaskComparatorTest {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private Task task1;
    private Task task2;

    @Before
    public void setUp()
    throws ParseException {
        task1 = mock(Task.class);
        task2 = mock(Task.class);

        when(task1.isCompleted()).thenReturn(false);
        when(task2.isCompleted()).thenReturn(false);

        when(task1.getDueDate()).thenReturn(format.parse("2015-01-01"));
        when(task2.getDueDate()).thenReturn(format.parse("2015-01-01"));
    }

    @Test
    public void task1IncompleteTask2CompleteTest() {
        when(task2.isCompleted()).thenReturn(true);
        int result = new TaskComparator().compare(task1, task2);
        assertEquals(-1, result);
    }

    @Test
    public void task1CompleteTask2IncompleteTest() {
        when(task1.isCompleted()).thenReturn(true);
        int result = new TaskComparator().compare(task1, task2);
        assertEquals(1, result);
    }

    @Test
    public void task1DueFirstTest()
    throws ParseException {
        when(task2.getDueDate()).thenReturn(format.parse("2015-12-31"));
        int result = new TaskComparator().compare(task1, task2);
        assertEquals(-1, result);
    }

    @Test
    public void task2DueFirstTest()
    throws ParseException {
        when(task1.getDueDate()).thenReturn(format.parse("2015-12-31"));
        int result = new TaskComparator().compare(task1, task2);
        assertEquals(1, result);
    }

    @Test
    public void taskSortingTest()
    throws ParseException {
        Task task3 = mock(Task.class);
        when(task1.isCompleted()).thenReturn(true);
        when(task1.getDueDate()).thenReturn(format.parse("2015-08-20"));
        when(task2.getDueDate()).thenReturn(format.parse("2015-08-27"));
        when(task3.isCompleted()).thenReturn(true);
        when(task3.getDueDate()).thenReturn(format.parse("2015-08-28"));

        List<Task> taskList = new ArrayList<Task>();
        taskList.add(task1);
        taskList.add(task2);
        taskList.add(task3);

        Collections.sort(taskList, new TaskComparator());
        assertEquals(3, taskList.size());
        Task tmp = taskList.get(0);
        assertEquals(task2, tmp);

        tmp = taskList.get(1);
        assertEquals(task1, tmp);

        tmp = taskList.get(2);
        assertEquals(task3, tmp);
    }

}
