import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FairLockExample {
    private static final Lock lock = new ReentrantLock(true); // true 表示开启公平锁
    private static int currentNumber = 1; // 当前要打印的数字
    private static final int MAX_COUNT = 10; // 每个线程打印的次数
    // 创建三个 Condition 对象，用于线程间的通信
    private static final Condition condition1 = lock.newCondition();
    private static final Condition condition2 = lock.newCondition();
    private static final Condition condition3 = lock.newCondition();

    public static void main(String[] args) {
        // 创建三个线程
        Thread thread1 = new Thread(new Printer(1, condition1, condition2));
        Thread thread2 = new Thread(new Printer(2, condition2, condition3));
        Thread thread3 = new Thread(new Printer(3, condition3, condition1));

        thread1.start();
        thread2.start();
        thread3.start();
    }

    static class Printer implements Runnable {
        private final int number;
        private final Condition currentCondition; 
        private final Condition nextCondition; 
    
        public Printer(int number, Condition currentCondition, Condition nextCondition) {
            this.number = number;
            this.currentCondition = currentCondition;
            this.nextCondition = nextCondition;
        }
    
        @Override
        public void run() {
            try {
                for (int i = 0; i < MAX_COUNT; i++) {
                    lock.lock(); // 获取锁
                    try {
                        // currentNumber一开始设置为1，所以只有线程1获取锁后才能打印，其他线程如果先走到这里，就会挂起然后释放锁
                        while (currentNumber != number) {
                            currentCondition.await(); // condition的awat()方法会挂起线程并释放获得的锁
                        }
                        System.out.println(number);
                        currentNumber = (currentNumber % 3) + 1;
                        nextCondition.signal(); // 唤醒下一个线程
                    } finally {
                        lock.unlock(); // 释放锁
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } 
    }
}

