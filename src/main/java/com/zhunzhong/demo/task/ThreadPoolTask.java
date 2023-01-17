package com.zhunzhong.demo.task;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class ThreadPoolTask {

    private final int consumerThreadMin = 2;

    private final int consumerThreadMax = 3;

    private final int keepAliveTime = 5000;

    /**
     * 来个demo：corePoolSize=3；maximumPoolSize=5；workQueue=5；
     *
     * （1）当n<=3：直接占用核心线程池；
     *
     * （2）当n=4~8：3个核心线程占满、1个请求进入workQueue；
     *
     * （3）当n=9~10：3个核心线程占满、5个请求进入workQueue、剩下的请求新开线程响应；
     *
     * （4）当n>10：3个核心线程占满、5个请求进入workQueue、2个请求被新开线程响应、其余的请求直接拒绝
     */
    private final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
            consumerThreadMin,
            consumerThreadMax,
            keepAliveTime,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(1),
            new ThreadPoolExecutor.CallerRunsPolicy());

    public void submit(Runnable task) {
        this.threadPoolExecutor.submit(task);
    }

    public void test(int count) {
        log.info("start ===============threadName{} ,threadId{} run .",
                Thread.currentThread().getName(), Thread.currentThread().getId());
        CountDownLatch countDownLatch = new CountDownLatch(count);
        for (int i = 0; i < count; i++) {
            log.info("****** {} ************",i+1);
            this.submit(new Job(countDownLatch));
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }

    class Job implements Runnable {

        private CountDownLatch countDownLatch;

        public Job(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            log.info("threadName{} ,threadId{} start run .", Thread.currentThread().getName(), Thread.currentThread().getId());
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            countDownLatch.countDown();
            log.info("threadName{} ,threadId{} run end .", Thread.currentThread().getName(), Thread.currentThread().getId());
        }
    }


}
