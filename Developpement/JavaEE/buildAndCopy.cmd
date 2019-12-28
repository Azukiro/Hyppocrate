@echo off
echo [INFO] Building...
call mvn clean install

echo [INFO] Removing old files...
del /q "%JBOSS_HOME%\standalone\deployments\Hypocrate.war"
rmdir /q /s "%JBOSS_HOME%\standalone\deployments\Hypocrate"

echo [INFO] Copying new files...
xcopy "%cd%\target\Hypocrate" "%JBOSS_HOME%\standalone\deployments\Hypocrate" /c /q /e /y /i
xcopy "%cd%\target\Hypocrate.war*" "%JBOSS_HOME%\standalone\deployments\Hypocrate.war*" /c /q /y