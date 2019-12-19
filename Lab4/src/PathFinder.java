
import java.lang.reflect.Array;
import java.util.*;

import java.util.stream.Collectors;


public class PathFinder<V> {

    private DirectedGraph<V> graph;
    private long startTimeMillis;


    public PathFinder(DirectedGraph<V> graph) {
        this.graph = graph;
    }


    public class Result<V> {
        public final boolean success;
        public final V start;
        public final V goal;
        public final double cost;
        public final List<V> path;
        public final int visitedNodes;
        public final double elapsedTime;

        public Result(boolean success, V start, V goal, double cost, List<V> path, int visitedNodes) {
            this.success = success;
            this.start = start;
            this.goal = goal;
            this.cost = cost;
            this.path = path;
            this.visitedNodes = visitedNodes;
            this.elapsedTime = (System.currentTimeMillis() - startTimeMillis) / 1000.0;
        }

        public String toString() {
            String s = "";
            s += String.format("Visited nodes: %d\n", visitedNodes);
            s += String.format("Elapsed time: %.1f seconds\n", elapsedTime);
            if (success) {
                s += String.format("Total cost from %s -> %s: %s\n", start, goal, cost);
                s += "Path: " + path.stream().map(x -> x.toString()).collect(Collectors.joining(" -> "));
            } else {
                s += String.format("No path found from %s", start);
            }
            return s;
        }
    }


    public Result<V> search(String algorithm, V start, V goal) {
        startTimeMillis = System.currentTimeMillis();
        switch (algorithm) {
        case "random":   return searchRandom(start, goal);
        case "dijkstra": return searchDijkstra(start, goal);
        case "astar":    return searchAstar(start, goal);
        }
        throw new IllegalArgumentException("Unknown search algorithm: " + algorithm);
    }


    public Result<V> searchRandom(V start, V goal) {
        int visitedNodes = 0;
        LinkedList<V> path = new LinkedList<>();
        double cost = 0.0;
        Random random = new Random();

        V current = start;
        path.add(current);
        while (current != null) {
            visitedNodes++;
            if (current.equals(goal)) {
                return new Result<>(true, start, current, cost, path, visitedNodes);
            }

            List<DirectedEdge<V>> neighbours = graph.outgoingEdges(start);
            if (neighbours == null || neighbours.size() == 0) {
                break;
            } else {
                DirectedEdge<V> edge = neighbours.get(random.nextInt(neighbours.size()));
                cost += edge.weight();
                current = edge.to();
                path.add(current);
            }
        }
        return new Result<>(false, start, null, -1, null, visitedNodes);
    }


    public Result<V> searchDijkstra(V start, V goal) {

        HashMap<V, DirectedEdge<V>> edgeTo = new HashMap<>();
        HashMap<V, Double> distTo = new HashMap<>();

        Comparator<V> comparator = new Comparator<V>() {
            @Override
            public int compare(V o1, V o2) {
                if (distTo.get(o1) > distTo.get(o2)){
                    return 1;
                } else if (distTo.get(o1) < distTo.get(o2)){
                    return -1;
                } else {
                    return 0;
                }
            }
        };

        PriorityQueue<V> pq = new PriorityQueue<V>(comparator);

        Set<V> visitedNodes = new HashSet<>();
        List<V> p = new ArrayList<>();

        pq.add(start);
        distTo.put(start, 0.0);

        while (!pq.isEmpty()){
            V v = pq.remove();
            if (!visitedNodes.contains(v)){
                visitedNodes.add(v);
                if (v.equals(goal)){
                    p = path(v, start, edgeTo, p);
                    return new Result<V>(true, start, goal, distTo.get(v), p, visitedNodes.size());
                }
                for (DirectedEdge<V> e :graph.outgoingEdges(v)){
                    V w = e.to();
                    double newDist = distTo.get(v) + e.weight();
                    if (!distTo.containsKey(w) || (distTo.get(w) > newDist)){  // denna och den under gör precis samma
                        distTo.put(w, newDist);
                        edgeTo.put(w, e);
                        pq.add(w);
                    }
                }
            }
        }
        return new Result<V>(false, start, null, -1, null, visitedNodes.size());
    }

    private List<V> path(V start, V goal, HashMap<V, DirectedEdge<V>> map, List<V> rets){
        rets.add(start);
        if (start.equals(goal)){
            Collections.reverse(rets);
            return rets;
        }
        return path(map.get(start).from(), goal, map, rets);
    }


    public Result<V> searchAstar(V start, V goal) {
        HashMap<V, DirectedEdge<V>> edgeTo = new HashMap<>();
        HashMap<V, Double> distTo = new HashMap<>();
        HashMap<V, Double> fScore = new HashMap<>();

        Comparator<V> comparator = new Comparator<V>() {
            @Override
            public int compare(V o1, V o2) {
                if (fScore.get(o1) > fScore.get(o2)){
                    return 1;
                } else if (fScore.get(o1) < fScore.get(o2)){
                    return -1;
                } else {
                    return 0;
                }
            }
        };

        PriorityQueue<V> pq = new PriorityQueue<V>(comparator);

        Set<V> visitedNodes = new HashSet<>();
        List<V> p = new ArrayList<>();

        pq.add(start);
        distTo.put(start, 0.0);
        fScore.put(start, graph.guessCost(start, goal));

        while (!pq.isEmpty()){
            V v = pq.remove();
            if (!visitedNodes.contains(v)){
                visitedNodes.add(v);
                if (v.equals(goal)){
                    p = path(v, start, edgeTo, p);
                    return new Result<V>(true, start, goal, distTo.get(v), p, visitedNodes.size());
                }
                for (DirectedEdge<V> e : graph.outgoingEdges(v)){
                    V w = e.to();
                    double newDist = distTo.get(v) + e.weight();
                    if (!distTo.containsKey(w) || (distTo.get(w) > newDist)) {
                        distTo.put(w, newDist);
                        edgeTo.put(w, e);
                        fScore.put(w, distTo.get(w) + graph.guessCost(w, goal));
                        pq.add(w);
                    }
                }
            }
        }

        /********************
         * TODO: Task 3
         ********************/
        return new Result<V>(false, start, null, -1, null, visitedNodes.size());
    }

}
