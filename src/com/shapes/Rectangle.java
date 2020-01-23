package com.shapes;

import javafx.scene.canvas.GraphicsContext;

public class Rectangle extends java.awt.Rectangle implements Shape {

    private String name;

    public Rectangle(int x, int y, int width, int height) {
        super(x, y, width, height);
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
        gc.strokeRoundRect(x, y, width, height, 30.0, 20.0);
        gc.fillText(name, x + width, y);
    }

    @Override
    public void moveTo(int x, int y) {
        this.x += x;
        this.y += y;
    }
}
