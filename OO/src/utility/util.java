package utility;

import java.util.Random;

import controller.mainController.GameControl;

public class util {


    /**
     * @param lo Lower bound
     * @param hi Upper bound
     * @return a random number range from lo to hi
     * @Precondition Upper bound must greater than lower bound
     */
    public static int randInt(int lo, int hi) {
        //precondition
        assert lo < hi : ("invaild arguments: Upper bound must greater than lower bound");

        Random random = new Random();

        int s = random.nextInt(hi) % (hi - lo + 1) + lo;

        //uncomment to break postcondition
        //s += 10000;

        //postcondition
        assert lo <= s && s <= hi : ("outcome should be greater than lower bound and less than upper bound");

        return s;
    }

    public static Position randPos() {
    	GameControl gc = GameControl.getInstance();
        return new Position(randInt(1, gc.getGameSize() - 1), randInt(1, gc.getGameSize() - 1));
    }

}
