# -*- coding:utf-8 -*
import urllib, urllib2, re, sys, os, requests
# path = r"f:/24034931/"
# url = 'http://huaban.com/boards/24034931'

class hua():
    global i_headers 
    i_headers = {"User-Agent": "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.71 Safari/537.36"};
    def urlHandle(self,url):
        req = urllib2.Request(url, headers=i_headers);
        html = urllib2.urlopen(req).read();
        reg = re.compile(r'"pin_id":(\d+),.+?"file":{"farm":"farm1", "bucket":"hbimg",.+?"key":"(.*?)",.+?"type":"image/(.*?)"', re.S)
        groups = re.findall(reg, html)
        return groups;
    
    def imgHandle(self,groups, path):
        if groups:
            for att in groups:
                pin_id = att[0]
                att_url = att[1] + '_fw236'
                img_type = att[2]
                img_url = 'http://img.hb.aicdn.com/' + att_url
                r = requests.get(img_url)
                with open(path + att_url + '.' + img_type, 'wb') as fd:
                    for chunk in r.iter_content():
                        fd.write(chunk)
                        
    
    def start(self,path,url):
        count = 0;
        groups = self.urlHandle(url)
        self.imgHandle(groups,path)
        while(groups):
            count += 1
            print count
            pin_id = groups[-1][0]
            print pin_id
            urltemp = url + '/?max=' + str(pin_id) + '&limit=' + str(20) + '&wfl=1'
            print urltemp;
            # print(urltemp)
            groups = self.urlHandle(urltemp)
            # print groups
            self.imgHandle(groups, path)
            
