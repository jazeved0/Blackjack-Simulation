package ehs.mat;

import ehs.mat.game.BlackjackGame;
import ehs.mat.strategy.OptimalStrategy;
import ehs.mat.strategy.Strategy;
import ehs.mat.strategy.ThresholdStrategy;

public class TestHarness {

    private static final int TEST_COUNT = 10000000;
    private static int NUM_DECKS = 2;
    private static final int BEST_THRESHOLD = 17;

    public static void main(String[] args) {
        /*// Test Thresholds:
        for(int i = 2; i < 20; ++i) {
            double[] results = testStrategy(new ThresholdStrategy(i));
            System.out.println("Final deltaBet with threshold=" + i + ": " + results[0] / TEST_COUNT);
        }*/

        for(int i = 1; i <= 8; ++i) {
            NUM_DECKS = i;
            System.out.println(i + " decks:");
            double[] results1 = testStrategy(new ThresholdStrategy(BEST_THRESHOLD));
            System.out.println("Final deltaBet with threshold: " + results1[0] / TEST_COUNT);

            double[] results2 = testStrategy(new OptimalStrategy());
            System.out.println("Final deltaBet with optimal: " + results2[0] / TEST_COUNT);
            System.out.println();
        }
    }

    public static double playGameUsingStrategy(Strategy s) {
        BlackjackGame game = new BlackjackGame(NUM_DECKS);
        return game.playUntilFinished(s);
    }

    public static double[] testStrategy(Strategy s) {
        double[] deltaBets = new double[TEST_COUNT + 1];
        for(int i = 0; i < TEST_COUNT; ++i) {
            double result = playGameUsingStrategy(s);
            deltaBets[i + 1] = result;
            deltaBets[0] += result;
        }
        return deltaBets;
    }
}
