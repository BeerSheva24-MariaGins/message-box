package telran.producer.consumer;

import java.util.concurrent.locks.*;

public class ConditionsMesageBox implements MessageBox{
    private Lock lock = new ReentrantLock();
    private Condition waitingForConsumer = lock.newCondition();
    private Condition waitingForProducer = lock.newCondition();
    private String message;
    @Override
    public void put(String message) throws InterruptedException {
       try {
        lock.lock();
        while(this.message != null) {
            waitingForConsumer.await();
        }
        this.message = message;
        waitingForProducer.signal();
       }finally {
            lock.unlock();
       }
    }

    @Override
    public String take() throws InterruptedException {
        try {
            lock.lock();
            while(message == null) {
                waitingForProducer.await();
            }
            String msg = message;
            message = null;
            waitingForConsumer.signal();
            return msg;
           }finally {
                lock.unlock();
           }
    }

    @Override
    public String poll() {
        try {
            lock.lock();
            String msg = message;
            message = null;
            waitingForConsumer.signal();
            return msg;
           }finally {
                lock.unlock();
           }
    }

}