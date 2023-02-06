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

import sys
import os

'''
将当前工作目录添加到环境变量
'''
sys.path.append(os.getcwd())

'''
执行脚本，开始配置开发环境
'''
from downfiles import auto_download_files

# step 1: 下载所需文件
auto_download_files()

