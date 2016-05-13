#!/bin/env python  
# -*- coding: utf-8 -*-   
import wx
from hua import hua;

# path = r"f:/24034931/"
# url = 'http://huaban.com/boards/24034931'

class Firstframe(wx.Frame):
    def __init__(self):  
        wx.Frame.__init__(self, parent=None,title="HuaBan",pos = (100,100), size=(500,450))  
              #添加第1个Panel面板  
        panel1 = wx.Panel(parent=self,pos = (0,80), size=(225, 250))  
        #panel1.Bind(wx.EVT_MOTION,  self.OnPanel1Move)  
        #添加其他控件  
        wx.StaticText(parent=panel1, label= " path :", pos=(10, 10),size=(50, 25))  
        self.posCtrl1 = wx.TextCtrl(parent=panel1, value = "f:/24034931/", pos=(100, 10),size=(200, 25))
        
        wx.StaticText(parent=panel1, label= " url :", pos=(10, 50),size=(50, 25))  
        self.posCtrl2 = wx.TextCtrl(parent=panel1, value = "http://huaban.com/boards/24034931", pos=(100, 50),size=(200, 25))
        
        self.btn=wx.Button(parent=panel1,label= " start ",pos=(100, 100),size=(150, 25))  
        self.btn.Bind(wx.EVT_BUTTON,  self.OnMyButtonClick)      
          
        #添加wxMenuBar菜单,提供了几种创建菜单的方式  
        menubar = wx.MenuBar()  
        #一级主菜单  
        file = wx.Menu()  
        file.Append(-1, '&New')  
        file.Append(-1, '&Open')  
        file.Append(-1, '&Save')  
        file.AppendSeparator()  
        #多级子菜单  
        imp = wx.Menu()   
        imp.Append(-1, 'Import newsfeed list...')  
        imp.Append(-1, 'Import bookmarks...')  
        imp.Append(-1, 'Import mail...')  
        
        file.AppendMenu(-1, 'I&mport', imp)  
        file.AppendSeparator()  
        #再添加一个菜单  
        quit = wx.MenuItem(file, wx.ID_CLOSE, '&Quit/tCtrl+W')  
        self.Bind(wx.EVT_MENU, self.OnQuit, id=wx.ID_CLOSE) #绑定方法  
        file.AppendItem(quit)  
        
        menubar.Append(file, '&File')  
        self.SetMenuBar(menubar)  
          
        self.Centre() #居中显示  
        self.Show(True)#总是一创建就显示Frame框架,  
          
    #定义事件方法  
    def OnMyButtonClick(self,event): #在按钮上面单击调用  
        self.btn.SetLabel("You Clicked!")
        path = self.posCtrl1.GetValue();
        url = self.posCtrl2.GetValue();
        print path
        print url
        huanew = hua()
        huanew.start(path,url);
          
    def OnQuit(self, event): #点击退出菜单时调用  
        self.Close()  
          
#################################################################################  
if __name__ == '__main__':  
    app = wx.App()  
    frame = Firstframe()  
    app.MainLoop()  
#################################################################################  
