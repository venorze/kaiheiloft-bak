'''
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
'''

'''
Creates on 2023/2/6.
:author Vincent Luo
'''

'''
该文件负责下载所需环境所需安装包
'''
import os
import urllib.request

#
# 用户目录
#
save_path = os.path.expanduser('~')

#
# 当前下载文件名称
#
curfile = 'Non'

#
# 显示文件下载进度回调函数
#
def urlretrieve_callback(blocknum, blocksize, totalsize):
    percent = 100.0 * blocknum * blocksize / totalsize
    if percent > 100:
        percent = 100

    print('\rDownload %s %.2f%%' % (curfile, percent), end='', flush=True)

#
# 文件下载函数
#
def urlretrieve(url, name):
    global curfile
    # 重置当前下载文件名
    curfile = name
    # 下载文件
    urllib.request.urlretrieve(url, '%s/Downloads/%s' % (save_path, name), urlretrieve_callback)
    # 取消命令行刷新
    print()

'''
执行下载文件
'''
def auto_download_files():
    # git
    urlretrieve('https://registry.npmmirror.com/-/binary/git-for-windows/v2.35.2.windows.1/Git-2.35.2-64-bit.exe', 'Git-2.35.2-64-bit.exe')
    # apache maven
    urlretrieve('https://dlcdn.apache.org/maven/maven-3/3.8.7/binaries/apache-maven-3.8.7-bin.zip', 'apache-maven-3.8.7-bin.zip')
    # latiao subscribe (error)
    # urlretrieve('https://lameizi.buzz/api/v1/client/subscribe?token=6c0bf778de611b09d35b923a80c0b4b4&flag=clash', 'subscribe')
    # clash
    urlretrieve('https://ghproxy.com/https://github.com/ender-zhao/Clash-for-Windows_Chinese/releases/download/CFW-V0.19.5_CN/Clash.for.Windows.Setup.0.19.5.exe', 'Clash.for.Windows.Setup.0.19.5.exe')
    # idea
    urlretrieve('https://download.jetbrains.com/idea/ideaIU-2022.3.2.exe', 'ideaIU-2022.3.2.exe')
    # jdk19
    urlretrieve('https://d6.injdk.cn/openjdk/openjdk/19/openjdk-19.0.2_windows-x64_bin.zip', 'openjdk-19.0.2_windows-x64_bin.zip')


