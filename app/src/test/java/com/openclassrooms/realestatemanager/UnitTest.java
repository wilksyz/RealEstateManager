package com.openclassrooms.realestatemanager;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTest {
    @Test
    public void convertEurosInDollars(){
        int dollars = Utils.convertEuroDollar(25876);

        assertEquals(30741, dollars);
    }
}