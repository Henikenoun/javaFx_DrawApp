package model;

import javafx.scene.canvas.GraphicsContext;

public abstract class ShapeEntity {
    protected double x;
    protected double y;
    protected ShapeType type;

    public ShapeEntity(double x, double y, ShapeType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public double getX() {
        return x;
    }
      
    public double getY() {
        return y;
    }
       
    public ShapeType getType() {
        return type;
    }

    public abstract String getDetails();

    public abstract void draw(GraphicsContext gc);

    // 🆕 méthode commune pour redimensionner dynamiquement
    public abstract void resize(double newX, double newY);

    @Override
    public String toString() {
        return "ShapeEntity{" +
                "type=" + type +
                ", x=" + x +
                ", y=" + y +
                ", details=" + getDetails() +
                '}';
    }
}
