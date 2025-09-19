// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.Drivetrain;

import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.MetersPerSecondPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.LinearAcceleration;
import edu.wpi.first.units.measure.LinearVelocity;

/** Add your docs here. */
public class DrivetrainConstants {

    public static final LinearVelocity kMaxSpeed = MetersPerSecond.of(2);
    public static final AngularVelocity MaxAngularRate = RotationsPerSecond.of(0.75); // 3/4 of a rotation per second max angular velocity
    public static final LinearAcceleration kMaxAcceleration = MetersPerSecondPerSecond.of(10);

    public static final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(DrivetrainConstants.kMaxSpeed.in(MetersPerSecond) * 0.1)
            .withRotationalDeadband(DrivetrainConstants.kMaxSpeed.in(MetersPerSecond) * 0.1) // Add a 10% deadband
            .withDriveRequestType(DriveRequestType.Velocity); // Use open-loop control for drive motors

}
