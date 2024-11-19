package osu;

class Edge {
    Node from;
    Node to;
    int length;

    public Edge(Node from, Node to, int length) {
        this.from = from;
        this.to = to;
        this.length = length;
    }

    public String toString() {
        return from.getName() + " -> " + to.getName() + " [" + length + " km]";
    }

    public Node getFrom() {
        return from;
    }

    public void setFrom(Node from) {
        this.from = from;
    }

    public Node getTo() {
        return to;
    }

    public void setTo(Node to) {
        this.to = to;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}