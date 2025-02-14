#include <vector>

using namespace std;

class Solution {
    public:
    vector<vector<int>> combinationSum(vector<int>& candidates, int target) {
        vector<vector<int>> ans;
        vector<int> path;
        dfs(ans, path, candidates, target, 0, 0);
        return ans;
    }

    void dfs(vector<vector<int>>& ans, vector<int>& path, vector<int>& candidates, int target, int sum, int idx) {
        if (sum == target) {
            ans.push_back(path);
            return;
        }

        for (int i = idx; i < candidates.size(); i++) {
            if (sum + candidates[i] > target) 
                break;
            path.push_back(candidates[i]);
            dfs(ans, path, candidates, target, sum + candidates[i], i);
            path.pop_back();
        }
    }
};