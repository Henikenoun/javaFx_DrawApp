package repository;

import model.ShapeEntity;
import observer.Observable;

public interface ShapeRepository  {
    void save(ShapeEntity shape);
    void save(String type, String details);
}
