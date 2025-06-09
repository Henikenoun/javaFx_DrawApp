package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RectangleEntity extends ShapeEntity {

    private double width = 0;
    private double height = 0;

    public RectangleEntity(double x, double y) {
        super(x, y, ShapeType.RECTANGLE);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.LIGHTBLUE);
        gc.fillRect(x, y, width, height);
    }

    @Override
    public String getDetails() {
        return "width=" + width + ", height=" + height;
    }

    @Override
    public void resize(double newX, double newY) {
        width = Math.abs(newX - x);
        height = Math.abs(newY - y);
        // pour gérer le cas où on dessine vers le haut/gauche
        if (newX < x) x = newX;
        if (newY < y) y = newY;
    }
}
