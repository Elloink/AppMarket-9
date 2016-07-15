package com.appmarket.mapleaf.applicationmarket.manage;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Mapleaf on 2016/7/15.
 */
public class ThreadManager {
    public static ThreadPool threadPool;
    public static ThreadPool getThreadPool(){
        if(threadPool==null){
            synchronized (ThreadManager.class){
                if(threadPool==null){
                    int cpuCount = Runtime.getRuntime().availableProcessors();
                    int threadCount = cpuCount * 2+1;
                    threadPool= new ThreadPool(threadCount,threadCount,1L);
                }
            }
        }
        return threadPool;
    }
    public static class  ThreadPool{
        private int corePoolSize;
        private int maximumPoolSize;
        private long keepAliveTime;
        private ThreadPoolExecutor executor;

        private ThreadPool(int corePoolSize,int maximumPoolSize,long keepAliveTime){
            this.corePoolSize = corePoolSize;
            this.maximumPoolSize = maximumPoolSize;
            this.keepAliveTime = keepAliveTime;
        }
        public void execute(Runnable r){
            if(executor==null){
                executor = new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime,
                        TimeUnit.SECONDS,new LinkedBlockingQueue<Runnable>(), Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());
            }
            executor.execute(r);
        }
        public void cancel(Runnable r){
            if(executor!=null){
                executor.getQueue().remove(r);
            }
        }
    }
}
