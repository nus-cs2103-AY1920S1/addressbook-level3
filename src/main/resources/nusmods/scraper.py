import requests
import pandas as pd

modules_url = "https://nusmods.com/api/v2/2019-2020/moduleList.json"
module_url = "https://nusmods.com/api/v2/2019-2020/modules/"
export_url = './lessons.json'

modules_json = requests.get(modules_url).json()
modules = [module['moduleCode'] for module in modules_json]

def getModObject(moduleCode):
    url = module_url + moduleCode + ".json"
    lessons = requests.get(url).json()['semesterData'][0]['timetable']
    for lesson in lessons:
        del lesson['venue']
        del lesson['size']
    lessons = [lesson for lesson in lessons if lesson['lessonType'] != 'Lecture' and type(lesson['weeks']) is list]
    return {'lessons': lessons, 'moduleCode': moduleCode}

modules = [getModObject(module) for module in modules]

df = pd.DataFrame.from_dict(modules)

df.to_json(export_url, orient='records')
