package com.shapes;

import javafx.scene.canvas.GraphicsContext;

public class NamedRectangle extends Rectangle {

    private boolean isComment;

    private Shape compartment;

    public NamedRectangle(int x, int y, int width, int height, boolean isComment) {
        super(x, y, width, height);
        this.isComment = isComment;
        if(isComment){
            compartment = new CommentBox(x, y + height, width, height);
        }
        else{
            compartment = new Rectangle(x, y + height, width, height);
        }

    }

    @Override
    public void draw(GraphicsContext gc) {
        if(this.isComment){
            gc.setLineDashes(10);
        }
        else{
            gc.setLineDashes(null);
        }
        gc.strokeRoundRect(x, y, width, height, 30.0, 20.0);
        compartment.draw(gc);
        gc.fillText(super.getName(), x + width, y);
    }

    @Override
    public void moveTo(int x, int y) {
        super.moveTo(x, y);
        compartment.moveTo(x, y);
    }
}
