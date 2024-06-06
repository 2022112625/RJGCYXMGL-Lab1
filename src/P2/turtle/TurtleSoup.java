/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package turtle;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(turtle.Turtle turtle, int sideLength) {
        for(int i=0;i<4;i++) {
            turtle.forward(sideLength);
            turtle.turn(90);
        }
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        return (sides-2)*180.0/sides;
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        return (int)(360/(180-angle-0.1));
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(turtle.Turtle turtle, int sides, int sideLength) {
        for(int i=0;i<sides;i++) {
            turtle.forward(sideLength);
            turtle.turn(180-calculateRegularPolygonAngle(sides));
        }
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentBearing current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY,int targetX, int targetY) {
        return Math.atan((double)(targetY-currentY)/(targetX-currentX))/Math.PI*180+270-currentBearing;
    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
        List<Double> bearings = new ArrayList<>();
        if (xCoords.size() < 2 || yCoords.size() < 2)
            return bearings;
        double currentBearing = 0.0;
        for (int i = 0; i < xCoords.size() - 1; i++) {
            int currentX = xCoords.get(i);
            int currentY = yCoords.get(i);
            int nextX = xCoords.get(i + 1);
            int nextY = yCoords.get(i + 1);
            double bearingAdjustment = calculateBearingToPoint(currentBearing, currentX, currentY, nextX, nextY);
            currentBearing += bearingAdjustment;
            currentBearing = (currentBearing + 360) % 360;
            bearings.add(bearingAdjustment);
        }
        return bearings;
    }
    
    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points 
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and 
     * there are other algorithms too.
     * 
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    // 方法，使用礼包包装算法计算凸包
    public static Set<Point> convexHull(Set<Point> points) {
        // 如果点的数量小于3，则凸包就是点本身
        if (points.size() < 3)
            return points;

        // 将点集转换为数组
        Point[] pointArray = points.toArray(new Point[0]);
        int n = points.size();
        Set<Point> convexHullSet = new HashSet<>();

        // 找到最左边的点
        int leftmost = 0;
        for (int i = 1; i < n; i++) {
            if (pointArray[i].x < pointArray[leftmost].x)
                leftmost = i;
        }

        int p = leftmost, q;
        do {
            convexHullSet.add(pointArray[p]);
            q = (p + 1) % n;
            for (int i = 0; i < n; i++) {
                if (orientation(pointArray[p], pointArray[i], pointArray[q]) == 2)
                    q = i;
            }
            p = q;
        } while (p != leftmost);

        return convexHullSet;
    }

    // 辅助函数，确定有序三元组的方向
    static int orientation(Point p, Point q, Point r) {
        int val = (int)((q.y - p.y) * (r.x - q.x) - (q.x - p.x) * (r.y - q.y));
        if (val == 0) return 0;
        return (val > 0) ? 1 : 2;
    }
    
    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(turtle.Turtle turtle) {
        for(int i=0;i<720;i++){
            turtle.forward(i/360+1);
            turtle.turn(1);
        }
        for(int i=0;i<720;i++){
            turtle.forward(i/360+1);
            turtle.turn(-1);
        }
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        turtle.DrawableTurtle turtle = new turtle.DrawableTurtle();
        //System.out.println(calculateBearingToPoint(30,0,1,0,0));
        drawSquare(turtle, 40);
        //drawPersonalArt(turtle);
        // draw the window
        turtle.draw();
    }

}
