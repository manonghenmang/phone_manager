@echo off
REM 自定义构建脚本，强制使用JDK 11

SET JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-11.0.29.7-hotspot
SET PATH=%JAVA_HOME%\bin;%PATH%

echo 使用JDK版本：
java -version

echo 开始构建...
call gradlew.bat clean build

pause


1111