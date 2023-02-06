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
from downfiles import GLOBAL_SAVE_PATH

#
# 遍历文件然后安装
#
def xinstall(urlmap):
    for k in urlmap:
        path = '%s/%s' % (GLOBAL_SAVE_PATH, k)
        if os.path.exists(path):
            print('%s/%s' % (GLOBAL_SAVE_PATH, k))
        # os.system('%s/%s' % (GLOBAL_SAVE_PATH, k))

