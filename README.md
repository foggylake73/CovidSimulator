# CovidSimulator
Science project implemented at Langley High School, from 2022 to 2023, finally uploaded onto GitHub

A Java-based simulation modeling the transmission of COVID-19 in Langley High School, using data from the CDC

All text and code is from 2022 to 2023
Citations and sources have been lost, as my high school has automatically deleted them



## The Data
Note: All the data that I use is from my school and the CDC.

My school averages 2,024 students per school year, so the number of students in my model is also 2,024. At my school, students have a total of 8 classes. Each school day consists of 4 periods that alternate between the even and odd classes. Therefore, each student will be randomly assigned 8 classrooms, and each day will simulate 4 blocks. The model will emulate the entire school year, which includes school days and breaks (ex: weekends, winter break, spring break), except for summer break. There are 292 days and 42 weeks in my model, reflecting the number of days in a school year. For the infection rate, I made an array of the 7-day cases per 100,000 for each week. I chose to use the 7-day cases per 100,000 because it is the weekly number of new COVID-19 cases per 100,000 people. For the death rate, I used the 7-day deaths per 100,000 because it is the number of weekly COVID-19 deaths from 100,000 people. My school district has a policy where, if you get COVID-19, you must stay at home for 10 days. Therefore, once someone gets infected in my program, they will remain at home for 10 days.

## The Files
CovidSimulator.java contains the main method of my program, which is what starts and runs my model. It loops through every school day and outputs the day, the number of daily cases, and the number of daily deaths into an output file.
Classroom.java is used to represent the classrooms in my school. It contains each period held in the classroom and the students in each block.
Person.java is the parent class for Student.java and Teacher.java. It contains methods and variables that both Student.java and Teacher.java share.
Student.java extends the Person class and represents the students.
Teacher.java extends Person.java and represents the teachers. Due to my project’s objective of tracking the number of COVID-19 cases and deaths among students, if a teacher gets infected with COVID-19, they won’t be infected for the next loop.
Campus.java implements the GUI. In my original project, I used IntelliJ IDEA by JetBrains to code my project, where I also created a GUI to pair with the project. However, when I transferred my code to VS Code, the GUI didn’t work, resulting in me scrapping the idea.
