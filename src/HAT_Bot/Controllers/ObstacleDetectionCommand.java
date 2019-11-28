package HAT_Bot.Controllers;

/**
 * This enum checks all the commands for the obstacle detection.
 */
public enum ObstacleDetectionCommand {
    Okay, // Command so the Boe-bot can keep driving.
    Stop, // Command to let the Boe-bot stop.
    SlowDown, // Command that slows down the Boe-bot.
    None // There is not given a command yet.
}
