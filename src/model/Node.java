package model;

public class Node {

    private static int counter = 0;

    private final int id;
    private double x;
    public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	private double y;

    public Node(double x, double y) {
        this.id = counter++;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Node)) return false;
        Node other = (Node) obj;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
