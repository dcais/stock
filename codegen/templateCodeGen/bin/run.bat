@SetLocal EnableDelayedExpansion

@set MAIN_CLASS="com.tqmall.river.guardian.RiverGuardian"

@set CONFIG_PATH=%1
@if "%CONFIG_PATH%"=="" goto END

 
@set CLASSPATH=.
@FOR %%i IN ("%~dp0\..\libs\*.jar") DO SET CLASSPATH=!CLASSPATH!;%%i

@java -classpath %CLASSPATH% %MAIN_CLASS% -c %~dp0\..\%CONFIG_PATH%

:END