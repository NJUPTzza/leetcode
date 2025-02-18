from typing import List
import sys

class Solution:
    def coinChange(self, coins: List[int], amount : int) -> int:
        dp = [sys.maxsize - 1] * (amount + 1)
        dp[0] = 0

        for i in range(len(coins)):
            for j in range(coins[i], amount + 1):
                dp[j] = min(dp[j], dp[j - coins[i]] + 1)
        
        return -1 if dp[amount] == sys.maxsize - 1 else dp[amount]