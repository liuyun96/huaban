# -*- coding: utf-8 -*- 
from distutils.core import setup  
import py2exe  
  
includes = ["encodings", "encodings.*"]  
  
options = {"py2exe":    
            {"compressed": 1, #压缩    
             "optimize": 2,    
             "ascii": 1,    
             "includes":includes,
             "dll_excludes": [ "MSVCP90.dll", "mswsock.dll", "powrprof.dll","w9xpopen.exe"],
             "bundle_files": 2 #所有文件打包成一个exe文件  
            }}  
setup(  
    zipfile=None,  
    console=[{"script": "huaban.py"}],  
    #windows=[{"script": "wxTest.py", "icon_resources": [(1, "pc.ico")]}],  
    #data_files=[("magic",["App_x86.exe",]),],  
    version = "2010.11.01.01",   
    description = "this is a py2exe test",   
    name = "huaban." 
)  