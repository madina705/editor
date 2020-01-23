package com.shapes;

import javafx.scene.canvas.GraphicsContext;

public interface Shape extends java.awt.Shape {

    void draw(GraphicsContext gc);
    void moveTo(int x, int y);
}
