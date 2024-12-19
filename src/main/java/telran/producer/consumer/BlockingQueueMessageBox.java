package telran.producer.consumer;

import java.util.concurrent.LinkedBlockingDeque;

public class BlockingQueueMessageBox implements MessageBox{
    private LinkedBlockingDeque<String> messageBox = new LinkedBlockingDeque<>(1);
    @Override
    public void put(String message) throws InterruptedException {
       messageBox.put(message);
    }

    @Override
    public String take() throws InterruptedException {
        return messageBox.take();
    }

    @Override
    public String poll() {
        return messageBox.poll();
    }

}