CREATE TABLE convects (
id serial,
convect_from text NOT NULL,
convect_to text NOT NULL,
value text NOT NULL,
result text NOT NULL,
course_from text NOT NULL,
course_to text NOT NULL,
val_curs_date date NOT NULL,
PRIMARY KEY (id)
);