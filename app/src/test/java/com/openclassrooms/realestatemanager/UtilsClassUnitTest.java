package com.openclassrooms.realestatemanager;

import com.openclassrooms.realestatemanager.utils.Utils;

import org.junit.Test;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UtilsClassUnitTest {
    @Test
    public void convertEurosInDollars(){
        int dollars = Utils.convertEuroDollar(25876);

        assertEquals(30741, dollars);
    }

    @Test
    public void convertDollarsToEuros(){
        int euros = Utils.convertDollarToEuro(30741);

        assertEquals(24962, euros);
    }

    @Test
    public void checkDateFormat(){
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, Locale.FRANCE);
        String date = dateFormat.format(new Date());

        String dateToday = Utils.getTodayDate();

        assertEquals(date, dateToday);
    }
}