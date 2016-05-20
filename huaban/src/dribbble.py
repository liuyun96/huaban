# -*- coding:utf-8 -*
import urllib, urllib2, re, sys, os, requests
from pyquery import PyQuery  as pq
reload(sys)
sys.setdefaultencoding('utf-8') #@UndefinedVariable
class Dribbble():
    global i_headers 
    i_headers = {"User-Agent": "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.71 Safari/537.36"};
    def urlHandle(self,url,path):
        try:
            req = urllib2.Request(url, headers=i_headers);
            html = urllib2.urlopen(req).read()
            link = pq(html)('.dribbble-link');
            for a in link:
                href = pq(a).attr("href");
                self.doHref(href,path);
        except:
            print sys.exc_info()
    def gGetFileName(self,url): 
        if url==None: return None 
        if url=="" : return "" 
        arr=url.split("/") 
        return arr[len(arr)-1] 
    def doHref(self,href,path):
        try:
            req = urllib2.Request("http://dribbble.com"+href, headers=i_headers);
            html = urllib2.urlopen(req).read()
            img = pq(html)('.single-img img');
            src = pq(img).attr("src").replace("https","http");
            print src;
            filepath = path+self.gGetFileName(src);
            ischeck = os.path.exists(filepath)
            if not ischeck:
                r = requests.get(src)
                with open(filepath,'wb') as fd:
                    for chunk in r.iter_content():
                        fd.write(chunk)
                    fd.close();
        except:
            print sys.exc_info()
    def start(self,path,page):
        for i in range(int(page), 50):
           self.urlHandle("https://dribbble.com/shots?page="+str(i),path);
if __name__ == '__main__':
   path = r"f:/24034931/"
   url = 'https://dribbble.com/shots?page=1'  
   dribbble = Dribbble();
   dribbble.start(path, 1);
   ##dribbble.doHref("/shots/2720047-Relax-with-good-book",path)
            
