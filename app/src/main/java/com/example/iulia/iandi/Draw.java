package com.example.iulia.iandi;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;

public class Draw extends View {

    Bitmap spaceshipB, spaceshipR;
    public Activity context;


    public Draw(Activity context) {
        super(context);
        this.context = context;
    }

  /*  public Bitmap[] getPlanets() {
        Bitmap[] planets = new Bitmap[32];

        planets[0] = BitmapFactory.decodeResource(getResources(), R.drawable.pluto, null);
        planets[1] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroidut, null);
        planets[2] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroidut, null);
        planets[3] = BitmapFactory.decodeResource(getResources(), R.drawable.neptun, null);
        planets[4] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroidut, null);
        planets[5] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroidut, null);
        planets[6] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroidut, null);
        planets[7] = BitmapFactory.decodeResource(getResources(), R.drawable.uranus, null);
        planets[8] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroidut, null);
        planets[9] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroidut, null);
        planets[10] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroidut, null);
        planets[11] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroidut, null);
        planets[12] = BitmapFactory.decodeResource(getResources(), R.drawable.saturn, null);
        planets[13] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroidut, null);
        planets[14] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroidut, null);
        planets[15] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroidut, null);
        planets[16] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroidut, null);
        planets[17] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroidut, null);
        planets[18] = BitmapFactory.decodeResource(getResources(), R.drawable.jupiter, null);
        planets[19] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroidut, null);
        planets[20] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroidut, null);
        planets[21] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroidut, null);
        planets[22] = BitmapFactory.decodeResource(getResources(), R.drawable.ceres, null);
        planets[23] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroidut, null);
        planets[24] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroidut, null);
        planets[25] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroidut, null);
        planets[26] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroidut, null);
        planets[27] = BitmapFactory.decodeResource(getResources(), R.drawable.mars, null);
        planets[28] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroidut, null);
        planets[29] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroidut, null);
        planets[30] = BitmapFactory.decodeResource(getResources(), R.drawable.asteroidut, null);
        planets[31] = BitmapFactory.decodeResource(getResources(), R.drawable.terra, null);
        return planets;
    }*/


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Rect rect = new Rect();
        Field field = new Field(rect,canvas);
        Road road = new Road(field);
        Point[] bezierPoints = road.getBezierPoints();
        Point[] points = road.getRandomPoints();
        Point [] controlPoints=road.getControlPoints(points);
       // Bitmap[] planets = getPlanets();
        //spaceshipB = BitmapFactory.decodeResource(getResources(), R.drawable.spaceship, null);
       // spaceshipR = BitmapFactory.decodeResource(getResources(), R.drawable.spaceship_red, null);
/*.....................................................................*/

       Paint blue = new Paint();
        Paint black = new Paint();
        Paint white = new Paint();
        Paint red = new Paint();
        Paint yellow = new Paint();

        blue.setColor(Color.BLUE);
        red.setColor(Color.RED);
        black.setColor(Color.BLACK);
        white.setColor(Color.WHITE);
        yellow.setColor(Color.YELLOW);

        blue.setStyle(Paint.Style.FILL);
        white.setStyle(Paint.Style.FILL);
        red.setStyle(Paint.Style.FILL);
        yellow.setStyle(Paint.Style.FILL);

        int rectWidth = Field.getField_L();
        int rectHeight = Field.getField_l();

      for (int j = 0; j <= 4; j += 2) {//pentru cele 3 linii
            for (int i = 0; i <= 9; i++) {
                rect = new Rect();
                Rect rect1 = new Rect();//mare
                rect.set(i * rectWidth + Field.dx, j * rectHeight + Field.dy, (i + 1) * rectWidth - Field.dx, (1 + j) * rectHeight - Field.dy);//mic
                rect1.set(i * rectWidth, j * rectHeight, (i + 1) * rectWidth, (1 + j) * rectHeight);//mare
                field = new Field(rect, canvas);
                Field field1 = new Field(rect1, canvas);//mare
                if (i % 2 == 0) {
                    field1.draw(blue);//mare
                    field.draw(white);

                } else {
                    field1.draw(white);//mare
                    field.draw(blue);
                }
            }
        }
        //colturile


       Rect rect1 = new Rect();//mare
        Field field1 = new Field(rect1, canvas);//mare
        rect.set(Field.dx, rectHeight + Field.dy, rectWidth - Field.dx, 2 * rectHeight - Field.dy);//legatura dintre prima si a doua linie-de sus in jos
        rect1.set(0, field1.l, field1.L, 2 * field1.l);//mare
        field1.draw(white);//mare
        field.draw(blue);
        rect1.set(9 * field1.L, 3 * field1.l, 10 * field1.L, 4 * field1.l);//mare
        rect.set(9 * rectWidth + Field.dx, 3 * rectHeight + Field.dy, 10 * rectWidth - Field.dx, 4 * rectHeight - Field.dy);//dreptunghiul mic//legatura dintre  a 2-a si a 3-a
        field1.draw(blue);//mare
        field.draw(white);


        for (int i = 0; i <= bezierPoints.length - 1; i++) {
             canvas.drawCircle(bezierPoints[i].x, bezierPoints[i].y, 5, black);
        }
        /*for (int i = 0; i < 22; i++) {
            canvas.drawBitmap(planets[i], points[i].x - planets[i].getWidth() / 2, points[i].y - planets[i].getHeight() / 2, null);
            canvas.drawBitmap(spaceshipB, points[i].x - spaceshipB.getWidth() / 2, points[i].y - spaceshipB.getHeight() / 2 - planets[i].getHeight() / 2, null);
            canvas.drawBitmap(spaceshipR, points[i].x - spaceshipR.getWidth() / 2, points[i].y - spaceshipR.getHeight() / 2 - planets[i].getHeight() / 2 - 25, null);
        }

        //ultimul rand de planete
        for (int i = 22; i < planets.length; i++) {
            if (i != 27) {
                canvas.drawBitmap(planets[i], points[i].x - planets[i].getWidth() / 2, points[i].y - planets[i].getHeight() / 2 + 20, null);
            } else {
                canvas.drawBitmap(planets[i], 410, 50, null);
            }
            if (i < planets.length - 1) {//nu mai desenaz navetele pa Pamant
                if (i == 27) {
                    canvas.drawBitmap(spaceshipB, 410, 50 - spaceshipB.getHeight() / 2 - planets[i].getHeight() / 2 + 20, null);
                    canvas.drawBitmap(spaceshipR, 410, 50 - spaceshipR.getHeight() / 2 - planets[i].getHeight() / 2 - 5, null);
                } else {
                    canvas.drawBitmap(spaceshipB, points[i].x - spaceshipB.getWidth() / 2, points[i].y - spaceshipB.getHeight() / 2 - planets[i].getHeight() / 2 + 20, null);
                    canvas.drawBitmap(spaceshipR, points[i].x - spaceshipR.getWidth() / 2, points[i].y - spaceshipR.getHeight() / 2 - planets[i].getHeight() / 2 - 5, null);
                }
            }
        }*/

        for (int i = 0; i < points.length; i++) {
            canvas.drawCircle(points[i].x, points[i].y, 5, red);
        }
        for(int i=0;i<controlPoints.length;i++){
            canvas.drawCircle(controlPoints[i].x,controlPoints[i].y,5,yellow);
        }
    }

}