package com;

import com.shapes.*;
import com.shapes.Rectangle;
import com.shapes.Shape;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Model {

    private double width = 400;
    private double height = 400;

    private IDataObserver observer;
    private Canvas canvas;

    private List<Shape> shapes = new ArrayList<>();

    public void setWidth(double width) {
        this.width = (int) width;
        canvas = null; // window size changed ... invalidate canvas
    }

    public void setHeight(double height) {
        this.height = (int) height;
        canvas = null; // window size changed ... invalidate canvas
    }

    public void addListener(IDataObserver observer) {
        this.observer = observer;
    }

    private Canvas initCanvas(double width, double height) {
        Canvas canvas = new Canvas(width, height);
        return canvas;
    }

    private void update(Canvas canvas) {
        observer.update(canvas);
    }

    public void repaint() {
        if (canvas == null) {
            canvas = initCanvas(width, height);
        }

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setStroke(Color.BLACK);

        for (Shape shape : shapes) {
            shape.draw(gc);
        }
        update(canvas);
    }

    public Shape getRectangleThatContainsPoint(Point2D point) {
        for (Shape shape : shapes) {
            if ((shape instanceof NamedRectangle) && shape.contains(point.getX(), point.getY())) {
                return shape;
            }
        }
        return null;
    }

    public Shape getShapeThatContainsPoint(Point2D point) {
        for (Shape shape : shapes) {
            if (shape instanceof Line) {
                Line line = (Line) shape;
                Point temp = new Point((int) (line.x1 + line.x2) / 2, (int) (line.y1 + line.y2) / 2);
                int distance = (int) Math.sqrt((temp.x - point.getX()) * (temp.x - point.getX()) +
                        (temp.y - point.getY()) * (temp.y - point.getY()));
                if (distance < 35) {
                    return shape;
                }
            } else if (shape.contains(point.getX(), point.getY())) {
                return shape;
            }
        }
        return null;
    }

    public void createRectangle(Point2D point, double width, double height) {
        shapes.add(new NamedRectangle((int) point.getX(), (int) point.getY(), (int) width, (int) height, false));
    }

    public void createLine(Rectangle firstRectangle, Rectangle secondRectangle) {
        shapes.add(new Line(firstRectangle, secondRectangle));
    }

    public void createLabel(Point2D point, String text) {
        Shape shape = getShapeThatContainsPoint(point);
        if (shape instanceof Rectangle) {
            Rectangle rect = (Rectangle) shape;
            rect.setName(text);
        } else if (shape instanceof Line) {
            Line line = (Line) shape;
            line.setName(text);
        }
    }

    public void createCommentBox(Point2D point, double width, double height) {
        shapes.add(new NamedRectangle((int) point.getX(), (int) point.getY(), (int) width, (int) height, true));
    }
}
