"""
Copyright (C) 2020 Vincent Luo All rights reserved.

Licensed under the Apache License, Version 2.0 (the 'License');
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an 'AS IS' BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
"""

'''
Creates on 2023/2/6.
:author Vincent Luo
'''

import sys
import os
import shutil

'''
将当前工作目录添加到环境变量
'''
sys.path.append(os.getcwd())

'''
执行脚本，开始配置开发环境
'''
from downloads import xdownload, GLOBAL_SAVE_PATH
from install import xinstall
from namedef import *
from install import cnotexist

cnotexist(GLOBAL_SAVE_PATH)

#
# 所需下载的文件列表
#
urlmap = {
    GIT: 'https://registry.npmmirror.com/-/binary/git-for-windows/v2.35.2.windows.1/Git-2.35.2-64-bit.exe',
    CLASH: 'https://ghproxy.com/https://github.com/ender-zhao/Clash-for-Windows_Chinese/releases/download/CFW-V0.19.5_CN/Clash.for.Windows.Setup.0.19.5.exe',
    IDEA: 'https://download.jetbrains.com/idea/ideaIU-2022.3.2.exe',
    JDK19: 'https://d6.injdk.cn/openjdk/openjdk/19/openjdk-19.0.2_windows-x64_bin.zip'
}

#
# 拷贝 apache maven 到 ~/Downloads/.khld
#
mavenname = 'apache-maven-3.8.7-bin.zip'
shutil.copyfile('../../lib/%s' % mavenname, '%s/%s' % (GLOBAL_SAVE_PATH, mavenname))

#
# step 1: 判断下载文件夹是否存在，如果不存在就创建
#
if not os.path.exists(GLOBAL_SAVE_PATH):
    os.mkdir(GLOBAL_SAVE_PATH)

#
# step 2: 下载所需文件
#
xdownload(urlmap)

#
# step 3: 安装对应文件。安装顺序如下：
#
xinstall()
