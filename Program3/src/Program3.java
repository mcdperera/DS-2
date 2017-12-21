
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author Charmal
 */
public class Program3 {

    /*
    / the time variable
     */
    private static int time;

    /*
    / List of vertexes
     */
    private static ArrayList<Vertex> vertexList = new ArrayList<>();

    /*
    / The nodes
     */
    private static List<Pair<Integer, Integer>> nodes = new ArrayList<>();


    /*
    /  The finishing time.
     */
    private static int finishing_time = 1;

    /**
     * The main method
     *
     * @param args the command line arguments
     * @throws java.lang.Exception
     */
    public static void main(String... args) throws Exception {

        String filename = "";

        if (args.length != 1) {
            System.out.println("Error : mention the filename as an argument");
            return;
        }

        filename = args[0];

        File file = new File(filename);

        if (!file.exists()) {
            System.out.println("The file you supplied doesn't seem to exist!");
        } else {

            File inputFile = new File(filename);

            BufferedReader br = new BufferedReader(new FileReader(inputFile));

            String line;

            int lineCount = 0;
            int nodeCount = 0;

            while ((line = br.readLine()) != null) {

                try {

                    String[] input = line.split("\\t");

                    if (lineCount == 0) {
                        setUpVertexList(input);

                    } else {
                        nodeCount++;
                        for (int i = 0; i < input.length; i++) {

                            if (input[i].equalsIgnoreCase("1")) {
                                nodes.add(new Pair(lineCount, i));
                            }
                        }
                    }

                } catch (Exception e) {
                    System.out.println(line);
                    System.out.println(e.getMessage());
                }

                lineCount++;
            }

            try {
                br.close();
            } catch (Exception e) {
                System.out.println("Failed to close buffered reader");
            }

            dfs();

            topologicalSort();

            strongConnectedNodes(nodeCount, nodes);
        }
    }

    /*
    * Setup vertexes list
     */
    private static void setUpVertexList(String[] inputs) {

        vertexList = new ArrayList<>();

        for (int i = 1; i < inputs.length; i++) {
            vertexList.add(new Vertex(i, inputs[i]));
        }
    }

    /**
     * Depth first search algorithm coloring the node.
     */
    private static void dfs() {

        for (Vertex vertex : vertexList) {
            vertex.setColor(Color.WHITE);
        }

        time = 0;

        for (Vertex vertex : vertexList) {
            if (vertex.getColor() == Color.WHITE) {
                dfsVisit(vertex);
            }
        }

        printVertexes();
    }

    /**
     * Depth first search visit algorithm
     *
     * @param vertex
     */
    private static void dfsVisit(Vertex vertex) {
        vertex.setColor(Color.GRAY);

        time++;

        vertex.setStartTime(time);

        for (Vertex adjacentVertex : getAdjacentVertex(vertex)) {

            if (adjacentVertex.getColor() == Color.WHITE) {

                dfsVisit(adjacentVertex);
            }

        }

        vertex.setColor(Color.BLACK);

        time++;

        vertex.setFinishTime(time);
    }

    /**
     * get the Adjacent Vertex to the given vertex
     *
     * @param vertex
     * @return Adjacent Vertex
     */
    private static Iterable<Vertex> getAdjacentVertex(Vertex vertex) {
        ArrayList<Vertex> adjcentVertexes = new ArrayList();

        for (Pair<Integer, Integer> pair : nodes) {

            if (pair.key == vertex.id) {
                adjcentVertexes.add(getVertexById(pair.value));
            }

        }

        return adjcentVertexes;
    }

    /**
     * Get vertex by id.
     *
     * @param id
     * @return the vertex object.
     */
    private static Vertex getVertexById(int id) {

        Vertex selectedVertex = new Vertex();

        for (Vertex vertex : vertexList) {
            if (vertex.id == id) {
                selectedVertex = vertex;
            }
        }

        return selectedVertex;
    }

    /**
     * Print vertexes.
     */
    private static void printVertexes() {

        System.out.println("Print vetex");

        for (Vertex vertex : vertexList) {
            System.out.println(vertex.toString());
        }
    }

    /**
     * Display topological sorted list.
     */
    private static void topologicalSort() {

        System.out.println("Topological sorted");

        Collections.sort(vertexList, new Comparator<Vertex>() {

            @Override
            public int compare(Vertex a1, Vertex a2
            ) {
                return a2.finishTime - a1.finishTime;
            }
        }
        );

        for (Vertex vertex : vertexList) {
            System.out.print(vertex.toName());
        }

        System.out.println();
    }

    /**
     * Find strongly connected nodes.
     */
    private static void strongConnectedNodes(int numberOfNodes, List<Pair<Integer, Integer>> nodes) {

        System.out.println("Songly Connected Components");

        SCC scc = new SCC(numberOfNodes, nodes);

        scc.printStrongConnectedGraph();
    }

    /*
    The strongest connected nodes.
     */
    public static class SCC {

        /*
         The matrix with adjacent values.
         */
        private int[][] adjacencyMatrix = null;

        /*
         The node of leaders.
         */
        private int[] leaderNode = null;

        /*
         The number of nodes.
         */
        private int number_of_nodes = 0;

        /*
        /    The leader
         */
        private static int leader = 0;

        /*
         Construct the SCC 
         */
        public SCC(int numberofNodes, List<Pair<Integer, Integer>> nodes) {

            number_of_nodes = numberofNodes;

            adjacencyMatrix = new int[numberofNodes + 1][numberofNodes + 1];

            for (Pair<Integer, Integer> pair : nodes) {

                adjacencyMatrix[pair.key][pair.value] = 1;
            }

            leaderNode = new int[numberofNodes + 1];

        }

        /*
         Print strong connected graph
         */
        public void printStrongConnectedGraph() {

            Map<Integer, Integer> finishMap;

            finishMap = setStrongConnectedComponent();

            Map<Integer, List<Integer>> list = new HashMap<>();

            for (int i = 1; i < leaderNode.length; i++) {

                int sccGroup = finishMap.get(leaderNode[i]);

                if (list.containsKey(sccGroup)) {
                    List<Integer> l2 = list.get(sccGroup);

                    l2.add(i);
                } else {
                    List<Integer> valueList = new ArrayList<>();

                    valueList.add(i);

                    list.put(sccGroup, valueList);
                }
            }

            for (Map.Entry<Integer, List<Integer>> entry : list.entrySet()) {

                List<Integer> value = entry.getValue();

                for (int i = 0; i < value.size(); i++) {

                    if ((i + 1) == value.size()) {
                        System.out.print(value.get(i));
                    } else {
                        System.out.print(value.get(i) + ",");
                    }
                }

                System.out.println("");
            }

        }

        /**
         * Set strongly connected Components.
         */
        private Map<Integer, Integer> setStrongConnectedComponent() {

            Map<Integer, Integer> finishMap = new HashMap<>();

            int explore[] = new int[number_of_nodes + 1];

            int finishingNodes[] = new int[number_of_nodes + 1];

            for (int i = number_of_nodes; i > 0; i--) {
                if (explore[i] == 0) {
                    DFS(false, adjacencyMatrix, i, explore, finishMap, finishingNodes);
                }
            }

            int reservev_matrix[][] = new int[number_of_nodes + 1][number_of_nodes + 1];

            for (int i = 1; i <= number_of_nodes; i++) {
                for (int j = 1; j <= number_of_nodes; j++) {

                    if (adjacencyMatrix[i][j] == 1) {

                        reservev_matrix[finishingNodes[j]][finishingNodes[i]] = adjacencyMatrix[i][j];

                    }
                }
            }

            for (int i = 1; i <= number_of_nodes; i++) {
                explore[i] = 0;
                leaderNode[i] = 0;
            }

            for (int i = number_of_nodes; i > 0; i--) {
                if (explore[i] == 0) {
                    leader = i;

                    DFS(true, reservev_matrix, i, explore, finishMap, finishingNodes);
                }
            }

            return finishMap;
        }

        /**
         * Depth first search
         *
         * @param isSecond
         * @param matrix
         * @param source
         * @param explore
         * @param finishMap
         * @param finishingNodes
         * @return
         */
        public Map<Integer, Integer> DFS(boolean isSecond, int matrix[][],
                int source, int explore[], Map<Integer, Integer> finishMap,
                int finishingNodes[]) {

            Stack<Integer> stack = new Stack<>();

            explore[source] = 1;

            stack.push(source);

            int i;

            int element;

            if (isSecond) {
                leaderNode[finishMap.get(source)] = leader;
            }

            while (!stack.isEmpty()) {

                element = stack.peek();

                i = 1;

                while (i <= number_of_nodes) {

                    if (matrix[element][i] == 1 && explore[i] == 0) {

                        if (isSecond) {
                            if (leaderNode[finishMap.get(i)] == 0) {

                                leaderNode[finishMap.get(i)] = leader;
                            }
                        }

                        stack.push(i);

                        explore[i] = 1;

                        element = i;

                        i = 1;

                        continue;
                    }

                    i++;
                }

                int poped = stack.pop();

                int time = finishing_time++;

                finishingNodes[poped] = time;

                finishMap.put(time, poped);

            }

            return finishMap;
        }
    }

    /**
     * Class contains the pair of key and values.
     *
     * @param <T>
     * @param <U>
     */
    private static class Pair<T, U> {

        public final T key;

        public final U value;

        public Pair(T key, U value) {

            this.key = key;

            this.value = value;
        }
    }

    /**
     * The class Vertex
     */
    private static class Vertex {

        public int id;

        public String name;

        public int shortestTime;

        public int finishTime;

        public Color Color;

        public Vertex() {

        }

        public Vertex(int id, String name) {

            this.id = id;

            this.name = name;
        }

        public void setStartTime(int time) {
            this.shortestTime = time;
        }

        public void setFinishTime(int time) {
            this.finishTime = time;
        }

        public void setColor(Color color) {
            this.Color = color;
        }

        public Color getColor() {
            return this.Color;
        }

        public String toName() {
            return new StringBuilder()
                    .append("|").append(name)
                    .append("|").toString();
        }

        @Override
        public String toString() {
            return new StringBuilder()
                    .append(name)
                    .append(":").append(shortestTime)
                    .append("/").append(finishTime).toString();
        }
    }

    /**
     * The color class
     */
    public enum Color {

        /**
         * None color.
         */
        NONE(0),
        /**
         * White color
         */
        WHITE(1),
        /**
         * Gray color
         */
        GRAY(2),
        /**
         * Black color
         */
        BLACK(3);

        /**
         * The value
         */
        private final int value;

        /**
         * Setting the color value
         */
        private Color(int value) {
            this.value = value;
        }

        /**
         * Returns the value.
         *
         * @return
         */
        public int getValue() {
            return value;
        }
    }

}
