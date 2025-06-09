package service;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.AlgorithmType;
import model.Edge;
import model.GraphType;
import model.Node;
import service.strategy.*;

import java.util.*;

public class AlgorithmService {

    private final List<Node> nodes;
    private final List<Edge> edges;
    private GraphicsContext gc;
    private Node startNode;
    private Node endNode;
    private GraphType graphType = GraphType.COMPLETE;
    private AlgorithmStrategy strategy;

    public AlgorithmService() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void setGraphicsContext(GraphicsContext gc) {
        this.gc = gc;
    }

    public void setStrategy(AlgorithmType type) {
        switch (type) {
            case DIJKSTRA:
                strategy = new DijkstraStrategy();
                break;
            case ASTAR:
                strategy = new AStarStrategy();
                break;
            case BFS:
                strategy = new BFSStrategy();
                break;
            default:
                throw new IllegalArgumentException("Unknown Algorithm Type");
        }
    }

    public List<Node> runAlgorithm(Node start, Node end) {
        if (strategy == null) {
            throw new IllegalStateException("Strategy not set!");
        }
        return strategy.findPath(nodes, edges, start, end);
    }

    public void addNode(Node node) {
        nodes.add(node);
        if (graphType == GraphType.COMPLETE) {
            for (Node other : nodes) {
                if (other != node) {
                    edges.add(new Edge(node, other));
                    edges.add(new Edge(other, node));
                    if (gc != null) {
                        gc.setStroke(Color.LIGHTGRAY);
                        gc.setLineWidth(1);
                        gc.strokeLine(node.getX(), node.getY(), other.getX(), other.getY());
                    }
                }
            }
        } else if (graphType == GraphType.CHAIN && nodes.size() >= 2) {
            Node previous = nodes.get(nodes.size() - 2);
            edges.add(new Edge(previous, node));
            edges.add(new Edge(node, previous));
            if (gc != null) {
                gc.setStroke(Color.LIGHTGRAY);
                gc.setLineWidth(1);
                gc.strokeLine(previous.getX(), previous.getY(), node.getX(), node.getY());
            }
        }
    }

    public void clearAll() {
        nodes.clear();
        edges.clear();
        if (gc != null) {
            gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        }
    }

    public void redrawNodes(GraphicsContext gc) {
        gc.clearRect(0, 0, gc.getCanvas().getWidth(), gc.getCanvas().getHeight());
        for (Node node : nodes) {
            gc.setFill(Color.BLACK);
            gc.fillOval(node.getX() - 5, node.getY() - 5, 10, 10);
        }
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(1);
        for (Edge edge : edges) {
            gc.strokeLine(edge.getFrom().getX(), edge.getFrom().getY(), edge.getTo().getX(), edge.getTo().getY());
        }
    }

    public void setStartNode(Node startNode) {
        this.startNode = startNode;
    }

    public void setEndNode(Node endNode) {
        this.endNode = endNode;
    }

    public void setGraphType(GraphType type) {
        this.graphType = type;
    }

    public List<Node> getNodes() {
        return nodes;
    }
}
