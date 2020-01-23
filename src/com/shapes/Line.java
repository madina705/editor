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
        int x1 = firstRectangle.x + firstRectangle.width / 2;
        int y1 = firstRectangle.y + firstRectangle.height / 2;
        int x2 = secondRectangle.x + secondRectangle.width / 2;
        int y2 = secondRectangle.y + secondRectangle.height / 2;
        gc.strokeLine(x1, y1, x2, y2);
        gc.fillText(name, (x1 + x2) / 2, (y1 + y2) / 2);
    }

    @Override
    public void moveTo(int x, int y) {

    }
}
