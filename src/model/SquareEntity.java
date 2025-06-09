package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SquareEntity extends ShapeEntity {

    private double size = 0;

    public SquareEntity(double x, double y) {
        super(x, y, ShapeType.SQUARE);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.LIGHTGREEN);
        gc.fillRect(x, y, size, size);
    }

    @Override
    public String getDetails() {
        return "size=" + size;
    }

    @Override
    public void resize(double newX, double newY) {
        double dx = Math.abs(newX - x);
        double dy = Math.abs(newY - y);
        size = Math.min(dx, dy); // pour garder un carré
        if (newX < x) x = newX;
        if (newY < y) y = newY;
    }
}
