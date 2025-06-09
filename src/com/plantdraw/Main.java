package com.plantdraw;

import config.AppConfig;
import controller.ShapeController;
import controller.AlgorithmController;
import model.ShapeType;
import model.AlgorithmType;
import model.GraphType;
import model.Node;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import presenter.ShapePresenter;
import service.ShapeService;
import service.AlgorithmService;

public class Main extends Application {

    private ShapeController shapeController;
    private AlgorithmController algorithmController;
    private ShapeType currentShapeType = ShapeType.RECTANGLE;
    private Canvas canvas;
    private GraphicsContext gc;
    private Node selectedStartNode = null;
    private Node selectedEndNode = null;
    private boolean selectingStart = false;
    private boolean selectingEnd = false;
    private boolean movingNodeMode = false;
    private Node nodeBeingMoved = null;

    @Override
    public void start(Stage primaryStage) {
        ShapeService shapeService = AppConfig.createShapeService();
        ShapePresenter shapePresenter = new ShapePresenter();
        shapeController = new ShapeController(shapeService, shapePresenter);
        shapeController.setCurrentColor(Color.BLACK);

        AlgorithmService algorithmService = new AlgorithmService();
        algorithmController = new AlgorithmController(algorithmService);

        BorderPane root = new BorderPane();

        // Top Menu
        HBox menuBar = new HBox(10);
        menuBar.setPadding(new Insets(10));

        Button rectangleButton = new Button("Rectangle");
        Button squareButton = new Button("Square");
        Button circleButton = new Button("Circle");
        Button deleteButton = new Button("Effacer Un");
        Button clearButton = new Button("Clear All");
        ColorPicker colorPicker = new ColorPicker(Color.BLACK);

        ChoiceBox<String> saveMethodChoiceBox = new ChoiceBox<>();
        saveMethodChoiceBox.getItems().addAll("Console", "File", "DB");
        saveMethodChoiceBox.setValue("Console");

        ChoiceBox<String> graphTypeChoiceBox = new ChoiceBox<>();
        graphTypeChoiceBox.getItems().addAll("Complet", "Chainé");
        graphTypeChoiceBox.setValue("Complet");

        menuBar.getChildren().addAll(
            colorPicker, rectangleButton, squareButton, circleButton,
            deleteButton, clearButton, saveMethodChoiceBox, graphTypeChoiceBox
        );
        root.setTop(menuBar);

        // Center Canvas
        canvas = new Canvas(800, 600);
        gc = canvas.getGraphicsContext2D();
        root.setCenter(canvas);

        algorithmController.setGraphicsContext(gc);

        // Right Sidebar
        VBox rightSidebar = new VBox(10);
        rightSidebar.setPadding(new Insets(10));

        Button selectStartButton = new Button("Select Start Node");
        Button selectEndButton = new Button("Select End Node");
        Button dijkstraButton = new Button("Run Dijkstra");
        Button aStarButton = new Button("Run A*");
        Button bfsButton = new Button("Run BFS");
        Button resetPathButton = new Button("Reset Path");
  

        rightSidebar.getChildren().addAll(selectStartButton, selectEndButton, dijkstraButton, aStarButton, bfsButton, resetPathButton);
        root.setRight(rightSidebar);

        // Events
        rectangleButton.setOnAction(e -> {
            currentShapeType = ShapeType.RECTANGLE;
            shapeController.onShapeSelected("RECTANGLE");
        });
        Button moveNodeButton = new Button("Déplacer Noeud");
        rightSidebar.getChildren().add(moveNodeButton);
        moveNodeButton.setOnAction(e -> {
            movingNodeMode = true;
            selectingStart = false;
            selectingEnd = false;
            System.out.println("Mode déplacement activé : cliquez sur un noeud puis déplacez-le.");
        });
        squareButton.setOnAction(e -> {
            currentShapeType = ShapeType.SQUARE;
            shapeController.onShapeSelected("SQUARE");
        });

        circleButton.setOnAction(e -> {
            currentShapeType = ShapeType.CIRCLE;
            shapeController.onShapeSelected("CIRCLE");
        });


        colorPicker.setOnAction(e -> shapeController.setCurrentColor(colorPicker.getValue()));
        deleteButton.setOnAction(e -> shapeController.deleteLastShape(canvas, gc));
        clearButton.setOnAction(e -> {
            shapeController.clearAllShapes(canvas, gc);
            algorithmController.resetAll();
            selectedStartNode = null;
            selectedEndNode = null;
        });
        saveMethodChoiceBox.setOnAction(e -> {
            String method = saveMethodChoiceBox.getValue();
            shapeController.onSaveMethodChanged(method);
        });
        graphTypeChoiceBox.setOnAction(e -> {
            String selected = graphTypeChoiceBox.getValue();
            if ("Complet".equals(selected)) {
                algorithmController.setGraphType(GraphType.COMPLETE);
            } else {
                algorithmController.setGraphType(GraphType.CHAIN);
            }
        });

        selectStartButton.setOnAction(e -> {
            selectingStart = true;
            selectingEnd = false;
            System.out.println("Cliquez sur un noeud pour le Start Node.");
        });

        selectEndButton.setOnAction(e -> {
            selectingEnd = true;
            selectingStart = false;
            System.out.println("Cliquez sur un noeud pour le End Node.");
        });
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> {
            if (!movingNodeMode) return;

            double x = e.getX();
            double y = e.getY();

            for (Node node : algorithmController.getNodes()) {
                if (Math.hypot(node.getX() - x, node.getY() - y) <= 10) {
                    nodeBeingMoved = node;
                    System.out.println("Noeud sélectionné pour déplacement : " + node.getId());
                    break;
                }
            }
        });

        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            if (nodeBeingMoved == null) return;

            double newX = e.getX();
            double newY = e.getY();

            // Mettre à jour la position du noeud
            nodeBeingMoved.setX(newX);
            nodeBeingMoved.setY(newY);

            // Redessiner tout
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            shapeController.redrawShapes(gc);   // suppose que cette méthode existe pour redessiner toutes les formes
            algorithmController.resetPathOnly(); // redraw nodes et éventuel tracé

        });

        canvas.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            if (nodeBeingMoved != null) {
                System.out.println("Déplacement terminé pour le noeud : " + nodeBeingMoved.getId());
                nodeBeingMoved = null;
                movingNodeMode = false;
            }
        });

        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            double x = e.getX();
            double y = e.getY();
            if (e.getButton() == MouseButton.PRIMARY) {
                if (selectingStart || selectingEnd) {
                    boolean nodeFound = false;
                    for (Node node : algorithmController.getNodes()) {
                        if (Math.hypot(node.getX() - x, node.getY() - y) <= 10) {
                            if (selectingStart) {
                                selectedStartNode = node;
                                algorithmController.setStartNode(node);
                                System.out.println("✅ Start Node sélectionné : " + node.getId());
                            } else if (selectingEnd) {
                                selectedEndNode = node;
                                algorithmController.setEndNode(node);
                                System.out.println("✅ End Node sélectionné : " + node.getId());
                            }
                            selectingStart = false;
                            selectingEnd = false;
                            nodeFound = true;
                            break;
                        }
                    }
                    if (!nodeFound) {
                        System.out.println("⚠️ Aucun noeud trouvé à cet endroit !");
                    }
                } else {
                    shapeController.addShape(currentShapeType, x, y, gc);
                    Node node = new Node(x, y);
                    algorithmController.addNode(node);
                }
            }
        });

        dijkstraButton.setOnAction(e -> algorithmController.executeAlgorithm(AlgorithmType.DIJKSTRA));
        aStarButton.setOnAction(e -> algorithmController.executeAlgorithm(AlgorithmType.ASTAR));
        bfsButton.setOnAction(e -> algorithmController.executeAlgorithm(AlgorithmType.BFS));
        resetPathButton.setOnAction(e -> {
            algorithmController.resetPathOnly();
            selectedStartNode = null;
            selectedEndNode = null;
        });

        Scene scene = new Scene(root, 1000, 700);
        primaryStage.setTitle("Shape Creator + Pathfinding Algorithms");
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(e -> shapeController.onApplicationExit());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
