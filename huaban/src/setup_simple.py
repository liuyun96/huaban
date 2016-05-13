#! /usr/bin/env python  
# -*- coding: utf-8 -*-  
#安装成windows服务的python脚本  
#内容：  
from distutils.core import setup  
import py2exe  
options = {"py2exe":{"compressed": 1, #压缩  
                     "optimize": 2,  
                     "bundle_files": 1 #所有文件打包成一个exe文件  
                     }}  
setup(  
    service=["PyWindowsService"],  
    options=options,  
    zipfile=None)   