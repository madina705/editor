package com.shapes;

import javafx.scene.canvas.GraphicsContext;

public class CommentBox extends Rectangle {

    public CommentBox(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setLineDashes(10);
        gc.strokeRoundRect(x, y, width, height, 30.0, 20.0);
        gc.fillText(super.getName(), x + width, y);
    }
}
