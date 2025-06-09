package model;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ColoredShapeDecorator extends ShapeDecorator {
    private final Color color;

    public ColoredShapeDecorator(ShapeEntity decoratedShape, Color color) {
        super(decoratedShape);
        this.color = color;
    }

    @Override
    public String getDetails() {
        return super.getDetails() + ", Color: " + color.toString();
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(color);

        switch (decoratedShape.getType()) {
            case RECTANGLE:
                gc.fillRect(getX(), getY(), 80, 40);
                break;
            case SQUARE:
                gc.fillRect(getX(), getY(), 40, 40);
                break;
            case CIRCLE:
                gc.fillOval(getX(), getY(), 50, 50);
                break;
            default:
                break;
        }
        
        // Si tu veux aussi dessiner la forme "originale" après la coloration :
        // super.draw(gc);
    }
    @Override
    public void resize(double newX, double newY) {
        decoratedShape.resize(newX, newY); // 👈 délègue aussi le redimensionnement
    }
}
