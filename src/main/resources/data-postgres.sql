INSERT INTO rstf.tbl_users (first_name, last_name, role, password, login) VALUES ( 'Lupa', 'Lupov', 0, '$2a$10$vPekSUAjcyEaYoHuEmJz6upMLFTQCToFjAlvRSdlMS4L4hDPLAu3W', 'Lupa');
INSERT INTO rstf.tbl_users (first_name, last_name, role, password, login) VALUES ( 'Pupa', 'Pupov', 1, '$2a$10$vPekSUAjcyEaYoHuEmJz6upMLFTQCToFjAlvRSdlMS4L4hDPLAu3W', 'Pupa');
INSERT INTO rstf.tbl_users (first_name, last_name, role, password, login) VALUES ( 'Student', 'Studentov', 2, '$2a$10$vPekSUAjcyEaYoHuEmJz6upMLFTQCToFjAlvRSdlMS4L4hDPLAu3W', 'Student');
INSERT INTO rstf.tbl_users (first_name, last_name, role, password, login) VALUES ( 'Jun', 'Junov', 2, '$2a$10$vPekSUAjcyEaYoHuEmJz6upMLFTQCToFjAlvRSdlMS4L4hDPLAu3W', 'Jun');
INSERT INTO rstf.tbl_users (first_name, last_name, role, password, login) VALUES ( 'TestTeacher', 'Ticherov', 1, '$2a$10$vPekSUAjcyEaYoHuEmJz6upMLFTQCToFjAlvRSdlMS4L4hDPLAu3W', 'TST');
INSERT INTO rstf.tbl_users (first_name, last_name, role, password, login) VALUES ( 'TestStudent', 'TestStudentov', 2, '$2a$10$vPekSUAjcyEaYoHuEmJz6upMLFTQCToFjAlvRSdlMS4L4hDPLAu3W', 'TSTS');
INSERT INTO rstf.tbl_lessons (start_time, end_time, day_of_week, teacher) VALUES ( '2022-08-15 19:55:16.000000', '2022-08-18 19:55:22.000000', 2, 2);
INSERT INTO rstf.tbl_courses (start_time, end_date, name, description) VALUES ( '2022-08-02 19:58:33.000000', '2022-09-15 19:58:40.000000', 'Course1', 'Desc');
INSERT INTO rstf.tbl_course_lessons(course_id, lesson_id) VALUES (1, 1);
INSERT INTO rstf.tbl_course_students(course_id, user_id) VALUES (1,3);
INSERT INTO rstf.tbl_course_students(course_id, user_id) VALUES (1,4);
INSERT INTO rstf.tbl_course_teachers(course_id, user_id) VALUES (1,2);


--select * from   rstf.tbl_course_students