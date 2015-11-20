package drawingmimicer;


import drawingmimicer.Utils.SmartPoint;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;


/**
 * on 5.10.2015.
 */
public class DrawController {

    private ViewFrame viewFrame;
    private DumberController dumberController;
    private OutputStreamManager output;
    private Predicate<Point> insideFrame = p -> 0<=p.x && p.x<ViewFrame.PANEL_WIDTH && 0<=p.y && p.y<ViewFrame.PANEL_HEIGHT;

    private List<Point> latestDumbStroke;

    public static void main(String[] args) {
        new DrawController();
    }

    public DrawController(){
        latestDumbStroke = new ArrayList<>();
        output = new OutputStreamManager();
        this.viewFrame = new ViewFrame(this);
        this.dumberController = new DumberController(5, this);
        dumberController.start();
    }

    public void onMouseDragged(MouseEvent e) {
        if(insideFrame.test(e.getPoint())) {
            dumberController.addPoint(new SmartPoint(e.getPoint()));
            viewFrame.addPoint(e.getPoint());
        }
    }

    public void onMouseReleased() {
        dumberController.pauseUpdating();
    }

    public void onDumbLinesUpdate(List<SmartPoint> dumbLines, boolean endOfStroke) {
        viewFrame.addDumbLines(dumbLines);
        latestDumbStroke.addAll(dumbLines);
        System.out.println("Stroke end" + endOfStroke);
        if(endOfStroke && !latestDumbStroke.isEmpty()){
            System.out.println("Stroke end" + endOfStroke);
            output.sendDumbStroke(latestDumbStroke);
            viewFrame.prepareForNextStroke();
            latestDumbStroke.clear(); //TODO test if latestDumpStrokes has been send before clear;
        }
    }

    public void onMouseExit() {
        System.out.println("Mouse exit");
        dumberController.pauseUpdating();
    }

    public void onCalibrateButtonClick() {
        System.out.println("calibrate");
        output.sendCalibrateCommand();
    }

    public void onCalibrateDoneClick() {
        System.out.println("all done");
        output.sendCalibrateDoneCommand();
    }

    public void onExitStageCommandClick() {
        System.out.println("exit stage");
        output.sendExitStageCommand();
    }
}
