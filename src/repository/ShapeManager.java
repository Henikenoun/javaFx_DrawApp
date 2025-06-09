package repository;

import model.ShapeEntity;
import java.util.ArrayList;
import java.util.List;

public class ShapeManager implements ShapeRepository {

    private final List<ShapeEntity> shapes = new ArrayList<>();

    @Override
    public void save(ShapeEntity shape) {
        shapes.add(shape);
    }

    @Override
    public void save(String type, String details) {
        System.out.println("Shape saved: " + type + " - " + details);
    }

    public List<ShapeEntity> getShapes() {
        return new ArrayList<>(shapes);
    }

    public void clearShapes() {
        shapes.clear();
    }

    public void removeLastShape() {
        if (!shapes.isEmpty()) {
            shapes.remove(shapes.size() - 1);
        }
    }
}
