package com.shapes;

import javafx.scene.canvas.GraphicsContext;

import java.awt.geom.Line2D;

public class Line extends Line2D.Float implements Shape {

    private String name;
    private Rectangle firstRectangle;
    private Rectangle secondRectangle;

    public Line(Rectangle firstRectangle, Rectangle secondRectangle) {
        super(firstRectangle.x + firstRectangle.width / 2, firstRectangle.y + firstRectangle.height / 2, secondRectangle.x + secondRectangle.width / 2, secondRectangle.y + secondRectangle.height / 2);
        this.firstRectangle = firstRectangle;
        this.secondRectangle = secondRectangle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public void draw(GraphicsContext gc) {
        gc.setLineDashes(null);
        int startX = firstRectangle.x + firstRectangle.width / 2;
        int startY = firstRectangle.y + firstRectangle.height / 2;
        int endX = secondRectangle.x + secondRectangle.width / 2;
        int endY = secondRectangle.y + secondRectangle.height / 2;
        gc.strokeLine(startX, startY, endX, endY);

        double angle = Math.atan2((endY - startY), (endX - startX)) - Math.PI / 2.0;
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);

        double x1 = (- 1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * 20.5 + endX;
        double y1 = (- 1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * 20.5 + endY;

        double x2 = (1.0 / 2.0 * cos + Math.sqrt(3) / 2 * sin) * 20.5 + endX;
        double y2 = (1.0 / 2.0 * sin - Math.sqrt(3) / 2 * cos) * 20.5 + endY;

        gc.strokeLine(endX, endY, x1, y1);
        gc.strokeLine(endX, endY, x2, y2);

        gc.fillText(name, (startX + endX) >> 1, (startY + endY) >> 1);
    }

    @Override
    public void moveTo(int x, int y) {

    }
}
