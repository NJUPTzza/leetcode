#include <vector>
#include <string>

using namespace std;

class Solution {
    public:
        string decodeString(string s) {
            vector<string> stack;
            size_t ptr = 0;

            while (ptr < s.size()) {
                char cur = s[ptr];
                if (isdigit(cur)) {
                    // 获取一个数字并进栈
                    string digits = getDigits(s, ptr);
                    stack.push_back(digits);
                } else if (isalpha(cur) || cur == '[') {
                    // 获取一个字母或者左括号并进栈
                    stack.push_back(string(1, s[ptr++]));
                } else {
                    ptr++;
                    vector<string> sub;
                    while (stack.back() != "[") {
                        sub.push_back(stack.back());
                        stack.pop_back();
                    }
                    reverse(sub.begin(), sub.end());
                    // 左括号出栈
                    stack.pop_back();
                    // 此时栈顶为当前 sub 对应的字符串应该出现的次数
                    int repTime = stoi(stack.back());
                    stack.pop_back();
                    string t, o = getString(sub);
                    // 构造字符串
                    while (repTime--) t += o;
                    // 将构造好的字符串入栈
                    stack.push_back(t);
                }
            }
        }

        string getDigits(string &s, size_t &ptr) {
            string ret = "";
            while (isdigit(s[ptr])) {
                ret.push_back(s[ptr++]);
            }
            return ret;
        }

        string getString(vector<string> &v) {
            string ret;
            for (const auto &s: v) {
                ret += s;
            }
            return ret;
        }
    };