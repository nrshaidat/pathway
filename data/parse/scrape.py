import urllib.request
import requests
import re
import time
from bs4 import BeautifulSoup as bs

''' Scraping data from Bulletin.Brown to get concentration information '''
url = 'https://bulletin.brown.edu/the-college/concentrations/'

response = requests.get(url)

soup = bs(response.text, "html.parser")

#Stores all URLs of concentrations in a list
url_list = []
line = 1
for aTag in soup.findAll('a'):
    if line >= 38 and line < 120:
        link = aTag['href']
        dl_url = 'https://bulletin.brown.edu' + link
        url_list.append(dl_url)
    line += 1


'''NOTE: This is temporarily left hard coded. After trying the parser on
a number of concentrations, we can make different methods to parse different
concentrations on Brown Bulletin (not all tables have the same schema)'''

#All publicly available concentration requirements have:
#link format: 'https://bulletin.brown.edu/the-college/concentrations/?'
test_url = 'https://bulletin.brown.edu/the-college/concentrations/apmc/'
response = requests.get(test_url)
soup = bs(response.text, "html.parser")

# For APMA CS concentration, finds the scb courselist.
table = soup.find('table', attrs={"class": "sc_courselist"})
table_rows = table.find_all('tr')

# TODO: Somehow gets a list of classes, but not clean
result = []
for row in table_rows:
    #Finds all links with a tag. We can strip the course names from links.
    td = row.find_all('a')

    # output = td.find('data-code')
    #['data-code']

    for i in td:
        result.append(i.get_text())
        print(result)

prereqs = []

# table = bs.find(lambda tag: tag.name=='table' and tag.has_attr('class')) # and tag['class']=="sc_courselist"
# rows = table.findAll(lambda tag: tag.name=='tr')
# for row in table_rows:
#     td = row.find_all('td')
#     print (td)
#     #each = [i.text for i in td]
#
#     #Parsing span class Prerequisites
#
#     #prereq = row.find_all('')
#     '''Gets top level table types'''
#     spans = soup.find_all('span', {'class' : 'courselistcomment areaheader'})
#     # print(spans)
#
#     lines = [span.get_text() for span in spans]
#
# print(soup.find("span", id = 'courselistcomment areaheader'))
