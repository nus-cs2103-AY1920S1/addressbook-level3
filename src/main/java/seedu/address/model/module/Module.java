package seedu.address.model.module;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Module {
    private final ModuleCode moduleCode;
    private final Title title;
    private final Description description;
    private final AcadYear acadYear;
    private final SemesterData semesterData;


    public Module(JSONObject obj){
        this.moduleCode = new ModuleCode(obj.get("moduleCode").toString());
        this.title = new Title(obj.get("title").toString());
        this.description = new Description(obj.get("description").toString());
        this.acadYear = new AcadYear(obj.get("acadYear").toString());
        this.semesterData = new SemesterData((JSONArray)obj.get("semesterData"));
    }

    public Description getDescription() {
        return description;
    }

    public ModuleCode getModuleCode(){
        return moduleCode;
    }

    public Timetable getTimeTable(String semesterNo, String classNo){
        Timetable timetable = semesterData.getSemester(semesterNo).getTimetable(classNo);
        return timetable;
    }

    public Semester getSemester(String semesterNo){
        Semester semester = semesterData.getSemester(semesterNo);
        return semester;
    }
}

/*
{
  "acadYear": "2018/2019",
  "preclusion": "ST1131, ST1131A, ST1232, ST2131, MA2216, CE2407, EC2231, EC2303, PR2103, DSC2008. ME students taking or having taken ME4273. All ISE students.",
  "description": "Basic concepts of probability, conditional probability, independence, random variables, joint and marginal distributions, mean and variance, some common probability distributions, sampling distributions, estimation and hypothesis testing based on a normal population.  This module is targeted at students who are interested in Statistics and are able to meet the pre-requisites. Preclude ME students taking or have taken ME4273.",
  "title": "Probability and Statistics",
  "department": "Statistics and Applied Probability",
  "faculty": "Science",
  "workload": [
    3,
    1,
    0,
    3,
    3
  ],
  "prerequisite": "MA1102R or MA1312 or MA1505 or MA1507 or MA1521",
  "moduleCredit": "4",
  "moduleCode": "ST2334",
  "semesterData": [
    {
      "semester": 1,
      "timetable": [
        {
          "classNo": "2",
          "startTime": "1400",
          "endTime": "1500",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "S16-06118",
          "day": "Monday",
          "lessonType": "Tutorial",
          "size": 67
        },
        {
          "classNo": "8",
          "startTime": "1000",
          "endTime": "1100",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "S16-06118",
          "day": "Friday",
          "lessonType": "Tutorial",
          "size": 48
        },
        {
          "classNo": "1",
          "startTime": "1000",
          "endTime": "1200",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "UT-AUD2",
          "day": "Thursday",
          "lessonType": "Lecture",
          "size": 335
        },
        {
          "classNo": "1",
          "startTime": "1000",
          "endTime": "1200",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "UT-AUD2",
          "day": "Monday",
          "lessonType": "Lecture",
          "size": 335
        },
        {
          "classNo": "7",
          "startTime": "1100",
          "endTime": "1200",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "S16-06118",
          "day": "Wednesday",
          "lessonType": "Tutorial",
          "size": 48
        },
        {
          "classNo": "3",
          "startTime": "1100",
          "endTime": "1200",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "S16-06118",
          "day": "Tuesday",
          "lessonType": "Tutorial",
          "size": 67
        },
        {
          "classNo": "1",
          "startTime": "1300",
          "endTime": "1400",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "S16-06118",
          "day": "Monday",
          "lessonType": "Tutorial",
          "size": 67
        },
        {
          "classNo": "5",
          "startTime": "1300",
          "endTime": "1400",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "S16-06118",
          "day": "Tuesday",
          "lessonType": "Tutorial",
          "size": 67
        },
        {
          "classNo": "4",
          "startTime": "1200",
          "endTime": "1300",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "S16-06118",
          "day": "Tuesday",
          "lessonType": "Tutorial",
          "size": 67
        },
        {
          "classNo": "6",
          "startTime": "1000",
          "endTime": "1100",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "S16-06118",
          "day": "Wednesday",
          "lessonType": "Tutorial",
          "size": 47
        }
      ],
      "examDate": "2018-11-29T01:00:00.000Z",
      "examDuration": 120
    },
    {
      "semester": 2,
      "timetable": [
        {
          "classNo": "3",
          "startTime": "0800",
          "endTime": "1000",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "LT34",
          "day": "Friday",
          "lessonType": "Lecture",
          "size": 200
        },
        {
          "classNo": "3",
          "startTime": "1400",
          "endTime": "1600",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "LT34",
          "day": "Wednesday",
          "lessonType": "Lecture",
          "size": 200
        },
        {
          "classNo": "8",
          "startTime": "1600",
          "endTime": "1700",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "S16-06118",
          "day": "Tuesday",
          "lessonType": "Tutorial",
          "size": 35
        },
        {
          "classNo": "11",
          "startTime": "1300",
          "endTime": "1400",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "S16-06118",
          "day": "Thursday",
          "lessonType": "Tutorial",
          "size": 35
        },
        {
          "classNo": "2",
          "startTime": "1400",
          "endTime": "1600",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "LT28",
          "day": "Friday",
          "lessonType": "Lecture",
          "size": 200
        },
        {
          "classNo": "3",
          "startTime": "1300",
          "endTime": "1400",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "S16-06118",
          "day": "Monday",
          "lessonType": "Tutorial",
          "size": 35
        },
        {
          "classNo": "15",
          "startTime": "1300",
          "endTime": "1400",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "S16-06118",
          "day": "Friday",
          "lessonType": "Tutorial",
          "size": 36
        },
        {
          "classNo": "13",
          "startTime": "1100",
          "endTime": "1200",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "S16-06118",
          "day": "Friday",
          "lessonType": "Tutorial",
          "size": 36
        },
        {
          "classNo": "14",
          "startTime": "1200",
          "endTime": "1300",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "S16-06118",
          "day": "Friday",
          "lessonType": "Tutorial",
          "size": 36
        },
        {
          "classNo": "16",
          "startTime": "1600",
          "endTime": "1700",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "S16-06118",
          "day": "Friday",
          "lessonType": "Tutorial",
          "size": 36
        },
        {
          "classNo": "17",
          "startTime": "1000",
          "endTime": "1100",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "S16-06118",
          "day": "Friday",
          "lessonType": "Tutorial",
          "size": 36
        },
        {
          "classNo": "5",
          "startTime": "1100",
          "endTime": "1200",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "S16-06118",
          "day": "Tuesday",
          "lessonType": "Tutorial",
          "size": 35
        },
        {
          "classNo": "10",
          "startTime": "1200",
          "endTime": "1300",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "S16-06118",
          "day": "Thursday",
          "lessonType": "Tutorial",
          "size": 35
        },
        {
          "classNo": "12",
          "startTime": "1600",
          "endTime": "1700",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "S16-06118",
          "day": "Thursday",
          "lessonType": "Tutorial",
          "size": 35
        },
        {
          "classNo": "2",
          "startTime": "1200",
          "endTime": "1300",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "S16-06118",
          "day": "Monday",
          "lessonType": "Tutorial",
          "size": 35
        },
        {
          "classNo": "9",
          "startTime": "1100",
          "endTime": "1200",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "S16-06118",
          "day": "Thursday",
          "lessonType": "Tutorial",
          "size": 35
        },
        {
          "classNo": "1",
          "startTime": "1400",
          "endTime": "1600",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "LT26",
          "day": "Tuesday",
          "lessonType": "Lecture",
          "size": 200
        },
        {
          "classNo": "1",
          "startTime": "1400",
          "endTime": "1600",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "LT26",
          "day": "Monday",
          "lessonType": "Lecture",
          "size": 200
        },
        {
          "classNo": "7",
          "startTime": "1300",
          "endTime": "1400",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "S16-06118",
          "day": "Tuesday",
          "lessonType": "Tutorial",
          "size": 35
        },
        {
          "classNo": "2",
          "startTime": "1400",
          "endTime": "1600",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "LT26",
          "day": "Thursday",
          "lessonType": "Lecture",
          "size": 200
        },
        {
          "classNo": "4",
          "startTime": "1600",
          "endTime": "1700",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "S16-06118",
          "day": "Monday",
          "lessonType": "Tutorial",
          "size": 35
        },
        {
          "classNo": "6",
          "startTime": "1200",
          "endTime": "1300",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "S16-06118",
          "day": "Tuesday",
          "lessonType": "Tutorial",
          "size": 35
        },
        {
          "classNo": "1",
          "startTime": "1100",
          "endTime": "1200",
          "weeks": [
            1,
            2,
            3,
            4,
            5,
            6,
            7,
            8,
            9,
            10,
            11,
            12,
            13
          ],
          "venue": "S16-06118",
          "day": "Monday",
          "lessonType": "Tutorial",
          "size": 35
        }
      ],
      "examDate": "2019-05-08T09:00:00.000Z",
      "examDuration": 120
    }
  ],
  "prereqTree": {
    "or": [
      "MA1102R",
      "MA1312",
      "MA1505",
      "MA1507",
      "MA1521"
    ]
  },
  "fulfillRequirements": [
    "DSC3223",
    "DSC3215",
    "FIN4111",
    "FNA4111",
    "MNO4312",
    "CS5228",
    "CS5233",
    "CS5239",
    "CS5240",
    "CS5246",
    "CS6244",
    "CS5340",
    "CS6211",
    "CS6283",
    "CS6285",
    "CS5332",
    "CS5346",
    "CS5338",
    "CS5422",
    "CS5446",
    "CS3244",
    "CS4222",
    "CS4243",
    "CS4248",
    "CS4246",
    "CS3236",
    "CS4226",
    "CS4257",
    "CS4261",
    "IS5152",
    "IS4240",
    "IS4242",
    "BT4015",
    "EE4211",
    "EE4204",
    "EE4131",
    "EE3731C",
    "EE4113",
    "EE4210",
    "IE2100",
    "IE2130",
    "IE3101",
    "IE4211",
    "MA4260",
    "MA3227",
    "MA4264",
    "MA4270",
    "MA3259",
    "MA3269",
    "ST2132",
    "ST2137",
    "ST3131",
    "ST3232",
    "ST3233",
    "ST3234",
    "ST3235",
    "ST3239",
    "ST3247",
    "ST3245",
    "ST2335",
    "ST3248",
    "SPH3101",
    "CS4268"
  ]
}
 */
