package com.example.tasks;

import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TaskComparatorTest {
    private static SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

    private Task task1;
    private Task task2;

    @Before
    public void setUp()
    throws ParseException {
        task1 = mock(Task.class);
        task2 = mock(Task.class);

        when(task1.isCompleted()).thenReturn(false);
        when(task2.isCompleted()).thenReturn(false);

        when(task1.getDueDate()).thenReturn(format.parse("01/01/2015"));
        when(task2.getDueDate()).thenReturn(format.parse("01/01/2015"));
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
        when(task2.getDueDate()).thenReturn(format.parse("12/31/2015"));
        int result = new TaskComparator().compare(task1, task2);
        assertEquals(-1, result);
    }

    @Test
    public void task2DueFirstTest()
    throws ParseException {
        when(task1.getDueDate()).thenReturn(format.parse("12/31/2015"));
        int result = new TaskComparator().compare(task1, task2);
        assertEquals(1, result);
    }

}
