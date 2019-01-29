package org.spring.springboot.dao;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.*;

@Component
@Configurable
@EnableScheduling

public class NewScheduledThreadPool {
    @Resource(name = "daoSupport")
    private DaoSupport dao;
    private final int AAA = 11;
    public static int i = 0;

    public static void main(String[] args) {
        final long begintime = System.nanoTime();
        // 创建一个线程池，用10个线程处理
//        ExecutorService pool = Executors.newCachedThreadPool();
//        for (int index = 0; index < 100; index++) {
//            Runnable run = new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        new Thread().sleep(100);  //模拟耗时操作
//                        System.out.println("[1]" + Thread.currentThread().getName());
//                    } catch (Exception e) {
//                    }
//                }
//            };
//           System.out.println(index);
//            pool.execute(run);
//            long endtime = System.nanoTime();
//            long costTime = (endtime - begintime)/1000;
//            System.out.println("[1] done!===="+costTime);
//        }
        final ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);

        for (int index = 0; index < 100; index++) {
            final int indexnn = index;
            Future<Integer> result = pool.schedule(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {


                    try {
                        new Thread().sleep(100);  //模拟耗时操作
                        System.out.println("[1]=====" + indexnn);

                        if(indexnn == 88){
                            return 22;
                        }
                    } catch (Exception e) {
                    }

                    return null;
                }
            }, 0, TimeUnit.SECONDS);
            if(indexnn >= 99){
                long endtime = System.nanoTime();
                long costTime = (endtime - begintime) / 1000;
                System.out.println("[1] done!====" + costTime);
            }
        }
        pool.shutdown();



    }


}
