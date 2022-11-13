CREATE TABLE currencies_as_of_the_current_date (
id serial,
val_curs_date date NOT NULL,
PRIMARY KEY (id)
);


CREATE TABLE currencies (
id serial,
valute_id text NOT NULL,
code text NOT NULL,
nominal int NOT NULL,
name text NOT NULL,
value_current_course numeric(1000,4) NOT NULL,
current_date_id int NOT NULL,
FOREIGN KEY (current_date_id) references currencies_as_of_the_current_date(id),
PRIMARY KEY (id));