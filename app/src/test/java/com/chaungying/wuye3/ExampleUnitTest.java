package com.chaungying.wuye3;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        Date date = new Date();
        date.setTime(1476408724000L);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.print(sdf.format(date));
    }
}