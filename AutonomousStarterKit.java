package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

    @Autonomous(name = "StarterKitAutoTest", group = "Autonomous")
    public class AutonomousStarterKit extends LinearOpMode {
        public DcMotor backleft = null;
        public DcMotor backright = null;
        public DcMotor frontleft = null;
        public DcMotor frontright = null;


        @Override
        public void runOpMode() throws InterruptedException {
            // Initialize hardware
            backright = hardwareMap.get(DcMotor.class, "BR");
            backleft = hardwareMap.get(DcMotor.class, "BL");
            frontright = hardwareMap.get(DcMotor.class, "FR");
            frontleft = hardwareMap.get(DcMotor.class, "FL");

            //backright.setDirection(DcMotor.Direction.REVERSE);
            frontright.setDirection(DcMotor.Direction.REVERSE);

            // Wait for the start button to be pressed
            waitForStart();
            backleft.setPower(0.5);
            frontleft.setPower(0.5);
            frontright.setPower(0.5);
            backright.setPower(0.5);
            sleep(1200);
            backleft.setPower(0);
            frontleft.setPower(0);
            frontright.setPower(0);
            backright.setPower(0);


        }
    }
