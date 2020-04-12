import json
import requests
from selenium import webdriver
from selenium.webdriver.support.ui import Select
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.common.by import By

USERNAME = ""
PASSWORD = ""

class CourseInfo:
    def __init__(id, name, prof, semester, prereqs):
        self.id = id
        self.name = name
        self.prof = prof
        self.semester = semester
        self.prereqs = prereqs
        
    def getID():
        return self.id
    def getName():
        return self.name
    def getProf():
        return self.prof
    def getSemester():
        return self.semester
    def getPrereqs():
        return self.prereqs
        
cs_request = {
    "other": {"srcdb": "201640"},
    "criteria": [
        {"field": "keyword", "value": "calculus"},
        {"field": "dept", "value": "MATH"}
    ]
}      
json_request = json.dumps(cs_request)




driver = webdriver.Chrome()

driver.get("https://cab.brown.edu/#")
login_button = driver.find_element_by_class_name('anon-only')
first_window = driver.current_window_handle
login_button.click()

windows = driver.window_handles
for window in windows:
    if (window != first_window):
        driver.switch_to.window(window)
        
driver.find_element_by_id('username').send_keys(USERNAME)
driver.find_element_by_id('password').send_keys(PASSWORD)
driver.find_element_by_name('_eventId_proceed').click()

WebDriverWait(driver, timeout=10).until(EC.presence_of_element_located((By.ID, "duo_iframe")))
driver.switch_to.frame("duo_iframe")
driver.find_element_by_xpath("//button[normalize-space()='Send Me a Push']").click()

driver.switch_to.window(first_window)
WebDriverWait(driver, timeout=20).until(EC.presence_of_element_located((By.CLASS_NAME, "authed-only")))
print(driver.find_element_by_class_name("user-name").text)

# Might need to wait until DUO push is completed on phone

test_info = requests.post('https://cab.brown.edu/course-search/api/?page=fose&route=search', data=json_request)
print(test_info)