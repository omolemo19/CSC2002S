package clubSimulation;

import clubSimulation.ClubGrid;
import clubSimulation.GridBlock;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Barman extends Thread {
    private ClubGrid club;
    private AtomicInteger barmanX; // X-coordinate of the barman's position
    private int maxY; // Maximum Y coordinate in the grid
    GridBlock currentBlock;
    private Random rand;
    private int movingSpeed;
    private PeopleLocation myLocation;


    public Barman(ClubGrid club, int movingSpeed) {
        this.club = club;
        this.barmanX = new AtomicInteger(1); // Initialize barman's position
        this.maxY = club.getMaxY();
        this.movingSpeed = movingSpeed;
        rand = new Random();
    }

    // Move the barman from left to right behind the bar
    private void moveBarman() throws InterruptedException {
        while (true) {
            for(int i=0;i<3;i++) { //sequence of 3

                int x_mv= rand.nextInt(3)-1; //-1,0 or 1
                int y_mv=Integer.signum(1-x_mv);

                for(int j=0;j<4;j++) { //do four fast dance steps
                    currentBlock=club.move(currentBlock,x_mv,y_mv, myLocation);
                    sleep(movingSpeed/5);
                    x_mv*=-1;
                    y_mv*=-1;
                }

            }
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
            while (true){
                moveBarman();
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}