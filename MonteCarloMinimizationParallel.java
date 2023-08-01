package MonteCarloMini;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.RecursiveAction;
import java.util.Random;
class MonteCarloMinimizationParallel extends RecursiveAction{

    static final boolean DEBUG=true;

    static long startTime = 0;
    static long endTime = 0;

    //timers - note milliseconds
    private static void tick(){
        startTime = System.currentTimeMillis();
    }
    private static void tock(){
        endTime=System.currentTimeMillis();
    }

    static int tasks;
    static int offset;

    static int rows;
    static int columns; //grid size
    static double xmin;
    static double xmax;
    static double ymin;
    static double ymax; //x and y terrain limits
    static TerrainArea terrain;  //object to store the heights and grid points visited by searches
    static double searches_density;	// Density - number of Monte Carlo  searches per grid position - usually less than 1!

    static int num_searches;		// Number of searches
    static Search [] searches;		// Array of searches
    Random rand = new Random();  //the random number generator

    static int finder =-1;

    static int min;


    public MonteCarloMinimizationParallel(int t, int start){tasks = t; offset = start;}

    @Override
    protected void compute() {




        if(DEBUG) {
            /* Print arguments */
            System.out.printf("Arguments, Rows: %d, Columns: %d\n", rows, columns);
            System.out.printf("Arguments, x_range: ( %f, %f ), y_range( %f, %f )\n", xmin, xmax, ymin, ymax );
            System.out.printf("Arguments, searches_density: %f\n", searches_density );
            System.out.printf("\n");
        }

        // Initialize
        terrain = new TerrainArea(rows, columns, xmin,xmax,ymin,ymax);
        num_searches = (int)( rows * columns * searches_density );
        searches= new Search [num_searches];
        for (int i=0;i<num_searches;i++)
            searches[i]=new Search(i+1, rand.nextInt(rows),rand.nextInt(columns),terrain);

        if(DEBUG) {
            /* Print initial values */
            System.out.printf("Number searches: %d\n", num_searches);
            //terrain.print_heights();
        }

        //all searches
        int min=Integer.MAX_VALUE;
        int local_min=Integer.MAX_VALUE;
        finder =-1;
        if (tasks <= 1000){

            for  (int i=0;i<num_searches;i++) {
                local_min=searches[i].find_valleys();
                if((!searches[i].isStopped())&&(local_min<min)) { //don't look at  those who stopped because hit exisiting path
                    min=local_min;
                    finder=i; //keep track of who found it
                }
                if(DEBUG) System.out.println("Search "+searches[i].getID()+" finished at  "+local_min + " in " +searches[i].getSteps() + " and started at " + searches[i].getPos_row() + " "+ searches[i].getPos_col() + " " + finder);
            }

        }

        else{
            int split = (int)(tasks/2.0);
            //split work into two
            MonteCarloMinimizationParallel left = new MonteCarloMinimizationParallel(split, offset);
            MonteCarloMinimizationParallel right = new MonteCarloMinimizationParallel(tasks - split, offset + split);
            left.fork();
            right.compute();
            left.join();
        }


    }

    public static void main(String[] args){

        if (args.length!=7) {
            System.out.println("Incorrect number of command line arguments provided.");
            System.exit(0);
        }
        /* Read argument values */
        rows =Integer.parseInt( args[0] );
        columns = Integer.parseInt( args[1] );
        xmin = Double.parseDouble(args[2] );
        xmax = Double.parseDouble(args[3] );
        ymin = Double.parseDouble(args[4] );
        ymax = Double.parseDouble(args[5] );
        searches_density = Double.parseDouble(args[6] );



        //start timer
        tick();

        MonteCarloMinimizationParallel calc = new MonteCarloMinimizationParallel(num_searches, 0);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(calc);

        tock();

        if(DEBUG) {
            /* print final state */
            terrain.print_heights();
            terrain.print_visited();
        }

        System.out.printf("Run parameters\n");
        System.out.printf("\t Rows: %d, Columns: %d\n", rows, columns);
        System.out.printf("\t x: [%f, %f], y: [%f, %f]\n", xmin, xmax, ymin, ymax );
        System.out.printf("\t Search density: %f (%d searches)\n", searches_density,num_searches );

        /*  Total computation time */
        System.out.printf("Time: %d ms\n",endTime - startTime );
        int tmp=terrain.getGrid_points_visited();
        System.out.printf("Grid points visited: %d  (%2.0f%s)\n",tmp,(tmp/(rows*columns*1.0))*100.0, "%");
        tmp=terrain.getGrid_points_evaluated();
        System.out.printf("Grid points evaluated: %d  (%2.0f%s)\n",tmp,(tmp/(rows*columns*1.0))*100.0, "%");

        /* Results*/
        System.out.printf("Global minimum: %d at x=%.1f y=%.1f\n\n", min, terrain.getXcoord(searches[finder].getPos_row()), terrain.getYcoord(searches[finder].getPos_col()) );




    }

}
