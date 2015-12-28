package drawingmimicer;

import drawingmimicer.Utils.SmartPoint;

import java.util.List;
import java.util.Optional;

/**
 * Created by Joona on 14.10.2015.
 */
public class DumberController extends Thread {

    private volatile boolean drawing = false;
    private Dumber dumber;
    private DrawController controller;

    public DumberController(final int ACCURACY, DrawController controller) {
        this.controller = controller;
        dumber = new Dumber(ACCURACY);
    }

    @Override
    public void run() {
        while(true) {
            refreshDumbLines();
            waitForWakeup();
        }
    }

    /**I called every time when mouse is dragged on the screen*/
    public void addPoint(SmartPoint p){
        if(!drawing)
            notifyWaitingThread();
        dumber.addPoint(p);
    }


    /**is called when mouse exits the screen or mouse button is released*/
    public void pauseUpdating(){
        drawing = false;
    }


    private synchronized void notifyWaitingThread() {
        this.drawing = true;
        notifyAll();
    }

    /**refreshDumbLines is called 10 times / sec when boolean value 'drawing' is true*/
    private void refreshDumbLines() {
        Optional<SmartPoint> limitPoint = Optional.empty();
        while (drawing) {
            limitPoint = dumber.getLimitPoint();
            if(limitPoint.isPresent())
                controller.onDumbLinesUpdate(dumber.getDumbLines(limitPoint.get()), false);
            try { sleep(100); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        finalizeStrokeEnd();
    }

    /**is called ones when value 'drawing' is set to false*/
    private void finalizeStrokeEnd(){
        List<SmartPoint> dumbLines = dumber.getDumbLinesWithForcedEnding();
        controller.onDumbLinesUpdate(dumbLines, true);
    }

    /**is called after boolean value 'drawing' is set to false and finilizeStrokeEnd method is performed*/
    private synchronized void waitForWakeup(){
        try {
            while (!drawing)
                wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notifyAll();
    }
}