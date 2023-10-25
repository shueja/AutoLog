# AutoLog

Sources for library are in `./src/`

Development/benchmarking project is in `./dev-benchmark/`

## BENCHMARKING

1. Edit `dev-benchmark/src/main/java/frc/robot/Robot.java` and change `useOblog` to change between Oblog and AutoLog. Change `dataLog` when AutoLog is selected to change between logging to NT only or DataLog only.

2. Deploy robot code (or run in simulation). The test starts automatically and prints logger update timing samples every 10 seconds, then averages those samples after 80 seconds. Team number can be set in `.wpilib/wpilib_preferences.json`
