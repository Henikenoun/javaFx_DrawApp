package model;

public class Edge {
    private final Node from;
    private final Node to;
    private final double weight;

    public Edge(Node from, Node to) {
        this.from = from;
        this.to = to;
        this.weight = Math.hypot(from.getX() - to.getX(), from.getY() - to.getY());
    }

    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }

    public double getWeight() {
        return weight;
    }
}
