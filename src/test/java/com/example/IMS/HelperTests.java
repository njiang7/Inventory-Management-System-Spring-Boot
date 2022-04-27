package com.example.IMS;
import com.example.IMS.Utilities.Helper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;


public class HelperTests {

    @Test
    public void convertStringToDateTest() {
        String s = "2022/04/28 00:00:00";
        Date d = Helper.convertStringToDate(s);
        assertEquals(d.toString(), "Thu Apr 28 00:00:00 EDT 2022");
    }

    @Test
    public void convertStringToDateExceptionTest() {
        String s = "2022/04/28 00:00";
        Date d = Helper.convertStringToDate(s); // Exception stack get printed
        assertNull(d); // Null is returned
    }

    @Test
    public void getCurrentTimeTest() {
        String s = Helper.getCurrentTime();
        Calendar cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String current_time = dateFormat.format(cal.getTime());
        assertEquals(current_time, s);
    }

    @Test
    public void getDueDateTest() {
        String issue_date = "2022/04/28 00:00:00";
        long l = 1;
        String due_date = Helper.getDueDate(issue_date, l);
        String dd = "2022/04/29 00:00:00";
        assertEquals(dd, due_date);
    }

    @Test
    public void getDueDateExceptionTest() {
        String issue_date = "2022/04/28 00:00";
        long l = 0;
        String due_date = Helper.getDueDate(issue_date, l); // printStackTrace() should be invoked printing out error msg
        String s = Helper.getCurrentTime();
        assertEquals(s, due_date);
    }
}
