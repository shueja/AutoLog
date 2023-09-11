// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.ArrayList;

import autolog.AutoLog;
import autolog.Logged;
import autolog.AutoLog.BothLog;
import edu.wpi.first.math.filter.LinearFilter;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.Logger;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot implements Logged, Loggable {

  int samples;
  boolean useOblog = false;
  boolean dataLog = true;
  
  ArrayList<Internal> m_internals = new ArrayList<>(
  );
  private LinearFilter filter = LinearFilter.movingAverage(50);
  double totalOfAvgs = 0;
  double avgsTaken = 0;
  
  @BothLog(path = "ThePose")
  private Pose2d m_pose = new Pose2d();
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    SmartDashboard.putBoolean("bool", true);
    AutoLog.dataLogger.addNetworkTable(NetworkTableInstance.getDefault().getTable("SmartDashboard"));
    for (int i = 0; i < 100; i++) {
      m_internals.add(new Internal(i + ""));
    }
    //DataLogManager.start();
    NetworkTableInstance.getDefault().getTopic("name").getGenericEntry();
    if (useOblog) {
      Logger.configureLoggingAndConfig(this, false);
    } else {
      AutoLog.setupLogging(this, "Robot", true);
    }
  }
  @Override
  public void robotPeriodic() {
    var timeBefore = Timer.getFPGATimestamp() * 1e6;
    if (useOblog) {
      Logger.updateEntries();
    } else {
      if (dataLog) {
        AutoLog.updateDataLog();
      } else {
        AutoLog.updateNT();
      }

    }
    var timeAfter = Timer.getFPGATimestamp() * 1e6;
    samples++;
    double avg = filter.calculate(timeAfter-timeBefore);
    if (samples % 500 == 0 && samples < (500 * 8) + 50) {
      System.out.println(avg);
      totalOfAvgs += avg;
      avgsTaken ++;
    }
    if (samples == 500 * 8) {
      System.out.println("Final Result: Oblog:" + useOblog + " DataLog:" + dataLog);
      System.out.println(totalOfAvgs/avgsTaken);
      
    }
    m_internals.forEach(Internal::update);
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
