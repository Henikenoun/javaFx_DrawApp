package repository;

import model.ShapeEntity;
import observer.Observable;
import observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class ConsoleShapeRepository implements ShapeRepository {

    private final List<Observer> observers = new ArrayList<>();

    @Override
    public void save(ShapeEntity shape) {
        if (shape != null) {
            save(shape.getType().name(), shape.getDetails());
            //notifyObservers();
        } else {
            System.out.println("[Error] ShapeEntity is null, cannot save.");
        }
    }

    @Override
    public void save(String type, String details) {
        if (type == null || details == null) {
            System.out.println("[Error] Type or Details is null, cannot save.");
            return;
        }
        System.out.println("[" + type + "] " + details);
        //notifyObservers();
    }

   
}
