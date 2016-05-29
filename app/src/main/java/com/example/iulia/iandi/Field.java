package com.example.iulia.iandi;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.renderscript.Double2;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;


/**
 * Created by Iulia on 24-Oct-15.
 */
public class Field {
    public static final int N = 8;
    public static final int dx = 30;
    public static final int dy = 30;
    public static final int h = 35;
    public int x1;// stg sus
    public int y1;//stg sus
    public int x2;//dr jos
    public int y2;//dr jos

    public static int l;
    public static int L;

    public int xd;
    public int yd;
    public int a;

    public int screenHeight=LoginActivity.getHeight();
    public int screenWidth=LoginActivity.getWidth();
    public double alocaredButtonSpace=screenHeight*(0.2);
    public int alocaredButtonSpaceInteger=(int)Math.round(alocaredButtonSpace);

    public Rect rect;
    public Canvas canvas;

    public Field(Rect rect, Canvas canvas) {
        this.rect = rect;
        this.canvas = canvas;

        x1 = rect.left;
        y1 = rect.top;
        x2 = rect.right;
        y2 = rect.bottom;


        this.l = (screenHeight- alocaredButtonSpaceInteger)/5;
        this.L = LoginActivity.getWidth() / 10;
    }

    public  static int getField_L(){
        return L;
    }
    public  static int getField_l(){
        return  l;
    }

    public void draw(Paint paint) {
        canvas.drawRect(rect, paint);
    }

    public Point setBezierPoint(int x1, int x2, int y1, int y2) {
        xd = (int) (Math.random() * (x2 - x1)) + x1;
        yd = (int) (Math.random() * (y2 - y1)) + y1;
        return new Point(xd, yd);
    }

    public Point CalculateBezierPoint(float t, Point p0, Point p1, Point p2, Point p3) {
        float p_x = 0;
        float p_y = 0;
        float u = 1 - t;
        float tt = t * t;
        float uu = u * u;
        float uuu = uu * u;
        float ttt = tt * t;

        p_x += uuu * p0.x; //first term
        p_x += 3 * uu * t * p1.x; //second term
        p_x += 3 * u * tt * p2.x; //third term
        p_x += ttt * p3.x; //fourth term

        p_y += uuu * p0.y; //first term
        p_y += 3 * uu * t * p1.y; //second term
        p_y += 3 * u * tt * p2.y; //third term
        p_y += ttt * p3.y; //fourth term

        return new Point((int) p_x, (int) p_y);
    }


}
