package clubSimulation;

import clubSimulation.ClubGrid;
import clubSimulation.GridBlock;

import java.util.concurrent.atomic.AtomicInteger;

public class Barman extends Thread {
    private ClubGrid club;
    private AtomicInteger barmanX; // X-coordinate of the barman's position
    private int maxY; // Maximum Y coordinate in the grid

    public Barman(ClubGrid club) {
        this.club = club;
        this.barmanX = new AtomicInteger(1); // Initialize barman's position
        this.maxY = club.getMaxY();
    }

    // Move the barman from left to right behind the bar
    private void moveBarman() throws InterruptedException {
        while (true) {
            if (barmanX.get() < club.getMaxX() - 1) {
                barmanX.getAndIncrement();
            } else {
                barmanX.set(1); // Reset to the start position
            }
            Thread.sleep(100); // Wait between movements
        }
    }

    // Serve drinks to patrons at the bar
    private void serveDrinks() throws InterruptedException {
        while (true) {
            GridBlock currentBlock = club.whichBlock(barmanX.get(), maxY - 1);
            if (currentBlock != null && currentBlock.isBar() && currentBlock.occupied()) {
                System.out.println("Barman serving drinks at position: " + barmanX.get() + " " + (maxY - 1));
                Thread.sleep(2000); // Serve the drink
            }
            Thread.sleep(1000); // Wait between serving attempts
        }
    }

    public void setBarmanX(int x) {
        barmanX.set(x);
    }

    @Override
    public void run() {
        try {
            moveBarman();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}