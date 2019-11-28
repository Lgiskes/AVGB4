package HAT_Bot.Controllers;

/**
 * Rules for the Obstacle detection classes.
 */
public interface ObstacleDetectionObserver {

    void onObstacleDetected (ObstacleDetection o, ObstacleDetectionCommand command); // Looks if there is an obstacle.

}

