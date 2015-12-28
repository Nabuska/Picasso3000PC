package drawingmimicer.Utils;

import java.awt.Point;

/**
 * Created by WinNabuska on 1.11.2015.
 */
public class SmartPoint extends Point {

    public SmartPoint(int x, int y){
        this.x = x; this.y = y;
    }

    public SmartPoint(Point p){
        this.x = p.x; this.y = p.y;
    }


    /**returns 'this' points distance from a line that goes between lineStart and lineEnd*/
    public double distanceFromLine(Point lineStart, Point lineEnd){
        double distance;
        if(this.equals(lineStart) || this.equals(lineEnd))
            distance = 0;
        else if(lineStart.equals(lineEnd))
            distance = distanceToLineHead(lineStart, lineEnd);
        else {
            double startToEndAngle = Math.atan2(lineEnd.y - lineStart.y, lineEnd.x - lineStart.x);
            double startToThisAngle = Math.atan2(this.y - lineStart.y, this.x - lineStart.x);
            double endToStartAngle = Math.atan2(lineStart.y - lineEnd.y, lineStart.x - lineEnd.x);
            double endToThisAngle = Math.atan2(this.y - lineEnd.y, this.x - lineEnd.x);
            if (deviationIsZero(startToEndAngle, startToThisAngle, endToStartAngle, endToThisAngle))
                distance = 0;
            else if (!deviationIsPerpendicular(startToEndAngle, startToThisAngle, endToStartAngle, endToThisAngle))
                distance = distanceToLineHead(lineStart, lineEnd);
            else {
                double angleDifference = angleDifference(startToEndAngle, startToThisAngle);
                distance = Math.sin(angleDifference)*lineStart.distance(this);
            }
        }
        return distance;
    }

    private boolean deviationIsZero(double startToEndAngle, double startToThisAngle, double endToStartAngle, double endToThisAngle){
        return startToEndAngle == startToThisAngle && endToStartAngle == endToThisAngle;
    }

    private boolean deviationIsPerpendicular(double startToEndAngle, double startToThisAngle, double endToStartAngle, double endToThisAngle){
        boolean perpendicular;
        if(angleDifference(startToEndAngle,startToThisAngle)>Math.PI/2)
            perpendicular = false;
        else{
            if(angleDifference(endToStartAngle,endToThisAngle)>Math.PI/2)
                perpendicular = false;
            else
                perpendicular = true;
        }
        return perpendicular;
    }

    private double angleDifference(double rad1, double rad2){
        double difference = Math.abs(rad1-rad2);
        if(difference>Math.PI)
            difference = Math.PI-difference%Math.PI;
        return difference;
    }

    private double distanceToLineHead(Point lineStart, Point lineEnd){
        double distance;
        double toEnd = lineEnd.distance(this);
        double toStart = lineStart.distance(this);
        distance = toStart<toEnd ? toStart : toEnd;
        return distance;
    }

    /*This method is only used for Testing*//*
    public boolean deviationIsPerpendicular2(Point lineStart, Point lineEnd){
        double startToEndAngle = Math.atan2(lineEnd.y - lineStart.y, lineEnd.x - lineStart.x);
        double startToThisAngle = Math.atan2(this.y - lineStart.y, this.x - lineStart.x);
        double endToStartAngle = Math.atan2(lineStart.y - lineEnd.y, lineStart.x - lineEnd.x);
        double endToThisAngle = Math.atan2(this.y - lineEnd.y, this.x - lineEnd.x);
        return deviationIsPerpendicular(startToEndAngle, startToThisAngle, endToStartAngle, endToThisAngle);
    }
*/
}