package com.zhunzhong;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolTest {

    ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Test
    public void testSynchronization() {
        List<Integer> numbers= Arrays.asList(1,2,3,4);
        numbers.forEach(i -> {
            if(i==3){
                return;
            }
            System.out.println(i);
        });
        System.out.println("aa");
        Counter counter = new Counter();
        try {
            for (int i = 0; i < 100000; i++) {
                executorService.submit(counter::increase);
                executorService.submit(counter::reduce);
            }
            executorService.shutdown();
            if(!executorService.awaitTermination(1000, TimeUnit.SECONDS)){
                executorService.shutdownNow();
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(counter.getNum());
        System.out.println("==============================");
    }

    static class Counter {

        private int num;


        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public synchronized void increase() {
            num++;
        }

        public synchronized void reduce() {
            num--;
        }
    }


}
