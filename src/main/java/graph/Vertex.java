package graph;

import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

public class Vertex implements Comparable<Vertex> {
    private static final Map<Point, Vertex> HASH = new HashMap<>();
    
    private final char name;
    private int row;
    private int col;
    
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

    
    public double distanceTo(Vertex vertex) {
        return Math.sqrt(Math.pow(vertex.row - this.row, 2) + Math.pow(vertex.col - this.col, 2));
    }
    
    public int getRow() {
        return row;
    }
    
    public void setRow(int row) {
        this.row = row;
    }
    
    public int getCol() {
        return col;
    }
    
    public void setCol(int col) {
        this.col = col;
    }
    
    public char getName() {
        return name;
    }
    
    public boolean isVisited() {
        return isVisited;
    }
    
    public void setVisited(boolean isVisited) {
        this.isVisited = isVisited;
    }
    
    @Override
    public String toString() {
        return "Vertex:" +
               " name=" + name +
               " row=" + row +
               " col=" + col;        
    }
    
    @Override
    public int compareTo(Vertex vertex) {
        int present = (int) Math.sqrt(Math.pow(this.row, 2) + Math.pow(this.col, 2));
        int other = (int) Math.sqrt(Math.pow(vertex.row, 2) + Math.pow(vertex.col, 2));
        return Integer.compare(present, other);
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return this.name == vertex.name &&
               this.row == vertex.row &&
               this.col == vertex.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, row, col);
        }
    
    private static class Point {
        private final char name;
        private final int row;
        private final int col;

        private Point(char name, int row, int col) {
            this.name = name;
            this.row = row;
            this.col = col;
        }
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return this.name == point.name &&
                   this.row == point.row &&
                   this.col == point.col;
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, row, col);
        }
    }
}
