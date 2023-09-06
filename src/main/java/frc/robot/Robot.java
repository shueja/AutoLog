// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import autolog.AutoLog;
import autolog.Logged;
import autolog.AutoLog.AL.BothLog;
import autolog.AutoLog.AL.NTLog;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.WPIUtilJNI;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.event.BooleanEvent;
import edu.wpi.first.wpilibj.event.EventLoop;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.Logger;
import io.github.oblarg.oblog.annotations.Log;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot implements Logged, Loggable {

  @Log
  @NTLog
  public int number = 0;

  double totaltime;
  int samples;

  public Translation2d m_translation = new Translation2d(0, 1);
  //Internal m_internal = new Internal();
  private GenericHID hid = new GenericHID(0);
  private EventLoop loop = new EventLoop();
  private LinearFilter filter = LinearFilter.movingAverage(50);
  
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    //DataLogManager.start();
    NetworkTableInstance.getDefault().getTopic("name").getGenericEntry();
    Logger.configureLoggingAndConfig(this, false);
    //AutoLog.setupLoggableLogging(this, "Robot", true);
    //new BooleanEvent(loop, ()->hid.getRawButtonPressed(1)).rising().ifHigh(()->number++);
  }

  @Override
  public void robotPeriodic() {
    var timeBefore = Timer.getFPGATimestamp() * 1e6;
    //AutoLog.update();
    Logger.updateEntries();
    var timeAfter = Timer.getFPGATimestamp() * 1e6;
    samples++;
    double avg = filter.calculate(timeAfter-timeBefore);
    if (samples % 1000 == 0) {
      System.out.println(avg);
    }


    //loop.poll();
  }
  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {}

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {}

  @Override
  public String getPath() {
    // TODO Auto-generated method stub
    return "Robot";
  }


}
