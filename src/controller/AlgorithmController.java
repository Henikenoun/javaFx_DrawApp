package controller;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.AlgorithmType;
import model.GraphType;
import model.Node;
import service.AlgorithmService;

import java.util.List;

public class AlgorithmController {

    private final AlgorithmService algorithmService;
    private GraphicsContext gc;
    private Node startNode;
    private Node endNode;

    public AlgorithmController(AlgorithmService algorithmService) {
        this.algorithmService = algorithmService;
    }

    public void setGraphicsContext(GraphicsContext gc) {
        this.gc = gc;
        algorithmService.setGraphicsContext(gc);
    }

    public void addNode(Node node) {
        algorithmService.addNode(node);
    }

    public List<Node> getNodes() {
        return algorithmService.getNodes();
    }

    public void setStartNode(Node node) {
        this.startNode = node;
        algorithmService.setStartNode(node);
    }

    public void setEndNode(Node node) {
        this.endNode = node;
        algorithmService.setEndNode(node);
    }

    public void setGraphType(GraphType type) {
        algorithmService.setGraphType(type);
    }

    public void executeAlgorithm(AlgorithmType type) {
        if (startNode == null) {
            System.out.println("❌ Start Node n'est pas sélectionné !");
            return;
        }
        if (endNode == null) {
            System.out.println("❌ End Node n'est pas sélectionné !");
            return;
        }

        // Nouvelle structure : D'abord définir la stratégie
        algorithmService.setStrategy(type);

        // Ensuite exécuter l'algorithme
        List<Node> path = algorithmService.runAlgorithm(startNode, endNode);

        if (path != null && !path.isEmpty()) {
            drawPath(path);
            System.out.println("✅ Chemin tracé avec succès.");
            System.out.println("Nombre de noeuds parcourus : " + path.size());
        } else {
            System.out.println("❌ Aucun chemin trouvé !");
        }
    }

    private void drawPath(List<Node> path) {
        if (gc == null || path == null || path.isEmpty()) return;

        gc.setStroke(Color.RED);
        gc.setLineWidth(3);
        for (int i = 0; i < path.size() - 1; i++) {
            Node from = path.get(i);
            Node to = path.get(i + 1);
            gc.strokeLine(from.getX(), from.getY(), to.getX(), to.getY());
        }
    }

    public void resetPathOnly() {
        if (gc != null) {
            algorithmService.redrawNodes(gc);
        }
    }

    public void resetAll() {
        if (gc != null) {
            algorithmService.clearAll();
        }
    }
}
