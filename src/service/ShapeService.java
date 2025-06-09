package service;

import model.ShapeEntity;
import observer.Observable;
import observer.Observer;
import observer.SaveLogger;
import repository.*;

import java.util.ArrayList;
import java.util.List;

public class ShapeService implements Observable {

    private ShapeRepository shapeRepository;
    private List<ShapeEntity> shapes;
    private final List<Observer> observers = new ArrayList<>();
    private final Observer saveLogger;

    public ShapeService(ShapeRepository shapeRepository) {
        this.shapeRepository = shapeRepository;
        this.shapes = new ArrayList<>();
        this.saveLogger = new SaveLogger();
        addObserver(saveLogger);
    }

    @Override
    public void addObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public void changeSaveMethod(String method) {
        if ("console".equalsIgnoreCase(method)) {
            shapeRepository = new ConsoleShapeRepository();
        } else if ("DB".equalsIgnoreCase(method)) {
            shapeRepository = new DatabaseShapeRepository();
        } else {
            shapeRepository = new FileShapeRepository();
        }
        shapeRepository.save("ChangeStrategy", "Switched to: " + method);
        notifyObservers("💾 Méthode de sauvegarde changée vers: " + method);
    }

    public void onShapeSelected(String type) {
        shapeRepository.save("SelectedShape", type);
        notifyObservers("✅ Forme sélectionnée: " + type);
    }

    public void addShape(ShapeEntity shape) {
        shapes.add(shape);
        shapeRepository.save(shape);
        notifyObservers("➕ Forme ajoutée: " + shape.getType());
    }

    public void removeShape(ShapeEntity shape) {
        shapes.remove(shape);
        shapeRepository.save("DeleteShape", "Deleted " + shape.getType());
        notifyObservers("➖ Forme supprimée: " + shape.getType());
    }

    public void clearShapes() {
        shapes.clear();
        shapeRepository.save("ClearAll", "All shapes cleared");
        notifyObservers("🗑️ Toutes les formes ont été supprimées !");
    }

    public List<ShapeEntity> getShapes() {
        return new ArrayList<>(shapes);
    }
}
