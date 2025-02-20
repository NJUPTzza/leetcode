package main

func orangesRotting(grid [][]int) int {
    m, n := len(grid), len(grid[0])
	total := 0
	for int i = 0; i < m; i++ {
		for int j = 0; j < n; j++ {
			if grid[i][j] >= 0 {
				total++
			}
			if grid[i][j] == 2 {
				
			}
		}
	}
}