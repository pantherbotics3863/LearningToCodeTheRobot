

package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.FollowPathCommand;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.subsystems.Drivetrain.CommandSwerveDrivetrain;
import frc.robot.subsystems.Drivetrain.Drive;
import frc.robot.subsystems.Drivetrain.DrivetrainConstants;
import frc.robot.subsystems.Drivetrain.Telemetry;
import frc.robot.subsystems.Drivetrain.TunerConstants;
import frc.robot.subsystems.Intake.IntakeConstants;
import frc.robot.subsystems.Intake.IntakePivot;
import frc.robot.subsystems.Intake.IntakeRollers;

public class RobotContainer {

    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(DrivetrainConstants.kMaxSpeed.in(MetersPerSecond) * 0.1)
            .withRotationalDeadband(DrivetrainConstants.kMaxSpeed.in(MetersPerSecond) * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors

    private final Telemetry logger = new Telemetry(DrivetrainConstants.kMaxSpeed.in(MetersPerSecond));

    private final CommandXboxController joystick = new CommandXboxController(0);

    private final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();

    private final IntakePivot intakePivot = new IntakePivot();
    
    private final IntakeRollers intakeRollers = new IntakeRollers();

    private final SendableChooser<Command> autoChooser;

    public RobotContainer() {
        autoChooser = AutoBuilder.buildAutoChooser("Tests");
        SmartDashboard.putData("Auto Mode", autoChooser);

        configureBindings();

        FollowPathCommand.warmupCommand().schedule();
    }

    private void configureBindings() {
        drivetrain.setDefaultCommand(
            new Drive(drivetrain, joystick.getHID())
        );

        intakeRollers.setDefaultCommand(
            intakeRollers.runVoltage(Volts.of(0))
        );

        intakePivot.setDefaultCommand(
            intakePivot.setPosition(IntakeConstants.kStowAngle)
        );

        // drivetrain.setDefaultCommand(

        // drivetrain.applyRequest(() ->
        //         drive.withVelocityX(-joystick.getLeftY() * DrivetrainConstants.kMaxSpeed.in(MetersPerSecond)) // Drive forward with negative Y (forward)
        //             .withVelocityY(-joystick.getLeftX() * DrivetrainConstants.kMaxSpeed.in(MetersPerSecond)) // Drive left with negative X (left)
        //             .withRotationalRate(-joystick.getRightX() * DrivetrainConstants.MaxAngularRate.in(RadiansPerSecond)) // Drive counterclockwise with negative X (left)
        //     )
        // );

        joystick.back().and(joystick.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        joystick.back().and(joystick.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        joystick.start().and(joystick.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        joystick.start().and(joystick.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        joystick.povUp().onTrue(drivetrain.runOnce(() -> drivetrain.seedFieldCentric()));

        joystick.leftTrigger().onTrue(intakePivot.zero());

        drivetrain.registerTelemetry(logger::telemeterize);
    }

    public Command getAutonomousCommand() {
        return autoChooser.getSelected();
    }
}
