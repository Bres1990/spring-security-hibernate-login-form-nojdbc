TRUNCATE TABLE Users;

INSERT INTO Users (username, password, role) values ('username', '$2a$11$Yub/pQnL96104LV1OnFL1OUC8fBx93lj7oEw189R7Z3uwLvDpslOy', 'ROLE_USER');
INSERT INTO Users (username, password, role) values ('ADMINISTRATOR', '$2a$11$M1/5BdNrEL.Dyesns9Iis.CYhYiKldRWlsTm6G1Nkn4RlKlE8Eq1i', 'ROLE_ADMIN');