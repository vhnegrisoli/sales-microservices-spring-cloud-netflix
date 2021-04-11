INSERT INTO permission (id, description) VALUES (1, 'Manager');
INSERT INTO permission (id, description) VALUES (2, 'Customer');

INSERT INTO user_details (id, user_name, password) VALUES (1, 'vhnegrisoli', '$2a$10$xLvrB4YqV9YMg8bmHdzGJerZ.52LeBy9Xbq1iCYnMiQEuvnvkbzta');

INSERT INTO user_permission (fk_user, fk_permission) VALUES (1, 1);
INSERT INTO user_permission (fk_user, fk_permission) VALUES (1, 2);
