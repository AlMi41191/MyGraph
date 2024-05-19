package graph;

public class Vertex {
    private char label;  // метка А например
    public boolean wasVisited;

    public Vertex(final char label) {
        this.label = label;
        wasVisited = false;
    }

    public char getLabel() {
        return this.label;
    }

    public boolean isWasVisited() {
        return this.wasVisited;
    }

    public void setWasVisited(final boolean wasVisited) {
        this.wasVisited = wasVisited;
    }
}
