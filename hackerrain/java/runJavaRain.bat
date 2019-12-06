:begin
@echo off
title -- - CLIENT -----
color a
cls

@echo 初始化进程，请稍候....
echo %JAVA_HOME%
if exist %JAVA_HOME%\lib\tools.jar (
    set CP=%JAVA_HOME%\lib\tools.jar
)
set JAVA_OPTS=-Xms256m -Xmx512m -XX:PermSize=128m -XX:MaxPermSize=256m
@echo 启动自动更新服务，请稍候....
%JAVA_HOME%\bin\java %JAVA_OPTS% -classpath %CP% -cp rain.jar Rain
rem if "console"=="%1" goto console
rem goto end

:end