package HAT_Bot.Controllers;

/**
 * These are all the different function of the HAT-bot with the remote control
 */
public enum RemoteControlCommand {

    setSpeed0, // sets the speed to 0
    setSpeed10, // sets the speed to 10
    setSpeed20, // sets the speed to 20
    setSpeed30, // sets the speed to 30
    setSpeed40, // sets the speed to 40
    setSpeed50, // sets the speed to 50
    setSpeed60, // sets the speed to 60
    setSpeed70, // sets the speed to 70
    setSpeed80, // sets the speed to 80
    setSpeed90, // sets the speed to 90
    setSpeed100, // sets the speed to 100
    emergencyBrake, // triggers the emergencyBrake
    forward, // sets the motors to go forwards
    backward, // sets the motors to go backwards
    turnLeft, // turns left (turns on the stop when the speed is set to 0)
    turnRight, // turns right (turns on the stop when the speed is set to 0)
    mute, // toggles the sound all the sounds on / off
    driveSquare, // drive a square
    driveTriangle, // drive a triangle
    driveCircle, // drive a circle
    resume, //resumes the line following-protocol
    toggleLights // toggles the RGB lights of the bot
}
