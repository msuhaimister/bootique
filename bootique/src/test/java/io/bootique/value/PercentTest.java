package io.bootique.value;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class PercentTest {

    @Test
    public void testEquals() {
        Percent p1 = new Percent(1);
        Percent p2 = new Percent(2);
        Percent p3 = new Percent(1);
        Percent p4 = new Percent(1.000);


        assertTrue(p1.equals(p1));
        assertFalse(p1.equals(null));
        assertFalse(p1.equals(p2));
        assertTrue(p1.equals(p3));
        assertTrue(p1.equals(p4));
        assertTrue(p4.equals(p1));
    }

    @Test
    public void testHashCode() {
        Percent p1 = new Percent(1);
        Percent p2 = new Percent(2);
        Percent p3 = new Percent(1);
        Percent p4 = new Percent(1.000);
        Percent p5 = new Percent(1.00000001);


        assertEquals(p1.hashCode(), p1.hashCode());
        assertEquals(p1.hashCode(), p3.hashCode());
        assertEquals(p1.hashCode(), p4.hashCode());
        assertNotEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1.hashCode(), p5.hashCode());
    }

    @Test
    public void testCompareTo() {
        Percent p1 = new Percent(1);
        Percent p2 = new Percent(2);
        Percent p4 = new Percent(1.000);

        assertTrue(p1.compareTo(p1) == 0);
        assertTrue(p1.compareTo(p2) < 0);
        assertTrue(p2.compareTo(p1) > 0);
        assertTrue(p1.compareTo(p4) == 0);

    }

    @Test(expected = NullPointerException.class)
    public void testParse_Null() {
        Percent.parse(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testParse_Empty() {
        Percent.parse("");
    }

    @Test(expected = NumberFormatException.class)
    public void testParse_NotANumber() {
        Percent.parse("abc%");
    }

    @Test
    public void testParse() {
        assertEquals(4., Percent.parse("4"), 0.0001);
        assertEquals(4., Percent.parse("4."), 0.0001);
        assertEquals(4., Percent.parse("4%"), 0.0001);
        assertEquals(4., Percent.parse("4.0%"), 0.0001);
    }

    @Test
    public void testParse_Negative() {
        assertEquals(-4., Percent.parse("-4"), 0.0001);
        assertEquals(-4., Percent.parse("-4%"), 0.0001);
    }

    @Test
    public void testParse_Zero() {
        assertEquals(0., Percent.parse("0"), 0.0001);
        assertEquals(0., Percent.parse("0.0%"), 0.0001);
        assertEquals(0., Percent.parse("-0.%"), 0.0001);
    }

    @Test
    public void testParse_Hundred() {
        assertEquals(100., Percent.parse("100"), 0.0001);
        assertEquals(100., Percent.parse("100.0%"), 0.0001);
    }

    @Test
    public void testParse_Large() {
        assertEquals(10001.0005, Percent.parse("10001.0005"), 0.000000001);
    }

    @Test
    public void testToString_Precision() {
        assertEquals("0.000124%", new Percent("0.0001237%").toString(3));
        assertEquals("1.01%", new Percent("1.0111111%").toString(3));
        assertEquals("1.012%", new Percent("1.011811%").toString(4));
        assertEquals("1.0110000%", new Percent("1.011%").toString(8));
        assertEquals("100.1%", new Percent("100.09%").toString(4));
    }
}
