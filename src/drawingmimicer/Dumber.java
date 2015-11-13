package drawingmimicer;
import drawingmimicer.Utils.SmartPoint;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by Joona on 5.10.2015.
 */
public class Dumber {

    private double accuracy;
    private List<SmartPoint> pointBuffer;
    private int lCount = 0;

    public Dumber(double accuracy) {
        pointBuffer = new CopyOnWriteArrayList<>();
        this.accuracy = accuracy;
    }

    public void addPoint(SmartPoint p){
        pointBuffer.add(p);
    }

    public List<SmartPoint> getDumbLines(SmartPoint firstOffsetPoint){
        List<SmartPoint> points = new ArrayList<>();
        Optional<SmartPoint> nextLimitPoint = Optional.of(firstOffsetPoint);
        points.add(pointBuffer.get(0));
        do{
            points.add(nextLimitPoint.get());
            pointBuffer.removeAll(pointBuffer.subList(0, pointBuffer.indexOf(nextLimitPoint.get())));
            nextLimitPoint = getLimitPoint();
        }while(nextLimitPoint.isPresent());
        return points;
    }

    public List<SmartPoint> getDumbLinesWithForcedEnding(){
        List<SmartPoint> points = new ArrayList<>();
        if (!pointBuffer.isEmpty()) {
            Optional<SmartPoint> firstOffset = getLimitPoint();
            if(firstOffset.isPresent())
                points.addAll(getDumbLines(firstOffset.get()));
            else
                points.add(pointBuffer.get(0));
            if(!pointBuffer.isEmpty())
                points.add(pointBuffer.get(pointBuffer.size()-1));
            pointBuffer.clear();
        }
        return points;
    }

    public Optional<SmartPoint> getLimitPoint(){
        Optional<SmartPoint> limitPoint = Optional.empty();
        if (pointBuffer.size() > 2) {
            Point startPoint = pointBuffer.get(0);
            for(int i = 2; i<pointBuffer.size() && !limitPoint.isPresent();i++){
                for (int j = 1; j <i && !limitPoint.isPresent(); j++) {
                    double distance = Math.abs(pointBuffer.get(j).distanceFromLine(startPoint, pointBuffer.get(i)));
                    if(distance>accuracy) {
                        limitPoint = Optional.of(pointBuffer.get(i - 1));
                    }
                }
            }
        }
        return limitPoint;
    }

    /*public double calculatePointOffsetFromLine(Point from, Point to, Point between){
        if(to.equals(between) || to.equals(from)) {
            return 0;
        }
        else{
            double startToEndDeltaX = to.getX()-from.getX();
            double startToEndDeltaY = to.getY()-from.getY();
            double startToEndAngle = Math.atan(startToEndDeltaX / startToEndDeltaY);
            double startToBetweenDeltaX = between.getX() - from.getX();
            double startToBetweenDeltaY = between.getY() - from.getY();
            double startBetweenAngle = Math.atan(startToBetweenDeltaX / startToBetweenDeltaY); //TODO swap params?
            double offsetAngle = startToEndAngle-startBetweenAngle;
            double startToBetweenDistance = Math.sqrt(Math.pow(startToBetweenDeltaX,2) + Math.pow(startToBetweenDeltaY,2));
            double distanceFromLine = Math.sin(offsetAngle)*startToBetweenDistance;

            return distanceFromLine;
        }
    }

    public double loopOffset(Point from, Point to, Point between){
        double startEnd = from.distance(to);
        double startBetween = from.distance(between);
        if(startEnd<startBetween){
            return between.distance(to);
        }
        else{
            return 0;
        }
    }*/

    /*//List<SmartPoint> points = getDumbLines();
    synchronized(pointBuffer){
        if(pointBuffer.size()>0){
            if(points.size()==0)
                points.add(pointBuffer.get(0));
            points.add(pointBuffer.get(pointBuffer.size()-1));
            pointBuffer.clear();
        }
    }
    return points;*/
}
