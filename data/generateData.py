import json
import sys

size = int(sys.argv[1])

jsonFile = open("template.txt", "r")
jsonString = jsonFile.read()
jsonFile.close()

jsonObject = json.loads(jsonString)

outputFile = open("output.json", "w+")

outputFile.write("[")
for i in range(size):
  jsonObject["itemDescription"]["description"] = str(i)
  outputFile.write(json.dumps(jsonObject))
  if i != (size-1):
    outputFile.write(",")

outputFile.write("]")
outputFile.close()
