DROP TABLE IF EXISTS feedback_vote;
DROP TABLE IF EXISTS feedback_facet;
DROP TABLE IF EXISTS feedback;

CREATE TABLE feedback(
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	id_shop int NOT NULL REFERENCES shop(id) ON DELETE CASCADE,
	id_user int NOT NULL REFERENCES user(id),
	positive boolean NOT NULL,
	text varchar(1024),
	published datetime NOT NULL DEFAULT NOW()
);

create table feedback_facet(
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	id_feedback int NOT NULL REFERENCES feedback(id) ON DELETE CASCADE,
	name varchar(64) NOT NULL,
	score decimal(2,1) NOT NULL,
	unique key name(id_feedback, name)
);

create table feedback_vote(
	id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
	id_feedback int NOT NULL REFERENCES feedback(id) ON DELETE CASCADE,
	id_user int NOT NULL REFERENCES user(id),
	positive boolean NOT NULL,
	unique key vote(id_feedback, id_user)
)