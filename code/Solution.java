import java.util.Stack;

class Solution {
    public boolean isValid(String s) {
        int n = s.length();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '(') {
                stack.push(')');
            } 
            else if (s.charAt(i) == '[') {
                stack.push('[');
            }
            else if (s.charAt(i) == '{') {
                stack.push('{');
            }
            else if (stack.isEmpty() || stack.peek() != s.charAt(i)) {
                return false;
            } 
            else {
                stack.pop();
            }
        }
        return stack.isEmpty();
    }
}