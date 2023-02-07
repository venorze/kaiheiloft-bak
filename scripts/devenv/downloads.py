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

'''
该文件负责下载所需环境所需安装包
'''
import os
import urllib.request

#
# 用户目录
#
GLOBAL_USER_ROOT = os.path.expanduser('~')

#
# 文件保存目录
#
GLOBAL_SAVE_PATH = '%s/.khldenvs/downloads' % os.path.expanduser('~')

#
# 当前下载文件名称和索引
#
GLOBAL_CURRENT_IDX = 0
GLOBAL_CURRENT_FILE = 'Non'


#
# 显示文件下载进度回调函数
#
def urlretrieve_callback(blocknum, blocksize, totalsize):
    percent = 100.0 * blocknum * blocksize / totalsize
    if percent > 100:
        percent = 100

    print('\r#%d Download %s %.2f%%' % (GLOBAL_CURRENT_IDX, GLOBAL_CURRENT_FILE, percent), end='', flush=True)


#
# 文件下载函数
#
def urlretrieve(url, name):
    global GLOBAL_CURRENT_FILE, GLOBAL_CURRENT_IDX
    # 重置当前下载文件名
    GLOBAL_CURRENT_FILE = name
    # 下载文件
    storelocation = '%s/%s' % (GLOBAL_SAVE_PATH, name)
    if not os.path.exists(storelocation):
        GLOBAL_CURRENT_IDX += 1
        urllib.request.urlretrieve(url, storelocation, urlretrieve_callback)
        # 取消命令行刷新
        print()


'''
执行下载文件，根据传入的urlmap遍历下载文件
'''
def xdownload(urlmap):
    for k in urlmap:
        urlretrieve(urlmap[k], k)
