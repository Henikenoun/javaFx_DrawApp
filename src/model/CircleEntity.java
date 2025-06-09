package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class CircleEntity extends ShapeEntity {

    private double radius = 0;

    public CircleEntity(double x, double y) {
        super(x, y, ShapeType.CIRCLE);
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.LIGHTCORAL);
        gc.fillOval(x, y, radius * 2, radius * 2);
    }

    @Override
    public String getDetails() {
        return "radius=" + radius;
    }

    @Override
    public void resize(double newX, double newY) {
        double dx = Math.abs(newX - x);
        double dy = Math.abs(newY - y);
        radius = Math.min(dx, dy) / 2; // rayon = moitié du plus petit côté

        // mise à jour de x, y pour que le cercle reste centré sur la zone de dessin
        if (newX < x) x = newX;
        if (newY < y) y = newY;
    }
}
