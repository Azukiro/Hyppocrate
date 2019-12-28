@echo off
echo [[92mINFO[0m] Building...
call mvn clean install
echo [[92mINFO[0m] Removing old files...
del /q "%JBOSS_HOME%\standalone\deployments\Hyppocrate.war"
rmdir /q /s "%JBOSS_HOME%\standalone\deployments\Hyppocrate"

echo [[92mINFO[0m] Copying new files...
xcopy "%cd%\target\Hyppocrate" "%JBOSS_HOME%\standalone\deployments\Hyppocrate" /c /q /e /y /i
xcopy "%cd%\target\Hyppocrate.war*" "%JBOSS_HOME%\standalone\deployments\Hyppocrate.war*" /c /q /y
echo [[92mINFO[0m] Copy Ended

