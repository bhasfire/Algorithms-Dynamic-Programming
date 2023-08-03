/*
 * Name: <your name>
 * EID: <your EID>
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Your solution goes in this class.
 * 
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 * 
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution.
 */
public class Program3 extends AbstractProgram3 {

    /**
     * Determines the solution of the optimal response time for the given input TownPlan. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return Updated TownPlan town with the "responseTime" field set to the optimal response time
     */
    @Override
    public TownPlan findOptimalResponseTime(TownPlan town) {
        int n = town.getHouseCount();
        int k = town.getStationCount();
        ArrayList<Integer> housePositions = town.getHousePositions();

        // Initialize the R array
        int[][] R = new int[n + 1][k + 1];
        for (int[] row : R)
            Arrays.fill(row, Integer.MAX_VALUE);
        R[0][0] = 0;

        // Calculate maximum distances for all pairs of houses
        int[][] maxDist = new int[n + 1][n + 1];
        for (int i = 0; i < n; i++) {
            maxDist[i][i] = 0;
            for (int j = i + 1; j < n; j++) {
                int mid = (housePositions.get(i) + housePositions.get(j)) / 2;
                maxDist[i][j] = Math.max(maxDist[i][j - 1], Math.abs(housePositions.get(j) - mid));
            }
        }

        // Dynamic programming solution
        for (int t = 1; t <= n; t++) {
            for (int j = 1; j <= Math.min(t, k); j++) {
                for (int i = j - 1; i < t; i++) {
                    R[t][j] = Math.min(R[t][j], Math.max(R[i][j - 1], maxDist[i][t - 1]));
                }
            }
        }

        // Set the optimal response time
        town.setResponseTime(R[n][k]);

        return town;
    }



    /**
     * Determines the solution of the set of police station positions that optimize response time for the given input TownPlan. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return Updated TownPlan town with the "policeStationPositions" field set to the optimal police station positions
     */
    @Override
    public TownPlan findOptimalPoliceStationPositions(TownPlan town) {
        /* TODO implement this function */
        return town;
    }
}
