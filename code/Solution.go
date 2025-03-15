package main

func twoSum(nums []int, target int) []int {
    hashtable := map[int]int{}
	for idx, val := range nums {
		if p, ok := hashtable[target - val]; ok {
			return []int{p, idx}
		}
		hashtable[val] = idx
	}
	return nil
}