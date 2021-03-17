package frc.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.SPI;

import java.lang.reflect.Array;

import edu.wpi.first.cameraserver.*;

import frc.robot.Systems.*;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.interfaces.Gyro;

public class Robot extends TimedRobot{

    // Create a new RobotMap to have access to the different parts of the robot.
    Systems system = new Systems();
    Drivetrain drivetrain = system.new Drivetrain();
    Input input = system.new Input();

    ControlPanel controlPanel = system.new ControlPanel();
    Shooter shooter = system.new Shooter();
    Lift lift = system.new Lift();
    ADXRS450_Gyro gyro = new ADXRS450_Gyro(SPI.Port.kOnboardCS0);
    

    

    Timer timer = new Timer();  
    double lastCompletedTime = 0.0;
    int currentStep = 0;
    double[] autonTimes = new double[2];

    @Override
    public void robotInit() {
      CameraServer.getInstance().startAutomaticCapture("frontCam", 0);
      CameraServer.getInstance().startAutomaticCapture("backCam", 1);

      System.out.println("BB9 is online. Beep boop. Beep beep boop.");
      gyro.reset();
      gyro.calibrate();

      autonTimes[0] = 1.0;
      autonTimes[1] = 2.0;
    }

    @Override
    public void robotPeriodic() {

    }

    @Override
    public void autonomousInit() { 
      gyro.reset(); 
      timer.reset();
      timer.start();
    }

    @Override
    public void autonomousPeriodic() {
      System.out.println("Auton Step: " + currentStep);
      System.out.println("Gyro Angle: " + gyro.getAngle());
      switch (currentStep) {
        case 0:
        if (timer.get() < autonTimes[currentStep]) {
          double intendedLeft = -.5;
          double itendedRight = -.5;
          double difference = (0 - gyro.getAngle()) * 0.05;
          System.out.println("Difference: " + difference);
            if (difference < 0) {
              drivetrain.Move(intendedLeft - difference,itendedRight);
            } else {
              drivetrain.Move(intendedLeft,itendedRight-difference);
            }
          } else {
            if (gyro.getAngle() > -60) {
              drivetrain.Move(.35, -.35);
            } else {
              drivetrain.Stop();
              lastCompletedTime = timer.get();
              currentStep++;
            }
          }
         break;
          case 1:
          if ((timer.get() - lastCompletedTime) < autonTimes[currentStep]) {
            double intendedLeft = -.5;
            double itendedRight = -.5;
            double difference = (-60 - gyro.getAngle()) * 0.05;
            System.out.println("Difference: " + difference);
              if (difference > 0) {
                drivetrain.Move(intendedLeft - difference,itendedRight);
              } else {
                drivetrain.Move(intendedLeft,itendedRight-difference);
             }
            } else {
              if (gyro.getAngle() < 0) {
                drivetrain.Move(-.35, .35);
              } else {
                drivetrain.Stop();
                currentStep++;
             }
            }
            break;
      
        default:
          System.out.println("Auton Complete");
          break;
      }
    }

    
    @Override
    public void teleopInit() {
      
    }

    @Override
    public void teleopPeriodic() { 
      System.out.println("Gyro Angle:" + (int) gyro.getAngle());
      if (!input.isDrivetrainLocked()) {
        drivetrain.Move(input.getLeftDrive(), input.getRightDrive());
      } else {
        drivetrain.Move(0,0);
      }


      if (input.getControlPanelActive()) controlPanel.Move(0.5f);
      else controlPanel.Stop();
      if (input.getShoot()) { 
        shooter.shoot(input.shooterIntensity());
      }
      else {
        shooter.stopShoot();
      } 

      if (input.getRaise()) { 
        shooter.raise(); 
        shooter.pickUp();
      } 
      else if (!input.getRaise() && !input.getShoot() && !input.getLower()) {
        shooter.stopRaise();
        shooter.stopPickUp();
      }

      if (input.getLower()) {
        shooter.drop();
      }

      if (input.liftBot()) {
        lift.LiftBot();
      } else if (input.lowerBot()) {
        lift.LowerBot();
      } else {
        lift.StopLift();
      }

      if (input.liftClaw()) {
        lift.RaiseLift();
      } else if (input.lowerClaw()) {
        lift.LowerRaise();
      } else {
        lift.StopRaise();
      }


    }
}