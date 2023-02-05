@REM ************************************************************************
@REM
@REM Copyright (C) 2020 Vincent Luo All rights reserved.
@REM
@REM Licensed under the Apache License, Version 2.0 (the "License");
@REM you may not use this file except in compliance with the License.
@REM You may obtain a copy of the License at
@REM
@REM     http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing, software
@REM distributed under the License is distributed on an "AS IS" BASIS,
@REM WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@REM See the License for the specific language governing permissions and
@REM limitations under the License.
@REM
@REM ************************************************************************

@REM 关闭命令回显
@echo off

@REM 保存目录
set SAVE_DIR="$env:userprofile/Downloads"

@REM --------------------------------
@REM 1. git
@REM --------------------------------
set GIT_URL="https://github.com/git-for-windows/git/releases/download/v2.39.1.windows.1/Git-2.39.1-64-bit.exe"
set GIT_NAME="Git-2.39.1-64-bit.exe"
start "download git" powershell /c Invoke-WebRequest -uri "%GIT_URL%" -OutFile "%SAVE_DIR%/%GIT_NAME%"

@REM --------------------------------
@REM 2. maven
@REM --------------------------------
set MAVEN_URL="https://dlcdn.apache.org/maven/maven-3/3.8.7/binaries/apache-maven-3.8.7-bin.zip"
set MAVEN_NAME="apache-maven-3.8.7-bin.zip"
start "download maven" powershell /c Invoke-WebRequest -uri "%MAVEN_URL%" -OutFile "%SAVE_DIR%/%MAVEN_NAME%"

@REM --------------------------------
@REM 3. java19
@REM --------------------------------
set JDK_URL="https://d6.injdk.cn/openjdk/openjdk/19/openjdk-19.0.2_windows-x64_bin.zip"
set JDK_NAME="jdk-19.0.2_windows-x64_bin.exe"
start "download jdk19" powershell /c Invoke-WebRequest -uri "%JDK_URL%" -OutFile "%SAVE_DIR%/%JDK_NAME%"

@REM --------------------------------
@REM 4. idea
@REM --------------------------------
set IDEA_URL="https://download.jetbrains.com/idea/ideaIU-2022.3.2.exe?_gl=1*160v1e6*_ga*MTk4NjE2MTUwOC4xNjc1NTk0Njc4*_ga_9J976DJZ68*MTY3NTYwODQ0Mi4yLjEuMTY3NTYwODQ2MC4wLjAuMA..&_ga=2.37991402.871307033.1675594678-1986161508.1675594678"
set IDEA_NAME="idea-2022.3.2.exe"
start "download idea-2022.3.2" powershell /c Invoke-WebRequest -uri "%IDEA_URL%" -OutFile "%SAVE_DIR%/%IDEA_NAME%"

@REM --------------------------------
@REM 5. clash
@REM --------------------------------
set CLASH_URL="https://github.com/Fndroid/clash_for_windows_pkg/releases/download/0.20.15/Clash.for.Windows.Setup.0.20.15.exe"
set CLASH_NAME="Clash.for.Windows.Setup.0.20.15.exe"
start "download clash" powershell /c Invoke-WebRequest -uri "%CLASH_URL%" -OutFile "%SAVE_DIR%/%CLASH_NAME%"

@REM --------------------------------
@REM 6. 辣条配置
@REM --------------------------------
set LATIAO_URL="https://lameizi.buzz/api/v1/client/subscribe?token=6c0bf778de611b09d35b923a80c0b4b4&flag=clash"
set LATIAO_NAME="subscribe"
start "download latiao subscribe" powershell /c Invoke-WebRequest -uri "%LATIAO_URL%" -OutFile "%SAVE_DIR%/%LATIAO_NAME%"
