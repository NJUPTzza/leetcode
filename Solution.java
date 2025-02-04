import java.util.HashMap;
import java.util.Map;

class Solution {
    public int subarraySum(int[] nums, int k) {
        int n = nums.length;
        int pre = 0; 
        int ans = 0;
        Map<Integer, Integer> mp = new HashMap<>();
        mp.put(0, 1);
        for (int i = 0; i < n; i++) {
            pre += nums[i];
            if (mp.containsKey(pre - k)) {
                ans += mp.get(pre - k);
            }
            mp.put(pre, mp.getOrDefault(pre, 0) + 1);
        }
        return ans;
    }
}