package model;

import javafx.scene.canvas.GraphicsContext;

public abstract class ShapeDecorator extends ShapeEntity {
    protected final ShapeEntity decoratedShape;

    public ShapeDecorator(ShapeEntity decoratedShape) {
        super(decoratedShape.getX(), decoratedShape.getY(), decoratedShape.getType());
        this.decoratedShape = decoratedShape;
    }

    @Override
    public String getDetails() {
        return decoratedShape.getDetails();
    }

    @Override
    public void draw(GraphicsContext gc) {
        decoratedShape.draw(gc);
    }
}
