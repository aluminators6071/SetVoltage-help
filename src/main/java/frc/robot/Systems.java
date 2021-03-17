package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import frc.robot.RobotMap.Controls;
import frc.robot.RobotMap.PWM;

public class Systems{

    /**
     * Access DriveTrain components such as the motor controllers and encoders.
     */
    public class Drivetrain {
        SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(11, 11, 11);

        Spark leftDrive = new Spark(PWM.DRIVETRAIN_LEFT);
        Spark rightDrive = new Spark(PWM.DRIVETRAIN_RIGHT);

        Drivetrain() {
            System.out.println("Created Drivetrain object.");
        }

        /**
         * Set the speeds of the left and right sides of the robot.
         * 
         * We use 2 motors on each side, but we use a PWM splitter going from one port on the RoboRIO to 2 seperate Rev Robotics Spark motor controllers. Hence why we are able to have just the 2 controllers.
         * 
         * @param leftAmmount
         * @param rightAmmount
         */
        public void Move(double leftAmmount, double rightAmmount) {
            leftDrive.setInverted(true);
            // TODO: Implement Exponential Drive.
            // leftDrive.setVoltage((feedforward.calculate(leftAmmount, leftAmmount)));
            // rightDrive.setVoltage((feedforward.calculate(rightAmmount, rightAmmount)));
            
            leftDrive.set(leftAmmount);
            rightDrive.set(rightAmmount);
        

        }

        /**
         * Stop all motors on the robot.
         */
        public void Stop() {
            leftDrive.stopMotor();
            rightDrive.stopMotor();
        }
    }

    public class ControlPanel {
        
        Spark controlPanel = new Spark(PWM.CONTROL_PANEL);

        ControlPanel() {
            System.out.println("Created ControlPanel object.");
        }

        /**
         * Set the speed of the motor.
         * 
         * @param value
         */
        public void Move(double value) {
    
            // TODO: Implement Exponential Drive.
            controlPanel.set(value);

        }

        /**
         * Stop all motors for the control panel.
         */
        public void Stop() {
            controlPanel.stopMotor();
        }
    }
    
    public class Lift {
        
        Spark liftRaise = new Spark(PWM.LIFT_RAISE);
        Spark liftPull = new Spark(PWM.LIFT_PULL);

        Lift() {
            System.out.println("Created lift object.");
        }

        /**
         * Set the speed of the motor.
         * 
         * @param value
         */
        public void LiftBot() {
            liftPull.set(1);
        }

        /**
         * Set the speed of the motor.
         * 
         * @param value
         */
        public void LowerBot() {
            liftPull.set(-1);
        }

        /**
         * Stop all motors for the lift.
         */
        public void RaiseLift() {
            liftRaise.set(1);
        }

        /**
         * Set the speed of the motor.
         * 
         * @param value
         */
        public void LowerRaise() {
            liftRaise.set(-1);
        }

        /**
         * Set the speed of the motor.
         * 
         * @param value
         */
        public void StopLift() {
            liftPull.stopMotor();
        }

        /**
         * Stop all motors for the lift.
         */
        public void StopRaise() {
            liftRaise.stopMotor();
        }
    }



    public class Shooter {
        
        Spark shooterTop = new Spark(PWM.SHOOTER_TOP);
        Spark shooterBottom = new Spark(PWM.SHOOTER_BOTTOM);
        Spark shooterIn = new Spark(PWM.SHOOTER_IN);

        Shooter() {
            System.out.println("Created Shooter object.");
        }

        public void raise() {
            shooterTop.set(.75);
        }

        public void shoot(double intensity) {
            System.out.println("Intencity:" + intensity);
            shooterBottom.set(intensity);
        }

        public void pickUp() {
            shooterIn.set(.75);
        }
        
        public void drop() {
            shooterIn.set(-1);
            shooterTop.set(-1);
        }

        public void stopPickUp() {
            shooterIn.set(0);
        }

        public void stopShoot() {
            shooterBottom.set(0);
        }

        public void stopRaise() {
            shooterTop.set(0);
        }
    }


    /**
     * The class for receiving input from the joysticks in Driver Station.
     * 
     * We use a dual Joystick configuration for more percise control of the robot. 
     */
    public class Input {
        Joystick left = new Joystick(0);
        Joystick right = new Joystick(1);

        Input() {
            System.out.println("Created Input object.");
        }
        
        public Double getLeftDrive() {
            return left.getRawAxis(Controls.DRIVE_AXIS);
        }
        public Double getRightDrive() {
            return right.getRawAxis(Controls.DRIVE_AXIS);
        }

        public boolean getControlPanelActive() {
            return (right.getRawButton(Controls.CONTROL_PANEL_RIGHT) || left.getRawButton(Controls.CONTROL_PANEL_LEFT)) ? true : false;
        }

        public boolean getShoot() {
            return right.getRawButton(Controls.SHOOTER_SHOOT);
        }

        public boolean getRaise() {
            return left.getRawButton(Controls.SHOOTER_RAISE);
        }

        public boolean getLower() {
            return left.getRawButton(Controls.SHOOTER_LOWER);
        }

        public boolean isDrivetrainLocked() {
            return right.getRawButton(Controls.DRIVETRAIN_LOCK);
        }

        public double shooterIntensity() {
            System.out.println((right.getThrottle() * .5 ) + .5);
            return (right.getThrottle() * .5 ) + .5;
        }

        public boolean liftClaw() {
            return left.getRawButton(Controls.RAISE_LIFT);
        }

        public boolean lowerClaw() {
            return left.getRawButton(Controls.LIFT_BOT);
        }

        public boolean liftBot() {
            return right.getRawButton(Controls.LIFT_BOT);
        }

        public boolean lowerBot() {
            return right.getRawButton(Controls.RAISE_LIFT);
        }
    }

}