var numIslands = function(grid) {
    if (grid === null || grid.length === 0) {
        return 0;
    }

    let H = grid.length;
    let W = grid[0].length;
    let ans = 0;

    for (let i = 0; i < H; i++) {
        for (let j = 0; j < W; j++) {
            if (grid[i][j] === '1') {
                ans++;
                dfs(i, j);
            }
        }
    }

    return ans;
}

function dfs(h, w) {
    if (h < 0 || h >= H || w < 0 || w >= W || grid[h][w] === '0') {
        return;
    }

    grid[h][w] = '0';
    dfs(h + 1, w);
    dfs(h - 1, w);
    dfs(h, w + 1);
    dfs(H, w - 1);
}