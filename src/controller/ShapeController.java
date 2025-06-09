package controller;

import model.CircleEntity;
import model.ColoredShapeDecorator;
import model.RectangleEntity;
import model.ShapeEntity;
import javafx.scene.paint.Color;
import factory.ShapeFactory;
import model.ShapeType;
import model.SquareEntity;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import presenter.ShapePresenter;
import service.ShapeService;
import observer.Observable;
import observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class ShapeController implements Observable {

    private final ShapeService service;
    private final ShapePresenter presenter;
    private final List<ShapeEntity> shapes = new ArrayList<>();
    private final List<Observer> observers = new ArrayList<>();
    private Color currentColor = Color.BLACK;
    private ShapeEntity currentShape = null;
    public ShapeController(ShapeService service, ShapePresenter presenter) {
        this.service = service;
        this.presenter = presenter;
    }

    public void startShape(ShapeType type, double startX, double startY) {
        switch (type) {
            case RECTANGLE:
                currentShape = new RectangleEntity(startX, startY);
                break;
            case SQUARE:
                currentShape = new SquareEntity(startX, startY);
                break;
            case CIRCLE:
                currentShape = new CircleEntity(startX, startY);
                break;
            default:
                throw new IllegalArgumentException("Type de forme inconnu : " + type);
        }
        shapes.add(currentShape);
    }
    public void resizeCurrentShape(double newX, double newY) {
        if (currentShape != null) {
            currentShape.resize(newX, newY);
        }
    }

    // Fin du dessin / redimensionnement
    public void endCurrentShape() {
        currentShape = null;
    }
    // Observable methods
    @Override
    public void addObserver(Observer observer) {
        if (observer != null && !observers.contains(observer)) {
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

    // Méthodes publiques

    public void onSaveMethodChanged(String method) {
        service.changeSaveMethod(method);
        notifyObservers("✅ Méthode de sauvegarde changée vers: " + method);
    }

    public void onShapeSelected(String type) {
        service.onShapeSelected(type);
        notifyObservers("✏️ Forme sélectionnée: " + type);
    }

    public void setCurrentColor(Color color) {
        this.currentColor = color;
        notifyObservers("🎨 Couleur sélectionnée: " + color.toString());
    }

    public void addShape(ShapeType type, double x, double y, GraphicsContext gc) {
        ShapeEntity shape = ShapeFactory.createShape(type, x, y);
        if (currentColor != null) {
            shape = new ColoredShapeDecorator(shape, currentColor);
        }
        shapes.add(shape);
        shape.draw(gc);
        service.addShape(shape);
        notifyObservers("➕ Nouvelle forme ajoutée : " + type.name() + " aux coordonnées (" + (int)x + ", " + (int)y + ")");
    }

    public void deleteLastShape(Canvas canvas, GraphicsContext gc) {
        if (!shapes.isEmpty()) {
            ShapeEntity removed = shapes.remove(shapes.size() - 1);
            redrawAll(gc);
            service.removeShape(removed);
            notifyObservers("❌ Dernière forme supprimée : " + removed.getType().name());
        } else {
            notifyObservers("⚠️ Aucune forme à supprimer !");
        }
    }

    public void clearAllShapes(Canvas canvas, GraphicsContext gc) {
        if (!shapes.isEmpty()) {
            shapes.clear();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            service.clearShapes();
            notifyObservers("🧹 Toutes les formes ont été effacées !");
        } else {
            notifyObservers("⚠️ Aucune forme à effacer !");
        }
    }

    public void onApplicationExit() {
        notifyObservers("👋 Application fermée, au revoir !");
        System.out.println("Application closed");
    }
    public void redrawShapes(GraphicsContext gc) {
        gc.clearRect(0, 0, 800, 600);
        for (ShapeEntity shape : shapes) {  // shapes : liste des formes créées
            shape.draw(gc);
        }
    }

    // Méthode interne pour tout redessiner
    private void redrawAll(GraphicsContext gc) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        for (ShapeEntity shape : shapes) {
            shape.draw(gc);
        }
    }
 // Redessine toutes les formes sur le canvas
    public void redrawAll(GraphicsContext gc, double canvasWidth, double canvasHeight) {
        gc.clearRect(0, 0, canvasWidth, canvasHeight);
        for (ShapeEntity shape : shapes) {
            shape.draw(gc);
        }
    }

    // Optionnel : accès à la liste des formes
    public List<ShapeEntity> getShapes() {
        return shapes;
    }
}
