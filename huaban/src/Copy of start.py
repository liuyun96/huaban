#!/bin/env python  
# -*- coding: utf-8 -*-   
import wx

class Firstframe(wx.Frame):
    def __init__(self):  
        wx.Frame.__init__(self, parent=None,title="My Test Frame",pos = (100,100), size=(500,450))  
              #添加第1个Panel面板  
        panel1 = wx.Panel(parent=self,pos = (0,80), size=(225, 250))  
        panel1.Bind(wx.EVT_MOTION,  self.OnPanel1Move)  
        #添加其他控件  
        wx.StaticText(parent=panel1, label= " Cursor Pos:", pos=(10, 10),size=(100, 25))  
        self.posCtrl1 = wx.TextCtrl(parent=panel1, value = "0,0", pos=(100, 10),size=(100, 25))  
          
        #添加第2个Panel面板  
        panel2 = wx.Panel(parent=self,pos = (275,80), size=(225, 250))  
        #添加其他控件  
        wx.StaticText(parent=panel2,label= " The Second Panel", pos=(10, 50),size=(150, 25))  
        self.btn=wx.Button(parent=panel2,label= " My Button",pos=(10, 100),size=(150, 25))  
        self.btn.Bind(wx.EVT_BUTTON,  self.OnMyButtonClick)  
          
        #添加wxStatusBar工具条  
        self.sb=self.CreateStatusBar(number =3)  
        self.SetStatusText("One",0)  
        self.SetStatusText("Two",1)  
        self.SetStatusText("Three",2)  
        
        #添加wxToolBar  
        self.tb=self.CreateToolBar()  
        bitmap1 = wx.EmptyBitmapRGBA(32, 24, red=0, green=0, blue=0, alpha=100)  
        self.tb.AddSeparator()  
        self.tb.AddLabelTool(1,'',bitmap1)  
        self.tb.AddSeparator()  
        bitmap2 = wx.EmptyBitmapRGBA(32, 24, red=0, green=0, blue=0, alpha=150)          
        self.tb.AddLabelTool(2,'',bitmap2)          
        self.tb.Realize()  
          
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
    def OnPanel1Move(self, event): #在Panel1上面移动的时调用  
        pos = event.GetPosition()  
        self.posCtrl1.SetValue("%s, %s" % (pos.x, pos.y))  
          
    def OnMyButtonClick(self,event): #在按钮上面单击调用  
        self.btn.SetLabel("You Clicked!")  
          
    def OnQuit(self, event): #点击退出菜单时调用  
        self.Close()  
          
          
#################################################################################  
if __name__ == '__main__':  
    app = wx.App()  
    frame = Firstframe()  
    app.MainLoop()  
#################################################################################  
