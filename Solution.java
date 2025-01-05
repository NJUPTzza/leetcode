import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        List<List<Integer>> ans = new ArrayList<>();
        // 枚举 i
        for (int i = 0; i < n; i++) {
            // 如果 i 与上次重复，就直接省略，减少重复
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int k = n - 1;
            // 利用 j + k = -i 来寻找符合条件的 j 和 k
            int target = -nums[i];
            // 枚举 j
            for (int j = i + 1; j < n; j++) {
                // 如果 j 与上次重复，就直接省略，减少重复
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                // 保证 j 在 k 左边的同时不断将 k 左移，枚举 k
                while (j < k && nums[j] + nums[k] > target) {
                    k--;
                }
                // 如果 j 与 k 重叠，则说明这个 i 情况已经枚举完毕
                if (j == k) {
                    break;
                }
                // 如果等于，则加入ans中
                if (nums[j] + nums[k] == target) {
                    List<Integer> list = new ArrayList<>();
                    list.add(nums[i]);
                    list.add(nums[j]);
                    list.add(nums[k]);
                    ans.add(list);
                }
            }
        }
        return ans;
    }
}