INSERT INTO Users (id, username, password) values (1, 'username', 'password');
INSERT INTO Users (id, username, password) values (2, 'ADMINISTRATOR', 'administration');
INSERT INTO Users (id, username, password) values (3, 'Bresthrandir', 'bresthrandir');

INSERT INTO UserRoles (id, uid, name) values (1, 1, 'ROLE_USER');
INSERT INTO UserRoles (id, uid, name) values (2, 2, 'ROLE_ADMIN');
INSERT INTO UserRoles (id, uid, name) values (3, 3, 'ROLE_USER');
