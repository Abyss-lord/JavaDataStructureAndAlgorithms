package com.pcx.linear.stack;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Random;

/**
 * @className: TestStack
 * @description: Finish
 * @author: panchenxi
 * @date: 2024/6/18
 * @version: 1.0
 */
public class TestStack {
    public static final int DEFAULT_TEST_ROUND = 10;
    private static final Logger LOG = LoggerFactory.getLogger(TestStack.class);

    @Test
    public void testBasic() {
        Stack<Integer> s = new Stack<>();
        Stack<Integer> stackWithCapacity = new Stack<>(10);
        assert Objects.equals(stackWithCapacity.capacity(), 10);
        assert Objects.equals(stackWithCapacity.size(), 0);

        assert stackWithCapacity.isEmpty();
        for (int i = 0; i < DEFAULT_TEST_ROUND; i++) {
            Random random = new Random();
            int val = random.nextInt();
            s.push(val);
        }
        LOG.info("size={}, capacity={}", s.size(), s.capacity());
        assert Objects.equals(s.size(), DEFAULT_TEST_ROUND);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeCapacity() {
        Stack<Integer> stackWithCapacity = new Stack<>(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testOutOfRangeCapacity() {
        Stack<Integer> stackWithCapacity = new Stack<>(Short.MAX_VALUE + 1);
    }

    @Test(expected = RuntimeException.class)
    public void testPopInEmptyStack() {
        Stack<Integer> s = new Stack<>();
        s.pop();
    }

    @Test(expected = RuntimeException.class)
    public void testPeekInEmptyStack() {
        Stack<Integer> s = new Stack<>();
        s.peek();
    }

    @Test
    public void testPeek() {
        Stack<Integer> s = new Stack<>();
        for (int i = 0; i < DEFAULT_TEST_ROUND; i++) {
            s.push(i);
        }

        assert Objects.equals(s.peek(), DEFAULT_TEST_ROUND - 1);
        assert Objects.equals(s.size(), DEFAULT_TEST_ROUND);

    }

    @Test
    public void testPop() {
        Stack<Integer> s = new Stack<>();
        for (int i = 0; i < DEFAULT_TEST_ROUND; i++) {
            s.push(i);
        }
        assert Objects.equals(s.size(), DEFAULT_TEST_ROUND);
        LOG.info(s.getCurrentElements());
        assert Objects.equals(s.pop(), DEFAULT_TEST_ROUND - 1);
        LOG.info(s.getCurrentElements());
        assert Objects.equals(s.size(), DEFAULT_TEST_ROUND - 1);
    }

    @Test
    public void testPushAndPop() {
        Stack<Integer> s = new Stack<>();
        for (int i = 0; i < DEFAULT_TEST_ROUND; i++) {
            s.push(i);
            assert Objects.equals(s.pop(), i);
        }
    }

    @Test
    public void testDivideByNum() {
        assert Objects.equals(StackExercise.divideByNum(3, 2), "11");
        assert Objects.equals(StackExercise.divideByNum(7, 2), "111");
        assert Objects.equals(StackExercise.divideByNum(8, 8), "10");
        assert Objects.equals(StackExercise.divideByNum(15, 8), "17");
        assert Objects.equals(StackExercise.divideByNum(31, 16), "1F");
        assert Objects.equals(StackExercise.divideByNum(11, 16), "B");
    }

    @Test
    public void testBracketMatching() {
        // 输出: True
        assert StackExercise.bracketMatching("(a+b)(c*d)func()");
        // 输出: False
        assert !StackExercise.bracketMatching("()][()");
        // 输出: True
        assert StackExercise.bracketMatching("(){}[]");
        // 输出: False
        assert !StackExercise.bracketMatching("(){)[}");
        // 输出: True
        assert StackExercise.bracketMatching("()");
        // 输出: True
        assert StackExercise.bracketMatching("()[]{}");
        // 输出: False
        assert !StackExercise.bracketMatching("(]");
        // 输出: False
        assert !StackExercise.bracketMatching("([)]");
        // 输出: True
        assert StackExercise.bracketMatching("{[]}");
    }

    @Test
    public void testConvertInfixToPostfix() {
        assert StackExercise.calculateValueFromPostfix(StackExercise.convertInfixToPostfix(
                "( 1 + 3 ) * ( 5 + 51 ) / 3 + (23 + 3) + (3 - 2) * 5")) - 105.66666 < 0.1;
        assert StackExercise.calculateValueFromPostfix(StackExercise.convertInfixToPostfix("( 1 +" +
                " 3 ) * ( 5 + 51 )")) - 224 < 0.1;
        assert StackExercise.calculateValueFromPostfix(StackExercise.convertInfixToPostfix("(4 + " +
                "(13 / 5))")) - 6.6 < 0.1;
        assert StackExercise.calculateValueFromPostfix(StackExercise.convertInfixToPostfix("((2 + 1) * 3)"))
                - 9 < 0.1;
        assert Math.abs(StackExercise.calculateValueFromPostfix(
                StackExercise.convertInfixToPostfix("((10 * (6 / ((9 + 3) * 11))) + 17) + 5")) - 22.454) < 0.1;

    }
}

