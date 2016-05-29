package com.example.iulia.iandi;

import android.graphics.Point;



/**
 * Created by Iulia on 02.11.2015.
 */
public class Road {
    public Field field;
    public Road(Field field) {
        this.field = field;

    }
    public Point[] getRandomPoints() {//puncte pe patratele mici
        Point[] points = new Point[32];
        int a = 2;
        for (int i = 0; i <= 9; i++) {

            points[i] = field.setBezierPoint(i * field.L + field.dx, (i + 1) * field.L - field.dx, 4 * field.l + field.dy, 5 * field.l - field.dy);
            if (i == 9)
                points[i + 1] = field.setBezierPoint(i * field.L + field.dx, (i + 1) * field.L - field.dx, 3 * field.l + field.dy, 4 * field.l - field.dy);
        }
        for (int j = 9; j >= 0; j--) {
            points[a + j] = field.setBezierPoint(j * field.L + field.dx, (j + 1) * field.L - field.dx, 2 * field.l + field.dy, 3 * field.l - field.dy);
            a = a + 2;
            if (j == 0)
                points[21] = field.setBezierPoint(j * field.L + field.dx, (j + 1) * field.L - field.dx, field.l + field.dy, 2 * field.l - field.dy);
        }
        for (int k = 0; k <= 9; k++) {
            points[22 + k] = field.setBezierPoint(k * field.L + field.dx, (k + 1) * field.L - field.dx, field.dy, field.l - field.dy);
        }
        return points;
    }

    public Point[] getControlPoints(Point[] points) {

        Point[] controlPoints = new Point[22];
        controlPoints[0] = new Point(points[0].x + field.h, points[0].y - field.h);
        controlPoints[1] = new Point(points[3].x - field.h, points[3].y - field.h);
        controlPoints[2] = new Point(points[3].x + field.h, points[3].y + field.h);
        controlPoints[3] = new Point(points[6].x - field.h, points[6].y + field.h);
        controlPoints[4] = new Point(points[6].x + field.h, points[6].y - field.h);
        controlPoints[5] = new Point(points[9].x - field.h, points[9].y + field.h);
        controlPoints[6] = new Point(points[9].x + field.h, points[9].y - field.h);
        controlPoints[7] = new Point(points[11].x + field.h, points[11].y - field.h);
        controlPoints[8] = new Point(points[12].x - field.h, points[12].y + field.h);
        controlPoints[9] = new Point(points[15].x + field.h, points[15].y - field.h);
        controlPoints[10] = new Point(points[15].x - field.h, points[15].y + field.h);
        controlPoints[11] = new Point(points[18].x + field.h, points[18].y - field.h);
        controlPoints[12] = new Point(points[18].x - field.h, points[18].y + field.h);
        controlPoints[13] = new Point(points[20].x - field.h, points[20].y + field.h);
        controlPoints[14] = new Point(points[22].x - field.h, points[22].y - field.h);
        controlPoints[15] = new Point(points[24].x - field.h, points[24].y + field.h);
        controlPoints[16] = new Point(points[24].x + field.h, points[24].y - field.h);
        controlPoints[17] = new Point(points[27].x - field.h, points[27].y + field.h);
        controlPoints[18] = new Point(points[27].x + field.h, points[27].y - field.h);
        controlPoints[19] = new Point(points[30].x - field.h, points[30].y + field.h);
        controlPoints[20] = new Point(points[30].x + field.h, points[30].y - field.h);
        controlPoints[21] = new Point(points[31].x, points[31].y + field.h);
        return controlPoints;
    }

    public Point[] getBezierPoints() {

        Point[] points = getRandomPoints();
        Point[] controlPoints = getControlPoints(getRandomPoints());
        Point[] bezierPoints = new Point[(field.N * 11) + 1];
        int j = 0;
        int ll = 0;
        for (int k = 0; k <= 18; k += 2) {//k tine contol points
            for (int i = 0; i <= field.N; i++) {//i tine pc de pe curba
                bezierPoints[ll + i] = field.CalculateBezierPoint((float) i / field.N, points[j], controlPoints[k], controlPoints[k + 1], points[j + 3]);
            }
            if (k == 18) {
                for (int l = 0; l <= field.N; l++) {
                    bezierPoints[(field.N * 10) + l] = field.CalculateBezierPoint((float) l / field.N, points[30], controlPoints[20], controlPoints[21], points[31]);
                }
            }
            j += 3;
            ll += Field.N;
        }

        return bezierPoints;
    }



}
