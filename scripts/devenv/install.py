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
import os

'''
安装已下载的应用
'''
from downloads import GLOBAL_SAVE_PATH, GLOBAL_USER_ROOT
from namedef import *

GLOBAL_INSTALL_PATH = "%s/.khldenvs" % GLOBAL_USER_ROOT

'''
如果文件不存在就创建
'''
def cnotexist(pathname):
    if not os.path.exists(pathname):
        os.mkdir(pathname)
    return pathname

'''
解压文件
'''
def ifnot_exist_unzip(savepath, filename):
    storefile = "%s/%s" % (savepath, os.path.splitext(filename)[0])
    if not os.path.exists(storefile):
        os.mkdir(storefile)
        file = "%s/%s" % (GLOBAL_SAVE_PATH, filename)
        os.system("tar xf %s -C %s" % (file, storefile))
        return True

    return False

#
# 遍历文件然后安装
#
def xinstall():
    # 判断保存文件夹是否存在
    if not os.path.exists(GLOBAL_INSTALL_PATH):
        os.mkdir(GLOBAL_INSTALL_PATH)

    # 遍历文件夹
    for filename in os.listdir(GLOBAL_SAVE_PATH):
        pathname = os.path.join(GLOBAL_SAVE_PATH, filename)
        if os.path.isfile(pathname):
            if 'openjdk' in filename:
                ijdk()
            elif 'maven' in filename:
                imaven()
            elif 'Git' in filename:
                igit(pathname)
            elif 'idea' in filename:
                iidea(pathname)
            elif 'Clash' in filename:
                iclash(pathname)

'''
jdk
'''
def ijdk():
    # 判断 jdks 文件夹是否存在，如果不存在则创建
    savepath = cnotexist("%s/jdks" % GLOBAL_INSTALL_PATH)
    # 解压JDK文件
    if ifnot_exist_unzip(savepath, JDK19):
        print("install %s --- OK" % JDK19)

'''
maven
'''
def imaven():
    # 判断 mavens 文件夹是否存在，如果不存在则创建
    savepath = cnotexist("%s/mavens" % GLOBAL_INSTALL_PATH)
    # 解压JDK文件
    if ifnot_exist_unzip(savepath, MAVEN):
        print("install %s --- OK" % MAVEN)

'''
git
'''
def igit(pathname):
    os.system("%s /S" % pathname)
    print("install %s --- OK" % GIT)

'''
idea
'''
def iidea(pathname):
    os.system("%s /S" % pathname)
    print("install %s --- OK" % IDEA)

'''
clash
'''
def iclash(pathname):
    os.system("%s /S" % pathname)
    print("install %s --- OK" % CLASH)