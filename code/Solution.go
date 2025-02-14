package main

func canJump(nums []int) bool {
	var cover int
	n := len(nums)
    for i := 0; i <= cover; i++ {
		cover = max(cover, i + nums[i])
		if cover >= n - 1 {
			return true
		}
	} 
	return false;
}