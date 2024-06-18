package com.pcx.linear.stack;

import java.util.Arrays;
import java.util.Objects;

/**
 * @className: Stack
 * @description: Finish
 * @author: panchenxi
 * @date: 2024/6/18
 * @version: 1.0
 */
public class Stack<T> {
    // 存储元素的数组
    private Object[] elements;
    // 栈中当前元素数量
    private int elementCnt;
    // 默认初始容量
    private static final int DEFAULT_CAPACITY = 16;
    // 默认扩容阈值
    private static final int DEFAULT_THRESHOLD = 4;


    /**
     * 默认构造方法，使用默认容量创建栈。
     */
    public Stack() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * 使用指定容量创建栈的构造方法。
     *
     * @param capacity 初始容量
     */
    public Stack(int capacity) {
        // 检查容量范围
        checkRange(capacity);
        // 初始化元素数组
        elements = new Object[capacity];
        // 初始化元素数量
        this.elementCnt = 0;
    }

    /**
     * 将元素入栈。
     *
     * @param item 要入栈的元素
     */
    public void push(T item) {
        addElement(item);
    }

    /**
     * 弹出栈顶元素并返回。
     *
     * @return 弹出的栈顶元素
     * @throws RuntimeException 如果栈为空则抛出异常
     */
    public T pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return getAndRemoveElement();
    }

    /**
     * 判断栈是否为空。
     *
     * @return 如果栈为空返回 true，否则返回 false
     */
    public synchronized boolean isEmpty() {
        return Objects.equals(size(), 0);
    }

    /**
     * 返回栈中元素的数量。
     *
     * @return 栈中元素的数量
     */
    public int size() {
        return elementCnt;
    }

    /**
     * 返回栈的容量。
     *
     * @return 栈的容量
     */
    public int capacity() {
        return elements.length;
    }

    /**
     * 查看栈顶元素但不移除。
     *
     * @return 栈顶元素
     * @throws RuntimeException 如果栈为空则抛出异常
     */
    public synchronized T peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        int size = size();
        return getItemAt(size - 1);
    }

    /**
     * 获取当前栈中所有元素的字符串表示。
     *
     * @return 包含当前栈中所有元素的字符串
     */
    public synchronized String getCurrentElements() {
        return Arrays.toString(elements);
    }


    private synchronized void addElement(T item) {
        int requireCapacity = size() + 1;
        if (!checkCapacity(requireCapacity)) {
            growth(requireCapacity);
        }
        elements[elementCnt++] = item;
    }

    private synchronized T getAndRemoveElement() {
        int size = size();
        return removeElementAt(size - 1);
    }

    private void checkRange(int n) {
        if (n < 0 || n >= Short.MAX_VALUE) {
            throw new IllegalArgumentException("range must between " + 0 + " and  " + Short.MAX_VALUE);
        }
    }

    private boolean growth(int requireCapacity) {
        int oldCapacity = elements.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - requireCapacity < 0) {
            newCapacity = requireCapacity;
        }

        elements = Arrays.copyOf(elements, newCapacity);
        return true;
    }

    private boolean checkCapacity(int requireCapacity) {
        int currentCapacity = elements.length;
        return currentCapacity - requireCapacity > DEFAULT_THRESHOLD;
    }

    private synchronized T getItemAt(int index) {
        if (index >= elementCnt) {
            throw new ArrayIndexOutOfBoundsException(index + " >= " + elementCnt);
        }

        return (T) elements[index];
    }

    private synchronized T removeElementAt(int index) {
        T oldValue = getItemAt(index);

        int numMoved = size() - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elements, index + 1, elements, index,
                    numMoved);
        }
        elements[--elementCnt] = null;

        return oldValue;
    }
}


class EmptyStackException extends RuntimeException {
    public EmptyStackException() {
    }
}