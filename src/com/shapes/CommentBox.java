package com.shapes;

import javafx.scene.canvas.GraphicsContext;

public class CommentBox extends Rectangle {

    public CommentBox(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setLineDashes(10);
        gc.strokeRect(x, y, width, height);
        gc.fillText(super.getName(), x + width, y);
    }
}
