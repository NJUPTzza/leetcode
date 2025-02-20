package main

func spiralOrder(matrix [][]int) []int {
	rows, cols := len(matrix), len(matrix[0])
	visited := make([][]bool, rows)
	for i := 0; i < rows; i++ {
		visited[i] = make([]bool, cols)
	}

	var (
		total = rows * cols
		ans = make([]int, total)
		row, col = 0, 0
		directions = [][]int{[]int{0, 1}, []int{1, 0}, []int{0, -1}, []int{-1, 0}}
		directionIndex = 0
	)

	for i := 0; i < total; i++ {
		ans[i] = matrix[row][col]
		visited[row][col] = true
		nextRow, nextCol := row + directions[directionIndex][0], col + directions[directionIndex][1]
		if nextRow < 0 || nextRow >= rows || nextCol < 0 || nextCol >= cols {
			directionIndex = (directionIndex + 1) % 4
		}
		row += directions[directionIndex][0]
		col += directions[directionIndex][1]
	}

	return ans
}