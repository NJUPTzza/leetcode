import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class Solution {
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int cur = 0, maxLen = 0, ans = 0;
        for (int num : nums) {
            set.add(num);
        }
        for (int i : set) {
            if (!set.contains(i - 1)) {
                cur = i;
                maxLen = 1;
            }
            while (set.contains(i + 1)) {
                maxLen++;
                i++;
            }
            ans = Math.max(maxLen, ans);
        }
        return ans;
    }
}