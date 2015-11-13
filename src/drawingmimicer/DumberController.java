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

    public void addPoint(SmartPoint p){
        if(!drawing)
            setDrawState(true);
        System.out.println(p);
        dumber.addPoint(p);
    }

    public void pauseUpdating(){
        drawing = false;
    }

    private synchronized void setDrawState(boolean drawing) {
        this.drawing = drawing;
        notifyAll();
    }

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

    private void finalizeStrokeEnd(){
        List<SmartPoint> dumbLines = dumber.getDumbLinesWithForcedEnding();
        controller.onDumbLinesUpdate(dumbLines, true);
    }

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