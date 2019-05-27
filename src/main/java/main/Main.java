package main;

import core.Scheduler;

import java.util.Timer;

public class Main {
    public static void main(String[] args) {
        Scheduler scheduler = new Scheduler();

        Timer timer = new Timer();
        long delay = 0;
        long intervalPeriod = 300000;

        timer.scheduleAtFixedRate(scheduler, delay, intervalPeriod);
    }
}
