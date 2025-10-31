package test.java.chatgpt;

import main.java.Stack;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class StackTest {

    @Test
    @DisplayName("New stack is empty with size 0 and not full")
    void newStackBasics() {
        Stack s = new Stack(3);
        assertTrue(s.isEmpty());
        assertEquals(0, s.size());
        assertFalse(s.isFull());
    }

    @Test
    @DisplayName("Push increases size and peek shows last pushed")
    void pushAndPeek() {
        Stack s = new Stack(5);
        s.push(10);
        assertEquals(1, s.size());
        assertEquals(10, s.peek());

        s.push(20);
        assertEquals(2, s.size());
        assertEquals(20, s.peek());

        s.push(30);
        assertEquals(3, s.size());
        assertEquals(30, s.peek());
    }

    @Test
    @DisplayName("Pop returns in LIFO order and decreases size")
    void popLifo() {
        Stack s = new Stack(5);
        s.push(10);
        s.push(20);
        s.push(30);

        assertEquals(30, s.pop());
        assertEquals(2, s.size());
        assertEquals(20, s.pop());
        assertEquals(1, s.size());
        assertEquals(10, s.pop());
        assertEquals(0, s.size());
        assertTrue(s.isEmpty());
    }

    @Test
    @DisplayName("isFull becomes true at capacity; push beyond capacity throws")
    void overflowAtCapacity() {
        Stack s = new Stack(2);
        s.push(1);
        s.push(2);
        assertTrue(s.isFull());
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> s.push(3));
        assertEquals("Stack is full", ex.getMessage());
        assertEquals(2, s.size());
        assertEquals(2, s.peek()); // top unchanged
    }

    @Test
    @DisplayName("Pop from empty throws with correct message")
    void underflowOnPop() {
        Stack s = new Stack(3);
        IllegalStateException ex = assertThrows(IllegalStateException.class, s::pop);
        assertEquals("Stack is empty", ex.getMessage());
    }

    @Test
    @DisplayName("Peek from empty throws with correct message")
    void underflowOnPeek() {
        Stack s = new Stack(3);
        IllegalStateException ex = assertThrows(IllegalStateException.class, s::peek);
        assertEquals("Stack is empty", ex.getMessage());
    }

    @Test
    @DisplayName("Interleaved push/pop maintains correct top and size")
    void interleavedOps() {
        Stack s = new Stack(4);
        s.push(5);           // [5]
        s.push(7);           // [5,7]
        assertEquals(7, s.pop()); // [5]
        s.push(9);           // [5,9]
        s.push(11);          // [5,9,11]
        assertEquals(11, s.peek());
        assertEquals(3, s.size());
        assertEquals(11, s.pop()); // [5,9]
        assertEquals(9, s.pop());  // [5]
        assertEquals(5, s.pop());  // []
        assertTrue(s.isEmpty());
    }

    @Test
    @DisplayName("Zero-capacity stack is immediately full and cannot push")
    void zeroCapacityBehavior() {
        Stack s = new Stack(0);
        assertTrue(s.isFull());
        assertTrue(s.isEmpty()); // size() == 0, top == -1
        IllegalStateException ex = assertThrows(IllegalStateException.class, () -> s.push(1));
        assertEquals("Stack is full", ex.getMessage());
        assertEquals(0, s.size());
    }

    @Test
    @DisplayName("Negative size constructor throws NegativeArraySizeException")
    void negativeCapacityRejected() {
        assertThrows(NegativeArraySizeException.class, () -> new Stack(-3));
    }

    @Test
    @DisplayName("Filling to capacity keeps correct top value")
    void topAtCapacity() {
        Stack s = new Stack(3);
        s.push(1);
        s.push(2);
        s.push(3);
        assertTrue(s.isFull());
        assertEquals(3, s.peek());
        assertEquals(3, s.size());
    }
}
