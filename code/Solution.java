import java.util.LinkedList;
import java.util.Queue;

class Solution {
        public int orangesRotting(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int total = 0;
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
                if (grid[i][j] > 0) 
                    total++;
                if (grid[i][j] == 2)
                    queue.add(new int[]{i, j});
            }

        int rotten = 0;
        int minutes = -1;
        int[] dx = new int[]{-1, 0, 1, 0};
        int[] dy = new int[]{0, 1, 0, -1};
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                minutes++;
                int[] cur = queue.poll();
                int x = cur[0];
                int y = cur[1];
                for (int i = 0; i < 4; i++) {
                    int newX = x + dx[i];
                    int newY = y + dy[i];
                    if (newX >= 0 && newX < m && newY >= 0 && newY < n && grid[newX][newY] == 1) {
                        grid[newX][newY] = 2;
                        rotten++;
                        queue.add(new int[]{newX, newY});
                    }
                }
            }
        }
        return rotten == total ? Math.min(minutes, 0) : -1;
    }
}