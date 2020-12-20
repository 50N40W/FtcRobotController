package org.firstinspires.ftc.teamcode;

/* *
 *  Created by James Rumsey   August 2019
 *
 *  Adapted from several seasons of Juden Ki 8578 and Kernel Panic 11959 code.
 *
 */

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;


@SuppressWarnings("WeakerAccess")
@TeleOp(name = "UltimateGoal", group = "HardwarePushbot")
@Disabled
public class DriverModeUltimateGoal extends LinearOpMode {
    private HardwareDefenitionUltimateGoal robot = new HardwareDefenitionUltimateGoal();
    private GamepadDrive gamepadDrive = new GamepadDrive();
    double leftCmd = 0.0;
    double rightCmd = 0.0;

    //Definitions for rate at which each driver task executes.   Time is in milliseconds
    final long SENSORPERIOD = 20;
    final long SERVOPERIOD = 20;
    final long NAVPERIOD = 20;
    final long MOTORPERIOD = 20;
    final long CONTROLLERPERIOD = 20;
    final long TELEMETRYPERIOD = 500;







    public void runOpMode() {

        HardwareDefenitionUltimateGoal.STATUS retVal;

        /*
         * Initialize all of the robot hardware.
         * The init() method of the hardware class does all the work here
         */
        telemetry.addData("Status", "Initializing Robot...");
        telemetry.addData("Status", "Configuring Hardware...");
        telemetry.setAutoClear(false);
        telemetry.update();
        retVal = robot.init(hardwareMap, false, false, false);
        telemetry.addData("      ", retVal);
        telemetry.update();

        // Initialize some of the timing variables
        long CurrentTime = System.currentTimeMillis();
        long LastSensor = CurrentTime;
        long LastEncoderRead = CurrentTime + 5;
        long LastServo = CurrentTime + 10;
        long LastNav = CurrentTime + 15;
        long LastMotor = CurrentTime + 20;
        long LastController = CurrentTime + 7;
        long LastTelemetry = CurrentTime + 17;


        telemetry.addData("Status", "Initialized");
        telemetry.update();

        ElapsedTime runtime = new ElapsedTime();
        waitForStart();
        runtime.reset();

        /*************************************************************
         *            Everything below here  \\ press START           *
         **************************************************************/

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            CurrentTime = System.currentTimeMillis();

            /****************************************************
             *                SENSORS
             *        INPUTS: Raw Sensor Values (Not encoders)
             *       OUTPUTS: parameters containing sensor values*
             ****************************************************/
            if (CurrentTime - LastSensor > SENSORPERIOD) {
                LastSensor = CurrentTime;
            }


            /* **************************************************
             *                Controller INPUT                  *
             *  INPUTS: raw controller values                   *
             *  OUTPUTS:
             *         g1_LeftX    g1_RightX
             *         g1_LeftY    g1_RightY
             *         g1_a (gamepad A)
             *         g1_b (gamepad B)
             ****************************************************/
            if (CurrentTime - LastController > CONTROLLERPERIOD) {
                LastController = CurrentTime;
            }


            /***************************************************
             *                NAV
             *      Inputs:  Gamepad positions
             *               Sensor Values (as needed)
             *      Outputs: Servo and Motor position commands
             *                         motor
             ****************************************************/
            if (CurrentTime - LastNav > NAVPERIOD) {
                LastNav = CurrentTime;


                if (gamepad1.x) {
                    robot.shooterTrigger.setPosition(0.0);
                }
                if (gamepad1.y) {
                    robot.shooterTrigger.setPosition(1.0);
                }
                if (gamepad1.a) {
                    robot.shooterTop.setPower(1.0);
                    robot.shooterBottom.setPower(1.0);
                }
                if (gamepad1.b) {
                    robot.shooterTop.setPower(0.0);
                    robot.shooterBottom.setPower(0.0);
                }

            }



            /* **************************************************
             *                SERVO OUTPUT
             *                Inputs: leftClamp position command
             *                        rightClamp position command *
             *                Outputs: Physical write to servo interface.
             ****************************************************/
            if (CurrentTime - LastServo > SERVOPERIOD) {
                LastServo = CurrentTime;
            }


            /* ***************************************************
             *                MOTOR OUTPUT
             *       Inputs:  Motor power commands
             *       Outputs: Physical interface to the motors
             ****************************************************/
            if (CurrentTime - LastMotor > MOTORPERIOD) {
                LastMotor = CurrentTime;

            }


            /* ***************************************************
             *                TELEMETRY
             *       Inputs:  telemetry structure
             *       Outputs: command telemetry output to phone
             ****************************************************/

            if (CurrentTime - LastTelemetry > TELEMETRYPERIOD) {
                LastTelemetry = CurrentTime;
                telemetry.clear();
                telemetry.addData("g1_lefty", gamepad1.left_stick_y);
                telemetry.addData("g1_righty", gamepad1.right_stick_y);
                telemetry.addData("leftCmd", leftCmd);
                telemetry.addData("rightCmd", rightCmd);
                telemetry.update();
            }
        }

        //SAFE EXIT OF RUN OPMODE, stop motors, leave servos????
        robot.shooterTop.setPower(0);
        robot.shooterBottom.setPower(0);

    }



}


