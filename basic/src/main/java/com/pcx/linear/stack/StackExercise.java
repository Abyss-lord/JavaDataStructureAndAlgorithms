package com.pcx.linear.stack;

import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

/**
 * @className: StackExercise
 * @description: Finish
 * @author: panchenxi
 * @date: 2024/6/18
 * @version: 1.0
 */
public class StackExercise {
    private StackExercise() {

    }

    static ImmutableMap<Integer, Character> map;

    static {
        ImmutableMap.Builder<Integer, Character> builder = ImmutableMap.builder();
        builder.put(0, '0');
        builder.put(1, '1');
        builder.put(2, '2');
        builder.put(3, '3');
        builder.put(4, '4');
        builder.put(5, '5');
        builder.put(6, '6');
        builder.put(7, '7');
        builder.put(8, '8');
        builder.put(9, '9');
        builder.put(10, 'A');
        builder.put(11, 'B');
        builder.put(12, 'C');
        builder.put(13, 'D');
        builder.put(14, 'E');
        builder.put(15, 'F');
        map = builder.build();
    }

    /**
     * 检查数字是否为2的幂
     *
     * @param num 要检查的数字
     * @return 如果是2的幂返回True，否则返回False。
     */
    public static boolean isPowerOfTwo(int num) {

        if (num < 0) {
            return false;
        }
        return Objects.equals((num & (num - 1)), 0);
    }

    /**
     * 将十进制数转换为其他进制数
     *
     * @param num 要转换的整数。必须是非负整数。
     * @param n   要转换到的进制。必须在2到16之间（包括2和16）。
     * @return 一个表示该整数在指定进制下的字符串。
     */
    public static String divideByNum(int num, int n) {

        Stack<Character> stack = new Stack<>();
        int rem;
        while (num > 0) {
            // 计算余数
            rem = num % n;
            stack.push(map.get(rem));
            num /= n;
        }

        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }

        return sb.toString();
    }

    /**
     * 检查括号字符串是否匹配。
     *
     * @param expression 要检查的括号字符串
     * @return 如果括号匹配，返回 True，否则返回 False
     */
    public static boolean bracketMatching(String expression) {

        if (Objects.isNull(expression)) {
            return false;
        }
        expression = expression.trim();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (isLeftBracket(c)) {
                stack.push(c);
            } else if (isRightBracket(c)) {
                if (stack.isEmpty()) {
                    return false;
                }
                char requiredBracket = getMatchedBracket(c);
                if (stack.peek() != requiredBracket) {
                    return false;
                } else {
                    stack.pop();
                }
            }

        }
        return stack.isEmpty();
    }


    /**
     * 将中缀表达式转换为后缀表达式。
     *
     * @param expression 中缀表达式字符串
     * @return 后缀表达式字符串
     */
    public static String convertInfixToPostfix(String expression) {

        if (!bracketMatching(expression) || expression.length() < 3) {
            throw new IllegalArgumentException("expression must be a valid expression");
        }

        Stack<Character> opStack = new Stack<>();

        List<String> postfix = new ArrayList<>();
        String split = "|";
        int idx = 0;
        while (idx < expression.length()) {
            char c = expression.charAt(idx);
            if (Character.isDigit(c)) {
                StringBuilder sb = new StringBuilder();
                while (idx < expression.length() && Character.isDigit(expression.charAt(idx))) {
                    sb.append(expression.charAt(idx));
                    idx++;
                }

                postfix.add(sb.toString());
                postfix.add(split);
            } else {
                if (isLeftBracket(c)) {
                    opStack.push(c);
                } else if (isOperator(c)) {
                    while (!opStack.isEmpty() && getPrecedence(opStack.peek()) > getPrecedence(c)) {
                        postfix.add(String.valueOf(opStack.pop()));
                    }

                    opStack.push(c);
                } else if (isRightBracket(c)) {
                    char lastOp = opStack.pop();
                    while (!Objects.equals(lastOp, '(')) {
                        postfix.add(String.valueOf(lastOp));
                        lastOp = opStack.pop();
                    }
                }
                idx++;
            }
        }

        while (!opStack.isEmpty()) {
            postfix.add(String.valueOf(opStack.pop()));
        }

        return String.join("", postfix);
    }

    /**
     * 从后缀表达式计算结果。
     *
     * @param expression 后缀表达式字符串
     * @return 计算结果
     */
    public static float calculateValueFromPostfix(String expression) {
        if (Objects.isNull(expression) || expression.length() < 5) {
            return Float.POSITIVE_INFINITY;
        }

        Stack<Float> stack = new Stack<>();
        int idx = 0;
        while (idx < expression.length()) {
            char c = expression.charAt(idx);
            StringBuilder sb = new StringBuilder();
            if (Character.isDigit(c)) {
                while (!Objects.equals(c, '|')) {
                    sb.append(c);
                    c = expression.charAt(++idx);
                }
                stack.push(Float.valueOf(sb.toString()));
            } else if (isOperator(c)) {
                float val1 = stack.pop();
                float val2 = stack.pop();
                float res = calculate(val1, val2, c);
                stack.push(res);
                idx++;
            } else {
                idx++;
            }
        }

        return stack.isEmpty() ? Float.POSITIVE_INFINITY : stack.pop();
    }

    private static boolean isLeftBracket(char c) {
        return '(' == c || '[' == c || '{' == c;
    }

    private static boolean isRightBracket(char c) {
        return ')' == c || ']' == c || '}' == c;
    }

    private static char getMatchedBracket(char bracket) {
        if (isLeftBracket(bracket)) {
            return getMatchedRightBracket(bracket);
        } else {
            return getMatchedLeftBracket(bracket);
        }
    }

    private static char getMatchedLeftBracket(char rightBracket) {
        if (')' == rightBracket) {
            return '(';
        } else if (']' == rightBracket) {
            return '[';
        } else {
            return '{';
        }
    }

    private static char getMatchedRightBracket(char leftBracket) {
        if ('(' == leftBracket) {
            return ')';
        } else if ('[' == leftBracket) {
            return ']';
        } else {
            return '}';
        }
    }

    private static int getPrecedence(char op) {
        if (Objects.equals(op, '+') || Objects.equals(op, '-')) {
            return 1;
        } else if (Objects.equals(op, '*') || Objects.equals(op, '/')) {
            return 2;
        }
        return 0;
    }

    private static boolean isOperator(char c) {
        switch (c) {
            case '+':
            case '-':
            case '*':
            case '/':
                return true;
            default:
                return false;
        }
    }

    private static float calculate(float m, float n, char op) {
        if (Objects.equals(op, '+')) {
            return m + n;
        } else if (Objects.equals(op, '*')) {
            return m * n;

        } else if (Objects.equals(op, '-')) {
            return n - m;
        } else {
            return n / m;
        }
    }

}
