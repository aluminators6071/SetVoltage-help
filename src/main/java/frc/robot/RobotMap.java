package frc.robot;

public class RobotMap {

    public class PWM {

        public static final int 
            DRIVETRAIN_RIGHT = 9,
            DRIVETRAIN_LEFT = 8,
            CONTROL_PANEL = 0,
            SHOOTER_TOP = 1,
            SHOOTER_BOTTOM = 2,
            SHOOTER_IN = 3,
            LIFT_PULL = 4,
            LIFT_RAISE = 5;

        private PWM() {} // Should never be constructed.
    }

    public class DIO {
        
        private DIO() {} // Should never be constructed.
    }

    public class CAN {

        private CAN() {} // SHould never be constructed.
    }

     public class DS_USB {

        public static final int 
            LEFT_STICK = 0,
            RIGHT_STICK = 1;
        
        private DS_USB() {} // Should never be constructed.
     }

     public class Controls {

        public static final int 
            DRIVE_AXIS = 1,
            CONTROL_PANEL_LEFT = 6,
            CONTROL_PANEL_RIGHT = 5,
            SHOOTER_RAISE = 1,
            SHOOTER_SHOOT = 1,
            SHOOTER_IN = 5,
            DRIVETRAIN_LOCK = 2,
            SHOOTER_LOWER = 5,
            RAISE_LIFT = 4,
            LIFT_BOT = 3;

        private Controls() {} // Should never be constructed.
     }

}