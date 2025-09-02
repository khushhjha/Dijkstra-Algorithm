// import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

// import javax.crypto.spec.DESKeySpec;


class BfsData{
    int cd;
    String psf;
    BfsData(int cd, String psf){
        this.cd = cd;
        this.psf = psf;
    }
}

class DijkstraData implements Comparable<DijkstraData>{
    int cd;
    String psf;
    int csf;
    DijkstraData(int cd, String psf, int csf){
        this.cd = cd;
        this.psf = psf;
        this.csf = csf;
    }
    
    @Override
    public int compareTo(DijkstraData o) {
        return Integer.compare(this.csf, o.csf);
    }
}


class dijkstraalgo{
    static Integer minCost = Integer.MAX_VALUE;
    static Integer maxCost = Integer.MIN_VALUE;
    public static void main(String[] args) {
        int[][] edges = {{0,1,10},{0,2,2},{1,3,30},{2,3,5},{3,4,4},{4,5,12},
            {4,6,18},{6,7,45},{5,7,23}};
        int n = 8;
        int[][] graph = buildGraph(edges,n);
        int src = 0;
        int des = 7;

        String[] data = dijkstra(n,graph, src, des);
        System.out.println("Path To Min Cost "+data[0]);
        System.out.println("Cost To Min Cost Path "+data[1]);
    }

    public static String[] dijkstra(int n , int[][] graph, int src, int des){
        PriorityQueue<DijkstraData> queue = new PriorityQueue<>();
        queue.add(new DijkstraData(src, src+"", 0));

        int[] visited = new int[n];
        String[] ans = new String[2];
        ans[0] = "";
        ans[1] = Integer.MAX_VALUE + "";

        while(!queue.isEmpty()){
            DijkstraData obj = queue.poll();
            int cd = obj.cd;
            int csf = obj.csf;
            String psf = obj.psf;

            if(visited[cd] == 1) continue; // already finalized
            visited[cd] = 1;

            if(cd == des){
                ans[0] = psf;
                ans[1] = csf+"";
                return ans; // in Dijkstra, first time we reach dest => shortest path
            }

            int[] nbrsArray = graph[cd];
            for(int i = 0; i< nbrsArray.length; i++){
                if(nbrsArray[i] != 0 && visited[i] == 0){
                    queue.add(new DijkstraData(i, psf+"->"+i, csf + nbrsArray[i]));
                }
            }
        }
        return ans;
    }



    public static void bfs(int[][] graph,int src,int n){
        Queue<BfsData> queue = new LinkedList<>();
        BfsData initialData = new BfsData(src,src+"");
        int[] visited = new int[n];
        queue.add(initialData);
        while (!queue.isEmpty()) {
            BfsData removedData = queue.poll();
            int cd = removedData.cd;
            String psf = removedData.psf;
            visited[cd] = 1;
            System.out.println(cd+"###"+psf);
            int[] nbrsArray = graph[cd];
            for(int i = 0; i< nbrsArray.length; i++){
                if(nbrsArray[i] != 0 && visited[i]== 0){
                    BfsData curr = new BfsData(i,psf+"->"+i);
                    queue.add(curr);
                    visited[i] = 1;
                }
            }
        }
        
    }



     public static void multiSolver(int[][] graph, int src, int des, int[] visited,String psf,
                    StringBuilder longestPath, StringBuilder shortestPath, int csf
                        ){
        if(src == des){
            if(csf < minCost){
                minCost = csf;
            }
            if(csf > maxCost) maxCost = csf;
            System.out.println(psf);
            if(psf.length() > longestPath.length()){
                longestPath.replace(0, longestPath.length(), psf);
            }
            if(psf.length() < shortestPath.length() || shortestPath.length() == 0){
                shortestPath.replace(0, shortestPath.length(), psf);
            }
            return;
        }
        visited[src] = 1;
        int[] nbrsArray = graph[src];
        for(int i = 0; i< nbrsArray.length; i++){
            if(nbrsArray[i] != 0 && visited[i] == 0){
                multiSolver(graph, i, des, visited,psf+"->"+i,longestPath,shortestPath,csf+nbrsArray[i]);
            }
        }
        visited[src] = 0; 
    }

    public static boolean hasPath(int[][] graph, int src, int des, int[] visited){
        if(src == des) return true;
        visited[src] = 1;
        int[] nbrsArray = graph[src];
        for(int i = 0; i< nbrsArray.length; i++){
            if(nbrsArray[i] != 0 && visited[i] == 0){
                if(hasPath(graph, i, des, visited)){
                    return true;
                }
            }
        } 
        return false;
    }

    public static int[][] buildGraph(int[][] edges, int n){
        int[][] graph = new int[n][n];
        for(int[] edge : edges){
            int src = edge[0];
            int des = edge[1];
            int wt = edge[2];
            graph[src][des] = wt;
            graph[des][src] = wt;
        }
        return graph;
    }
}
