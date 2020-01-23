package com;

import com.shapes.CommentBox;
import com.shapes.Rectangle;
import com.shapes.Shape;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public class Controller {

    private DrawingMode drawingMode = DrawingMode.RECTANGLE;
    private Model model;

    private Rectangle previousRectangle;
    private String nameText;

    private Point2D dragStartPoint;
    private Shape draggedShape;

    private static final double RECTANGLE_WIDTH = 75.0;
    private static final double RECTANGLE_HEIGHT = 75.0;

    public Controller(Model model) {
        this.model = model;
    }

    void selectRectangle() {
        drawingMode = DrawingMode.RECTANGLE;
    }

    void selectLine() {
        drawingMode = DrawingMode.LINE;
    }

    void selectCommentBox() {
        drawingMode = DrawingMode.COMMENT_BOX;
    }

    void selectName() {
        drawingMode = DrawingMode.NAME;
    }

    void selectMove() {
        drawingMode = DrawingMode.MOVE;
    }

    void textFieldTextChanged(String newValue) {
        nameText = newValue;
    }

    void pressedOnDrawingBox(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        Point2D point = new Point2D(x, y);

        switch (drawingMode) {
            case RECTANGLE:
                if (model.getRectangleThatContainsPoint(point) == null) {
                    model.createRectangle(point, RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
                }
                break;
            case LINE:
                if (previousRectangle == null) {
                    previousRectangle = (Rectangle) model.getRectangleThatContainsPoint(point);
                } else {
                    Rectangle currentRectangle = (Rectangle) model.getRectangleThatContainsPoint(point);
                    if (currentRectangle != null &&
                            !(previousRectangle instanceof CommentBox && currentRectangle instanceof CommentBox)) {
                        model.createLine(previousRectangle, currentRectangle);
                    }
                    previousRectangle = null;
                }
                break;
            case COMMENT_BOX:
                if (model.getRectangleThatContainsPoint(point) == null) {
                    model.createCommentBox(point, RECTANGLE_WIDTH, RECTANGLE_HEIGHT);
                }
                break;
            case NAME:
                if (nameText != null && !nameText.trim().isEmpty()) {
                    model.createLabel(point, nameText);
                }
                break;
            default:
                break;
        }
        model.repaint();
    }

    void canvasPaneSizeChanged(ObservableValue<? extends Number> observable) {
        if (observable instanceof ReadOnlyDoubleProperty) {
            ReadOnlyDoubleProperty doubleProperty = (ReadOnlyDoubleProperty) observable;
            double val = doubleProperty.doubleValue();
            String name = doubleProperty.getName();
            if (name.equalsIgnoreCase("width")) {
                model.setWidth(val);
            } else if (name.equalsIgnoreCase("height")) {
                model.setHeight(val);
            }
        }
    }

    void draggedOnDrawingBox(MouseEvent event) {
        if (drawingMode == DrawingMode.MOVE) {
            double x = event.getX();
            double y = event.getY();
            Point2D point = new Point2D(x, y);

            if (dragStartPoint == null) {
                Shape shape = model.getShapeThatContainsPoint(point);
                if (shape != null) {
                    draggedShape = shape;
                    dragStartPoint = point;
                }
            } else {
                int xDiff = (int) (point.getX() - dragStartPoint.getX());
                int yDiff = (int) (point.getY() - dragStartPoint.getY());
                dragStartPoint = point;
                draggedShape.moveTo(xDiff, yDiff);
                model.repaint();
            }
        }
    }

    public void dragExitedOnDrawingBox(MouseEvent event) {
        if (drawingMode == DrawingMode.MOVE && dragStartPoint != null) {
            dragStartPoint = null;
            draggedShape = null;
        }
    }

    public enum DrawingMode {
        RECTANGLE,
        LINE,
        COMMENT_BOX,
        NAME,
        MOVE
    }
}
