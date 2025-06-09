package factory;

import model.CircleEntity;
import model.RectangleEntity;
import model.ShapeEntity;
import model.ShapeType;
import model.SquareEntity;

public class ShapeFactory {

    public static ShapeEntity createShape(ShapeType type, double x, double y) {
        return switch (type) {
            case RECTANGLE -> new RectangleEntity(x, y);
            case SQUARE -> new SquareEntity(x, y);
            case CIRCLE -> new CircleEntity(x, y);
        };
    }
}

