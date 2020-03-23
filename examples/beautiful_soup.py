import requests
from bs4 import BeautifulSoup
import sqlite3

page = requests.get("https://bulletin.brown.edu/the-college/concentrations/comp/")
soup = BeautifulSoup(page.text, "lxml")

#print(soup.prettify())
req_tables = soup.find_all(class_="sc_courselist")
for result in req_tables:
    print(result)
    print("\n\n")
    
