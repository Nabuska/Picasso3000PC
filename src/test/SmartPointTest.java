package test;

import drawingmimicer.Utils.SmartPoint;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by WinNabuska on 1.11.2015.
 */
public class SmartPointTest extends TestCase{

    SmartPoint black_start;
    SmartPoint black_end;
    SmartPoint black_between;

    SmartPoint red_start;
    SmartPoint red_end;
    SmartPoint red_between;

    SmartPoint blue_start;
    SmartPoint blue_end;
    SmartPoint blue_between;

    SmartPoint green_start;
    SmartPoint green_end;
    SmartPoint green_between;

    SmartPoint purple_start;
    SmartPoint purple_end;
    SmartPoint purple_between;

    @Before
    public void setUp() {
        black_start = new SmartPoint(1,7);
        black_end = new SmartPoint(5,1);
        black_between = new SmartPoint(4,7);

        red_start = new SmartPoint(6,4);
        red_end = new SmartPoint(6,8);
        red_between = new SmartPoint(7,8);

        blue_start = new SmartPoint(8,6);
        blue_end = new SmartPoint(11,6);
        blue_between = new SmartPoint(9,4);

        green_start = new SmartPoint(10,1);
        green_end = new SmartPoint(12,5);
        green_between = new SmartPoint(10,5);

        purple_start = new SmartPoint(7,4);
        purple_end = new SmartPoint(8,2);
        purple_between = new SmartPoint(7,3);
    }

    public void testOffset_Offset1(){

        SmartPoint p1S = new SmartPoint(1,2);
        SmartPoint p1B = new SmartPoint(1,3);
        SmartPoint p1E = new SmartPoint(1,4);

        assertEquals(1,p1B.distanceFromLine(p1S, p1S), 0.0001);
        assertEquals(1,p1B.distanceFromLine(p1E, p1E), 0.0001);
    }

    @Test
    public void testOffset_ZeroOffset(){
        SmartPoint p1S = new SmartPoint(1,2);
        SmartPoint p1B = new SmartPoint(1,3);
        SmartPoint p1E = new SmartPoint(1,4);

        assertEquals(0,p1B.distanceFromLine(p1S, p1B), 0.0001);

        assertEquals(0,p1B.distanceFromLine(p1S, p1E), 0.0001);
        assertEquals(0,p1B.distanceFromLine(p1E, p1S), 0.0001);
        SmartPoint p2S = new SmartPoint(1, 0);
        SmartPoint p2B = new SmartPoint(3, 0);
        SmartPoint p2E = new SmartPoint(9, 0);
        assertEquals(0,p2B.distanceFromLine(p2S, p2E), 0.0001);
        assertEquals(0,p2B.distanceFromLine(p2E, p2S), 0.0001);
        SmartPoint p3S = new SmartPoint(100, 11);
        SmartPoint p3B = new SmartPoint(4, 11);
        SmartPoint p3E = new SmartPoint(1, 11);
        assertEquals(0,p3B.distanceFromLine(p3S, p3E), 0.0001);
        assertEquals(0,p3B.distanceFromLine(p3E, p3S), 0.0001);
        SmartPoint p4S = new SmartPoint(1, 2);
        SmartPoint p4B = new SmartPoint(2, 3);
        SmartPoint p4E = new SmartPoint(4, 5);
        assertEquals(0,p4B.distanceFromLine(p4S, p4E), 0.0001);
        assertEquals(0,p4B.distanceFromLine(p4E, p4S), 0.0001);
        SmartPoint p5S = new SmartPoint(100, 105);
        SmartPoint p5B = new SmartPoint(105, 104);
        SmartPoint p5E = new SmartPoint(110, 103);
        assertEquals(0,p5B.distanceFromLine(p5S, p5E), 0.0001);
        assertEquals(0,p5B.distanceFromLine(p5E, p5S), 0.0001);
    }

    @Test
    public void testOffset_MirrorOffsets(){
        SmartPoint p1S = new SmartPoint(1,2);
        SmartPoint p1B = new SmartPoint(3,100);
        SmartPoint p1E = new SmartPoint(1,4);
        assertEquals(p1B.distanceFromLine(p1E, p1S),p1B.distanceFromLine(p1S, p1E), 0.0001);
        SmartPoint p2S = new SmartPoint(1, 0);
        SmartPoint p2B = new SmartPoint(3, 4);
        SmartPoint p2E = new SmartPoint(9, 0);
        assertEquals(p2B.distanceFromLine(p2E, p2S), p2B.distanceFromLine(p2S, p2E), 0.0001);
        SmartPoint p3S = new SmartPoint(100, 11);
        SmartPoint p3B = new SmartPoint(14, 300);
        SmartPoint p3E = new SmartPoint(1, 11);
        assertEquals(p3B.distanceFromLine(p3E, p3S), p3B.distanceFromLine(p3S, p3E), 0.0001);
        SmartPoint p4S = new SmartPoint(1, 2);
        SmartPoint p4B = new SmartPoint(24, 14);
        SmartPoint p4E = new SmartPoint(4, 5);
        assertEquals(p4B.distanceFromLine(p4E, p4S), p4B.distanceFromLine(p4S, p4E), 0.0001);
        SmartPoint p5S = new SmartPoint(100, 105);
        SmartPoint p5B = new SmartPoint(77, 2);
        SmartPoint p5E = new SmartPoint(110, 103);
        assertEquals(p5B.distanceFromLine(p5E, p5S), p5B.distanceFromLine(p5S, p5E), 0.0001);
    }

    @Test
    public void testDistanceFromLine_NonPerpendicular (){
        black_between = new SmartPoint(2,8);
        assertEquals(Math.sqrt(2), black_between.distanceFromLine(black_start, black_end),0.001);

        red_between = new SmartPoint(7,9);
        assertEquals(Math.sqrt(2), red_between.distanceFromLine(red_start, red_end), 0.001);

        blue_between = new SmartPoint(12,6);
        assertEquals(1, blue_between.distanceFromLine(blue_start, blue_end), 0.001);

        green_between = new SmartPoint(10,0);
        assertEquals(1, green_between.distanceFromLine(green_start, green_end), 0.001);

        purple_between = new SmartPoint(7,1);
        assertEquals(Math.sqrt(2), purple_between.distanceFromLine(purple_start, purple_end), 0.001);

        //SWAP
        swapEndAndStartPoints();
        assertEquals(Math.sqrt(2), black_between.distanceFromLine(black_start, black_end), 0.001);

        assertEquals(Math.sqrt(2), red_between.distanceFromLine(red_start, red_end), 0.001);

        assertEquals(1, blue_between.distanceFromLine(blue_start, blue_end), 0.001);

        assertEquals(1, green_between.distanceFromLine(green_start, green_end), 0.001);

        assertEquals(Math.sqrt(2), purple_between.distanceFromLine(purple_start, purple_end), 0.001);
    }

    /*@Test
    public void testDeviationIsPerpendicular_True() throws NoSuchMethodException {
        *//*Class myPoint = black_between.getClass();
        Method method;
        method = myPoint.getMethod("devitioationIsPerpendicular", Point.class, Point.class);
        method.setAccessible(true);*//*

        assertTrue(black_between.deviationIsPerpendicular2(black_start, black_end));

        assertTrue(red_between.deviationIsPerpendicular2(red_start, red_end));

        assertTrue(blue_between.deviationIsPerpendicular2(blue_start, blue_end));

        assertTrue(green_between.deviationIsPerpendicular2(green_start, green_end));

        assertTrue(purple_between.deviationIsPerpendicular2(purple_start, purple_end));

        //SWAP
        swapEndAndStartPoints();
        assertTrue(black_between.deviationIsPerpendicular2(black_start, black_end));

        assertTrue(red_between.deviationIsPerpendicular2(red_start, red_end));

        assertTrue(blue_between.deviationIsPerpendicular2(blue_start, blue_end));

        assertTrue(green_between.deviationIsPerpendicular2(green_start, green_end));

        assertTrue(purple_between.deviationIsPerpendicular2(purple_start, purple_end));
    }

    @Test
    public void testDeviationIsPerpendicular_False(){
        black_between = new SmartPoint(2,8);
        assertFalse(black_between.deviationIsPerpendicular2(black_start, black_end));

        red_between = new SmartPoint(7,9);
        assertFalse(red_between.deviationIsPerpendicular2(red_start, red_end));

        blue_between = new SmartPoint(12,6);
        assertFalse(blue_between.deviationIsPerpendicular2(blue_start, blue_end));

        green_between = new SmartPoint(10,0);
        assertFalse(green_between.deviationIsPerpendicular2(green_start, green_end));

        purple_between = new SmartPoint(7,1);
        assertFalse(purple_between.deviationIsPerpendicular2(purple_start, purple_end));

        swapEndAndStartPoints();
        assertFalse(black_between.deviationIsPerpendicular2(black_start, black_end));

        assertFalse(red_between.deviationIsPerpendicular2(red_start, red_end));

        assertFalse(blue_between.deviationIsPerpendicular2(blue_start, blue_end));

        assertFalse(green_between.deviationIsPerpendicular2(green_start, green_end));

        assertFalse(purple_between.deviationIsPerpendicular2(purple_start, purple_end));
    }*/

    private void swapEndAndStartPoints(){

        HashMap<SmartPoint, SmartPoint> ps = new HashMap<>();
        ps.put(red_start, red_end);
        ps.put(black_start, black_end);
        ps.put(blue_start, blue_end);
        ps.put(green_start, green_end);
        ps.put(purple_start, purple_end);
        for(Map.Entry<SmartPoint, SmartPoint> e : ps.entrySet()){
            SmartPoint placeHolder = new SmartPoint(e.getKey().x, e.getKey().y);
            e.getKey().setLocation(e.getValue());
            e.getValue().setLocation(placeHolder);
        }

    }
}
