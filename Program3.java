/*
 * Name: <your name>
 * EID: <your EID>
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
        int n = town.getHouseCount();
        int k = town.getStationCount();
        ArrayList<Integer> housePositions = town.getHousePositions();

        // Initialize the R and S arrays
        int[][] R = new int[n + 1][k + 1];
        int[][] S = new int[n + 1][k + 1];
        for (int[] row : R)
            Arrays.fill(row, Integer.MAX_VALUE);
        R[0][0] = 0;

        // Dynamic programming solution
        for (int t = 1; t <= n; t++) {
            for (int j = 1; j <= Math.min(t, k); j++) {
                for (int i = j - 1; i < t; i++) {
                    int maxDist = 0;
                    for (int u = i; u < t; u++) {
                        maxDist = Math.max(maxDist, Math.abs(housePositions.get(u) - housePositions.get(t - 1)));
                    }
                    if (R[t][j] > Math.max(R[i][j - 1], maxDist)) {
                        R[t][j] = Math.max(R[i][j - 1], maxDist);
                        S[t][j] = i; // Record the leftmost house served by this station
                    }
                }
            }
        }

        // Backtrack to find the positions of the police stations
        ArrayList<Integer> policeStationPositions = new ArrayList<>();
        int t = n;
        for (int j = k; j >= 1; j--) {
            int leftHouseIndex = S[t][j];
            int rightHouseIndex = t - 1;
            int policeStationPosition = (housePositions.get(leftHouseIndex) + housePositions.get(rightHouseIndex)) / 2;
            policeStationPositions.add(0, policeStationPosition);
            t = S[t][j];
        }

        // Set the optimal police station positions
        town.setPoliceStationPositions(policeStationPositions);

        return town;
    }

    public boolean verifySolution(TownPlan town) {
        int optimalResponseTime = town.getResponseTime();
        ArrayList<Integer> policeStationPositions = town.getPoliceStationPositions();
        ArrayList<Integer> housePositions = town.getHousePositions();

        // For each house, find the minimum distance to a station
        ArrayList<Integer> minDistances = new ArrayList<>();
        for (int housePosition : housePositions) {
            int minDist = Integer.MAX_VALUE;
            for (int stationPosition : policeStationPositions) {
                minDist = Math.min(minDist, Math.abs(housePosition - stationPosition));
            }
            minDistances.add(minDist);
        }

        // The worst-case response time is the maximum of these minimum distances
        int worstCaseResponseTime = Collections.max(minDistances);

        return worstCaseResponseTime == optimalResponseTime;
    }




}
