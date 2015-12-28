package test;

import drawingmimicer.Dumber;
import drawingmimicer.Utils.SmartPoint;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by WinNabuska on 27.10.2015.
 */
public class DumberTest {

    Dumber dumber;
    SmartPoint point1;
    SmartPoint point2;
    SmartPoint point3;
    SmartPoint point4;
    java.util.List<SmartPoint> partialStroke1;
    java.util.List<SmartPoint> partialStroke2;

    public DumberTest() {
    }

    @Before
    public void setUp() {
        dumber = new Dumber(7);
        point1 = new SmartPoint(1,2);
        point2 = new SmartPoint(5,3);
        point3 = new SmartPoint(100, 20);
        point4 = new SmartPoint(1,1);
        partialStroke1 = Arrays.asList(point1, point2);
        partialStroke2 = Arrays.asList(point1, point2, point3, point4);
    }

    /**
     * Test of savePartialStroke method, of class Dumber.
     */
        /*@Test
        public void testSavePartialStroke() {
            int bufferSize = dumber.savePartialStroke(partialStroke1);
            assertEquals(bufferSize, partialStroke1.size());
            bufferSize = dumber.savePartialStroke(partialStroke2);
            assertEquals(bufferSize, (partialStroke1.size()+partialStroke2.size()));
        }*/

    /**
     * Test of getDumbLines method, of class Dumber.
     */
    /*@Test
    public void testGetMimicLines() {
        partialStroke1.forEach(p -> dumber.addPoint(p));
        assertTrue(dumber.getDumbLines().size()==0);
        partialStroke2.forEach(p -> dumber.addPoint(p));
        java.util.List<SmartPoint> mimicedLines = dumber.getDumbLines();
        assertEquals(2, mimicedLines.size());
        assertEquals(mimicedLines.get(0), point1);
        assertEquals(mimicedLines.get(1), point3);
    }
*/
    /**
     * Test of getDumbLinesWithForcedEnding method, of class Dumber.
     */
    @Test
    public void testGetMimicLinesWithForcedEnding() {
        java.util.List<SmartPoint> points;
        partialStroke1.forEach(p -> dumber.addPoint(p));
        points = dumber.getDumbLinesWithForcedEnding();
        assertEquals(points.get(0), partialStroke1.get(0));
        assertEquals(points.get(points.size()-1), partialStroke1.get(partialStroke1.size()-1));

        partialStroke2.forEach(p -> dumber.addPoint(p));
        points = dumber.getDumbLinesWithForcedEnding();
        assertEquals(points.get(0), partialStroke2.get(0));
        assertEquals(points.get(points.size()-1), partialStroke2.get(partialStroke2.size()-1));

        partialStroke1.forEach(p -> dumber.addPoint(p));
        partialStroke2.forEach(p -> dumber.addPoint(p));
        points.forEach(p -> dumber.addPoint(p));
        points = dumber.getDumbLinesWithForcedEnding();
        assertEquals(points.get(0), partialStroke1.get(0));
        assertEquals(points.get(points.size()-1), partialStroke2.get(partialStroke2.size()-1));
    }
}