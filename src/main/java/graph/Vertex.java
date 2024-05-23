package graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Vertex implements Comparable<Vertex> {
    private static final Map<Point, Vertex> HASH = new HashMap<>();

    private final char name;  // метка А например
    private final int row;
    private final int col;
    private boolean isVisited;

    private Vertex(char name, int row, int col) {
        this.name = name;
        this.row = row;
        this.col = col;

        this.isVisited = false;
    }

    public static Vertex createVertex(char name, int row, int col) {
        for (Point point : HASH.keySet()) {
            if (point.name == name && point.row == row && point.col == col) {
                return HASH.get(point);
            }
        }

        Vertex vertex = new Vertex(name, row, col);
        HASH.put(new Point(name, row, col), vertex);

        return vertex;
    }

    public char getName() {
        return name;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }

    public double distanceTo(Vertex vertex) {
        return Math.sqrt(Math.pow(vertex.row - this.row, 2) + Math.pow(vertex.col - this.col, 2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return name == vertex.name && row == vertex.row && col == vertex.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, row, col);
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "name=" + name +
                ", row=" + row +
                ", col=" + col +
                ", isVisited=" + isVisited +
                '}';
    }

    @Override
    public int compareTo(Vertex vertex) {
        return (int) Math.round(this.distanceTo(vertex));
    }

    private record Point(char name, int row, int col) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return row == point.row && col == point.col && name == point.name;
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, row, col);
        }
    }
}
