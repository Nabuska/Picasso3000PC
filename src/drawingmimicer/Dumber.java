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

    /**Dumber produces a dumbed down point data of the original drawing double value 'accuracy' determines the max
     * offset compared to the original drawing*/

    private double accuracy;
    private List<SmartPoint> pointBuffer;

    public Dumber(double accuracy) {
        pointBuffer = new CopyOnWriteArrayList<>();
        this.accuracy = accuracy;
    }

    public void addPoint(SmartPoint p){
        pointBuffer.add(p);
    }

    public void setAccuracy(int accuracy){
        this.accuracy = accuracy;
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
}
