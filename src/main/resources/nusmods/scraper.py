import requests
import pandas as pd

modules_url = "https://nusmods.com/api/v2/2019-2020/moduleList.json"
module_url = "https://nusmods.com/api/v2/2019-2020/modules/"
export_url = './lessonsFinal.json'

modules_json = requests.get(modules_url).json()
modules = [module['moduleCode'] for module in modules_json]

def getModObject(moduleCode):
    url = module_url + moduleCode + ".json"
    semesters = requests.get(url).json()['semesterData']
    for semester in semesters:
        lessons = semester['timetable']
        for lesson in lessons:
            del lesson['venue']
            del lesson['size']
        semester['timetable'] = [lesson for lesson in lessons if lesson['lessonType'] != 'Lecture' and type(lesson['weeks']) is list]
    return {'semesters': semesters, 'moduleCode': moduleCode}

modules = [getModObject(module) for module in modules]

df = pd.DataFrame.from_dict(modules)

df.to_json(export_url, orient='records')
