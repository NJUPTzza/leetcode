# hot100
## 哈希
### 1.两数之和
``` java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[] { map.get(target - nums[i]), i };
            }
            map.put(nums[i], i);
        }
        return new int[0];
    }
}
```

### 49.字母异位词分组
``` java
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        // 用排序好的字符串作为map的key值，value就是各种排序
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            char[] chars = str.toCharArray();
            Arrays.sort(chars);
            // 这里不能用 chars.toString() ，不然返回的是对象的类名加哈希码（内存地址的某种表示形式）。
            String key = new String(chars);
            List<String> list = map.getOrDefault(key, new ArrayList<String>());
            list.add(str);
            map.put(key, list);
        }
        // map.values() 返回的就是一个数组，map的每个value就是数组的一个元素
        return new ArrayList<>(map.values());
    }
}
```

### 128.最长连续序列
``` java
class Solution {
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int cur = 0, maxLen = 0, ans = 0;
        // 讲数组中所有元素放入Set中
        for (int num : nums) {
            set.add(num);
        }
        for (int i : set) {
            // 检测是否是连续序列的第一个值（最小值），如果是就重新开始计数
            if (!set.contains(i - 1)) {
                cur = i;
                maxLen = 1;
            }
            // 如果是连续序列的最小值，则开始往下查找连续多少个
            while (set.contains(cur + 1)) {
                maxLen++;
                cur++;
            }
            ans = Math.max(maxLen, ans);
        }
        return ans;
    }
}
```