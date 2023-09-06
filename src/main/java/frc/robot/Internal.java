package frc.robot;

import autolog.Logged;
import autolog.AutoLog.AL.NTLog;
import io.github.oblarg.oblog.Loggable;
import io.github.oblarg.oblog.annotations.Log;

public class Internal implements Logged, Loggable {

    @Override
    public String getPath() {
        // TODO Auto-generated method stub
        return null;
    }
    @Log
    @NTLog
    public String name="name";
}
