-- schema.sql
CREATE TABLE IF NOT EXISTS course (
    id VARCHAR(60) DEFAULT RANDOM_UUID() PRIMARY KEY,
    course_name VARCHAR NOT NULL
    );

CREATE TABLE IF NOT EXISTS account (
    username VARCHAR(60) PRIMARY KEY,
    password VARCHAR NOT NULL,
    token VARCHAR UNIQUE
    );

CREATE TABLE IF NOT EXISTS student (
    id VARCHAR(60) DEFAULT RANDOM_UUID() PRIMARY KEY,
    account VARCHAR(60) NOT NULL UNIQUE,
    cos VARCHAR(60),
    semester NUMBER,
    FOREIGN KEY (account) REFERENCES account(username),
    FOREIGN KEY (cos) REFERENCES course(id)
    );

CREATE TABLE IF NOT EXISTS subject (
    id VARCHAR(60) DEFAULT RANDOM_UUID() PRIMARY KEY,
    subject_name VARCHAR NOT NULL,
    prof VARCHAR
    );

CREATE TABLE IF NOT EXISTS learningGroup (
    id VARCHAR(60) DEFAULT RANDOM_UUID() PRIMARY KEY,
    group_name VARCHAR NOT NULL,
    owner VARCHAR(60) NOT NULL,
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    place VARCHAR NOT NULL,
    subject VARCHAR NOT NULL,
    max_size INT,
    FOREIGN KEY (owner) REFERENCES student(id),
    FOREIGN KEY (subject) REFERENCES subject(id)
    );

CREATE TABLE IF NOT EXISTS student_learningGroup (
    id VARCHAR(60) DEFAULT RANDOM_UUID() PRIMARY KEY,
    student_id VARCHAR(60),
    learning_group_id VARCHAR(60),
    FOREIGN KEY (student_id) REFERENCES student(id),
    FOREIGN KEY (learning_group_id) REFERENCES learningGroup(id)
    );

CREATE TABLE IF NOT EXISTS subjectCourse (
    id VARCHAR(60) DEFAULT RANDOM_UUID() PRIMARY KEY,
    subject_id VARCHAR(60),
    course_id VARCHAR(60),
    FOREIGN KEY (subject_id) REFERENCES subject(id),
    FOREIGN KEY (course_id) REFERENCES course(id)
    );