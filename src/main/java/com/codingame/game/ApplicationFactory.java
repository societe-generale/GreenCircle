package com.codingame.game;

import com.google.inject.Singleton;

import java.util.HashSet;
import java.util.Random;


@Singleton
public class ApplicationFactory {
    private int SMALL = 0;
    private int BIG = 1;
    private int applicationsList[][][] = {
            new int[][] { new int[] { 0, 0, 0, 2, 2, 0, 2, 0 }, new int[] { 4, 4, 0, 0, 0, 0, 0, 0 } },
            new int[][] { new int[] { 0, 2, 0, 0, 2, 0, 0, 2 }, new int[] { 4, 0, 4, 0, 0, 0, 0, 0 } },
            new int[][] { new int[] { 0, 0, 2, 0, 0, 0, 2, 2 }, new int[] { 4, 0, 0, 4, 0, 0, 0, 0 } },
            new int[][] { new int[] { 0, 2, 2, 2, 0, 0, 0, 0 }, new int[] { 4, 0, 0, 0, 4, 0, 0, 0 } },
            new int[][] { new int[] { 0, 2, 0, 2, 0, 0, 0, 2 }, new int[] { 4, 0, 0, 0, 0, 4, 0, 0 } },
            new int[][] { new int[] { 0, 0, 2, 2, 0, 2, 0, 0 }, new int[] { 4, 0, 0, 0, 0, 0, 4, 0 } },
            new int[][] { new int[] { 0, 0, 2, 0, 0, 2, 2, 0 }, new int[] { 4, 0, 0, 0, 0, 0, 0, 4 } },

            new int[][] { new int[] { 2, 0, 0, 2, 0, 2, 0, 0 }, new int[] { 0, 4, 4, 0, 0, 0, 0, 0 } },
            new int[][] { new int[] { 2, 0, 0, 0, 2, 2, 0, 0 }, new int[] { 0, 4, 0, 4, 0, 0, 0, 0 } },
            new int[][] { new int[] { 2, 0, 2, 0, 0, 0, 2, 0 }, new int[] { 0, 4, 0, 0, 4, 0, 0, 0 } },
            new int[][] { new int[] { 2, 0, 0, 0, 0, 0, 2, 2 }, new int[] { 0, 4, 0, 0, 0, 4, 0, 0 } },
            new int[][] { new int[] { 0, 0, 2, 2, 2, 0, 0, 0 }, new int[] { 0, 4, 0, 0, 0, 0, 4, 0 } },
            new int[][] { new int[] { 0, 0, 0, 0, 2, 2, 0, 2 }, new int[] { 0, 4, 0, 0, 0, 0, 0, 4 } },

            new int[][] { new int[] { 0, 0, 0, 0, 2, 0, 2, 2 }, new int[] { 0, 0, 4, 4, 0, 0, 0, 0 } },
            new int[][] { new int[] { 2, 2, 0, 2, 0, 0, 0, 0 }, new int[] { 0, 0, 4, 0, 4, 0, 0, 0 } },
            new int[][] { new int[] { 2, 2, 0, 0, 0, 0, 0, 2 }, new int[] { 0, 0, 4, 0, 0, 4, 0, 0 } },
            new int[][] { new int[] { 2, 0, 0, 0, 0, 2, 2, 0 }, new int[] { 0, 0, 4, 0, 0, 0, 4, 0 } },
            new int[][] { new int[] { 2, 2, 0, 0, 0, 2, 0, 0 }, new int[] { 0, 0, 4, 0, 0, 0, 0, 4 } },

            new int[][] { new int[] { 0, 0, 2, 0, 2, 0, 2, 0 }, new int[] { 0, 0, 0, 4, 4, 0, 0, 0 } },
            new int[][] { new int[] { 0, 2, 2, 0, 0, 0, 0, 2 }, new int[] { 0, 0, 0, 4, 0, 4, 0, 0 } },
            new int[][] { new int[] { 0, 0, 0, 2, 0, 0, 2, 2 }, new int[] { 0, 0, 0, 4, 0, 0, 4, 0 } },
            new int[][] { new int[] { 0, 2, 2, 0, 0, 2, 0, 0 }, new int[] { 0, 0, 0, 4, 0, 0, 0, 4 } },

            new int[][] { new int[] { 0, 2, 0, 2, 0, 0, 2, 0 }, new int[] { 0, 0, 0, 0, 4, 4, 0, 0 } },
            new int[][] { new int[] { 2, 0, 0, 2, 0, 0, 0, 2 }, new int[] { 0, 0, 0, 0, 4, 0, 4, 0 } },
            new int[][] { new int[] { 0, 0, 0, 2, 0, 2, 2, 0 }, new int[] { 0, 0, 0, 0, 4, 0, 0, 4 } },

            new int[][] { new int[] { 2, 2, 0, 0, 2, 0, 0, 0 }, new int[] { 0, 0, 0, 0, 0, 4, 4, 0 } },
            new int[][] { new int[] { 2, 0, 2, 0, 2, 0, 0, 0 }, new int[] { 0, 0, 0, 0, 0, 4, 0, 4 } },

            new int[][] { new int[] { 0, 0, 2, 0, 2, 2, 0, 0 }, new int[] { 0, 0, 0, 0, 0, 0, 4, 4 } },
    };

    private HashSet<Integer> alreadyTaken = new HashSet<>();


    public Application createBigApplication(Random random) {
        return createApplication(random, BIG);
    }

    private Application createApplication(Random random, int applicationType) {
        int index = -1;
        while(true) {
            index = random.nextInt(applicationsList.length);
            if (!alreadyTaken.contains(index)) {
                break;
            }
        }
        alreadyTaken.add(index);
        return new Application(index, applicationsList[index][applicationType]);
    }

    public Application createSmallApplication(Random random) {
        return createApplication(random, SMALL);
    }


}
