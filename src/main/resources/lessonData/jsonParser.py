import pandas as pd

url = "./moduleTimeTableDeltaRaw.json"
export_url = './lessons.json'

df = pd.read_json(url, orient='columns')

del df['AcadYear']
del df['DayCode']
del df['LastModified']
del df['LastModified_js']
del df['Semester']
del df['Venue']
del df['WeekCode']
del df['isDelete']

df_filtered = df[(df.LessonType != 'LECTURE')]

Export = df.to_json(export_url, orient='records')

df_export = pd.read_json(export_url, orient='columns')
print(df_export.head(10))

# df_filtered = df[(df.LessonType != 'LECTURE') & (df.WeekText != "EVERY WEEK")]
# print(df_filtered.head(50))
