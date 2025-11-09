@echo off
REM Build script for Advanced OOP Payroll System
REM Usage: build.bat [clean|build|rebuild|run-migration|run-app]

setlocal enabledelayedexpansion

if "%1"=="" (
    set ACTION=build
) else (
    set ACTION=%1
)

if /i "%ACTION%"=="clean" goto clean
if /i "%ACTION%"=="build" goto build
if /i "%ACTION%"=="rebuild" goto rebuild
if /i "%ACTION%"=="run-migration" goto run_migration
if /i "%ACTION%"=="migration" goto run_migration
if /i "%ACTION%"=="run-app" goto run_app
if /i "%ACTION%"=="app" goto run_app

echo Unknown action: %ACTION%
echo.
echo Usage: build.bat [action]
echo.
echo Actions:
echo   clean          - Clean build directory
echo   build          - Build project (default)
echo   rebuild        - Clean and build
echo   run-migration  - Build and run password migration
echo   run-app        - Build and run main application
goto end

:clean
echo Cleaning build directory...
if exist build rmdir /s /q build
if exist sources.txt del /q sources.txt
echo Done.
goto end

:build
echo Building Java project...

REM Create build directory
if not exist build\classes mkdir build\classes

REM Find all Java source files (excluding test directory)
echo   Finding source files...
dir /s /b src\*.java > sources_all.txt
findstr /V /C:"\src\test\" sources_all.txt > sources.txt
del sources_all.txt

REM Count files
for /f %%a in ('type sources.txt ^| find /c /v ""') do set COUNT=%%a
echo   Found %COUNT% Java files (excluding tests)

REM Compile
echo   Compiling...
javac -encoding UTF-8 -cp "lib/*" -d build/classes -sourcepath src @sources.txt

if %ERRORLEVEL% EQU 0 (
    echo Build successful!
    goto end
) else (
    echo Build failed!
    exit /b 1
)

:rebuild
call :clean
call :build
goto end

:run_migration
call :build
if %ERRORLEVEL% EQU 0 (
    echo.
    echo Running password migration...
    java -cp "build/classes;lib/*" MigratePasswordsToBcrypt
)
goto end

:run_app
call :build
if %ERRORLEVEL% EQU 0 (
    echo.
    echo Running main application...
    java -cp "build/classes;lib/*" Main
)
goto end

:end
endlocal
