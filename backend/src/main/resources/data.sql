INSERT INTO course (ID, COURSE_NAME) VALUES ('MInf', 'Master Informatik');
INSERT INTO course (ID, COURSE_NAME) VALUES ('BInf', 'Bachelor Informatik');
INSERT INTO course (ID, COURSE_NAME) VALUES ('BSE','Bachelor Software Engineering');

INSERT INTO subject (ID, SUBJECT_NAME, PROF) VALUES ('PSE','PSE', 'Christian Becker');
INSERT INTO subject (ID, SUBJECT_NAME, PROF) VALUES ('SWS','SWS', 'Kuesters');
INSERT INTO subject (ID, SUBJECT_NAME, PROF) VALUES ('TheoInf3','Theo3', 'Funke');

INSERT INTO subjectCourse (SUBJECT_ID, COURSE_ID) VALUES ('PSE', 'BInf');
INSERT INTO subjectCourse (SUBJECT_ID, COURSE_ID) VALUES ('PSE', 'BSE');
INSERT INTO subjectCourse (SUBJECT_ID, COURSE_ID) VALUES ('SWS', 'MInf');
INSERT INTO subjectCourse (SUBJECT_ID, COURSE_ID) VALUES ('TheoInf3', 'BInf');
INSERT INTO subjectCourse (SUBJECT_ID, COURSE_ID) VALUES ('TheoInf3', 'BSE');

INSERT INTO account (USERNAME, PASSWORD) VALUES ('Seb', '1234' );
INSERT INTO account (USERNAME, PASSWORD) VALUES ('Anne', '234567' );
INSERT INTO account (USERNAME, PASSWORD) VALUES ('Yannick', 'sfjjk' );


INSERT INTO student (ID, ACCOUNT, COS, SEMESTER) VALUES ('stud1', 'Seb',  'BSE', '5');
INSERT INTO student (ID, ACCOUNT, COS, SEMESTER) VALUES ('stud2', 'Anne' ,  'BInf', '7');
INSERT INTO student (ID, ACCOUNT, COS, SEMESTER) VALUES ('stud3', 'Yannick',  'MInf', '2');

INSERT INTO learningGroup (ID, GROUP_NAME, OWNER, START_TIME, END_TIME, PLACE, SUBJECT, MAX_SIZE) VALUES ('group1', 'Lerngruppe1PSE', 'stud3', '2023-12-12 12:00:00' , '2023-12-12 15:00:00', 'Infobib', 'PSE', '4');

