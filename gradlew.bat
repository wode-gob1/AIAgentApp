@rem Gradle startup script for Windows
@if "%DEBUG%"=="" echo off
setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%

set DEFAULT_JVM_OPTS="-Xmx64m" "-Xms64m"

if exist "%APP_HOME%\gradle\wrapper\gradle-wrapper.jar" (
    set CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar
) else (
    echo Error: gradle-wrapper.jar not found
    exit /b 1
)

"%JAVA_HOME%\bin\java.exe" %DEFAULT_JVM_OPTS% %JAVA_OPTS% -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*

if "%ERRORLEVEL%"=="0" goto executeSuccess
:executeFail
echo ERROR: Gradle build failed with exit code %ERRORLEVEL%

:executeSuccess
endlocal
