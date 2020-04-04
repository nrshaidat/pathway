import json
import requests

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
        
cs_request_object = {
    "other": {"srcdb": "201640"},
    "criteria": [
        {"field": "keyword", "value": "calculus"},
        {"field": "dept", "value": "MATH"}
    ]
}      
test_request_string = json.dumps(cs_request_object)
test_info = requests.get('https://cab.brown.edu/course-search/api/?page=fose&route=search', params=cs_request_object)
print(test_info)