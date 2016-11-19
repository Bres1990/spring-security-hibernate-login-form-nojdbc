TRUNCATE TABLE Users;

INSERT INTO Users (username, password, role, first_name, last_name, address, account_no) values ('username', '$2a$11$Yub/pQnL96104LV1OnFL1OUC8fBx93lj7oEw189R7Z3uwLvDpslOy', 'ROLE_USER', null, null, null, null);
INSERT INTO Users (username, password, role, first_name, last_name, address, account_no) values ('ADMINISTRATOR', '$2a$11$M1/5BdNrEL.Dyesns9Iis.CYhYiKldRWlsTm6G1Nkn4RlKlE8Eq1i', 'ROLE_ADMIN', null, null, null, null);