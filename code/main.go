package main

func subsets(nums []int) [][]int {
	var ans [][]int
	var path []int

    var backTrack func(startIndex int)

	backTrack = func(startIndex int) {
		tmp := make([]int, len(path))
        copy(tmp, path)
        ans = append(ans, tmp)

		if startIndex >= len(nums) {
			return
		}

		for i := startIndex; i < len(nums); i++ {
			path = append(path, nums[i])
			backTrack(i + 1)
			path = path[:len(path)-1]
		}
	}

	backTrack(0)
	return ans
}