package org.firstinspires.ftc.teamcode;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@TeleOp(name = "Basic Mecanum", group = "TeleOp")
public class TitansMecanumDrive extends LinearOpMode {

    public DcMotor backLeft, backRight;
    //public DcMotor frontLeft, frontRight;
    public DcMotor Outtake;

    public CRServo LeftFeeder;
    public CRServo RightFeeder;

    @Override
    public void runOpMode() {

        //frontLeft = hardwareMap.get(DcMotor.class, "FL");
        //frontRight = hardwareMap.get(DcMotor.class, "FR");
       backLeft = hardwareMap.get(DcMotor.class, "BL");
       backRight = hardwareMap.get(DcMotor.class, "BR");
        Outtake = hardwareMap.get(DcMotor.class, "Launcher");

        LeftFeeder = hardwareMap.get(CRServo.class, "LeftFeeder");
        RightFeeder = hardwareMap.get(CRServo.class, "RightFeeder");

        //frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.REVERSE);
        //frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.FORWARD);
        Outtake.setDirection(DcMotor.Direction.FORWARD);

        LeftFeeder.setDirection(DcMotorSimple.Direction.FORWARD);
        RightFeeder.setDirection(DcMotorSimple.Direction.FORWARD);

        //frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Outtake.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        Outtake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            // --- FIXED INPUTS ---
            double y = -gamepad1.left_stick_y;   // CHANGED: removed the negative, fixes forward/backward
//            double x = -gamepad1.left_stick_x;  // CHANGED: inverted, fixes strafing
            double rx = gamepad1.right_stick_x; // unchanged — turning was fine

            double x = 0;

            // --- MECANUM DRIVE CALCULATION ---
            //double frontLeftPower = y + x + rx;
            // double frontRightPower = y - x - rx;
            double backLeftPower = y - x + rx;
            double backRightPower = y + x - rx;

            // Normalize powers
            double max = Math.max(Math.abs(backLeftPower), Math.max(Math.abs(backRightPower),
                    Math.max(Math.abs(backLeftPower), Math.abs(backRightPower))));
            if (max > 1.0) {
                //frontLeftPower /= max;
                //frontRightPower /= max;
                backLeftPower /= max;
                backRightPower /= max;
            }

            //frontLeft.setPower(frontLeftPower);
            //frontRight.setPower(frontRightPower);
            backLeft.setPower(backLeftPower);
            backRight.setPower(backRightPower);

            // --- OUTTAKE CONTROL ---
            if (gamepad1.right_bumper) {
                Outtake.setPower(0.79);
            } else if (gamepad1.right_trigger > 0) {
                Outtake.setPower(0.5 + (0.5 * gamepad1.right_trigger));
            } else {
                Outtake.setPower(0.0);
            }

            telemetry.addData("ShooterPower", 0.5 + (0.5 * gamepad1.right_trigger));

            // --- FEEDER CONTROL ---
            if (gamepad1.left_bumper) {
                LeftFeeder.setPower(-1.0);
                RightFeeder.setPower(-1.0);
            } else {
                LeftFeeder.setPower(0.0);
                RightFeeder.setPower(0.0);
            }


            telemetry.addData("Joystick Y", gamepad1.left_stick_y);
            telemetry.addData("Joystick X", gamepad1.left_stick_x);
            telemetry.addData("Joystick RX", gamepad1.right_stick_x);
            //telemetry.addData("FL Power", frontLeft);
            //telemetry.addData("FR Power", frontRight);
            telemetry.addData("BL Power", backLeft);
            telemetry.addData("BR Power", backRight);
            telemetry.addData("Outtake Power", Outtake.getPower());
            telemetry.addData("Left Feeder Power", LeftFeeder.getPower());
            telemetry.addData("Right Feeder Power", RightFeeder.getPower());
            telemetry.update();

        }
    }
}
