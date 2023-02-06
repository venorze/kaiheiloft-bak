@REM --------------------------------------------------------------------
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
@REM --------------------------------------------------------------------

@REM Creates on 2023/2/6.

@REM 关闭命令回显
@echo off

@REM
@REM 启动脚本
@REM

@REM 设置临时目录
set TMP_DIR=%USERPROFILE%/.tmp

@REM 如果存在就删除，再创建
if exist "%TMP_DIR%" rd /Q /S "%TMP_DIR%"
mkdir "%TMP_DIR%"

@REM 解压python解释器，将python解释器解压到用户目录下
set PYTHON_ENV_HOME=%TMP_DIR%/python
mkdir "%PYTHON_ENV_HOME%"
tar xf "./lib/python-3.11.1-embed-amd64.zip" -C "%PYTHON_ENV_HOME%"

@REM 设置python执行文件
set PY_EXE=%PYTHON_ENV_HOME%/python.exe
@REM 设置脚本目录
set SCRIPT_DIRECTORY=%~dp0/scripts/devenv/

@REM 执行脚本
cd %SCRIPT_DIRECTORY%
call %PY_EXE% ./setup.py