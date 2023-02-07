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

@REM close command print.
@echo off

@REM
@REM start bat script.
@REM

@REM backup current work directory.
set BACKUP_CWD=%~dp0

@REM set template directory
set TMP_DIR=%USERPROFILE%/.khldenvs

@REM if the template directory not exist that create.
if not exist "%TMP_DIR%" mkdir "%TMP_DIR%"

@REM unzip python environment.
set PYTHON_ENV_HOME=%TMP_DIR%/python
if not exist "%PYTHON_ENV_HOME%" (
    mkdir "%PYTHON_ENV_HOME%"
    tar xf "./lib/python-3.11.1-embed-amd64.zip" -C "%PYTHON_ENV_HOME%"
)

@REM set python execute file.
set PY_EXE=%PYTHON_ENV_HOME%/python.exe

@REM set script directory.
@REM the %~dp0 is current execute work directory.
set SCRIPT_DIRECTORY=%~dp0/scripts/devenv/

@REM execute script.
cd %SCRIPT_DIRECTORY%
call %PY_EXE% ./setup.py

@REM return to execute work directory.
cd %BACKUP_CWD%