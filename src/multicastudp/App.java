package multicastudp;

import java.util.Timer;
import java.util.TimerTask;

public class App
{
    public static void main( String[] args )
    {

    	TimerTask task = new RunMeTask();

    	Timer timer = new Timer();
    	timer.schedule(task, 0, 10000);

    }

    private static class RunMeTask extends TimerTask {

       @Override
	public void run() {
            System.out.println("Run Me ~");
            complete();
	}

        private void complete() {
            try {
            //assuming it takes 20 secs to complete the task
                Thread.sleep(10000);
                System.out.println("Complete ~");
                System.exit(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}