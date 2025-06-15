@echo off
javac mainProg.java
if %errorlevel% neq 0 (
    echo Compilation failed.
    pause
    exit /b %errorlevel%
)
java -cp . mainProg
pause