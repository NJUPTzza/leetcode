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