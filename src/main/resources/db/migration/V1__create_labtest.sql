CREATE TABLE `course` (
  `id` int NOT NULL AUTO_INCREMENT,
  `course_date` date DEFAULT NULL,
  `course_usd_rub` decimal(6,2) DEFAULT NULL,
  `course_usd_kzt` decimal(6,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
  );

  CREATE TABLE `accounts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `bill` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
  );

  CREATE TABLE `limits` (
  `id` int NOT NULL AUTO_INCREMENT,
  `limit_sum` decimal(10,2) DEFAULT NULL,
  `limit_rem` decimal(10,2) DEFAULT NULL,
  `limit_datetime` timestamp NULL DEFAULT NULL,
  `limit_currency_shortname` varchar(3) DEFAULT NULL,
  `account_id` int DEFAULT NULL,
  `expense_category` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
  );

  CREATE TABLE `transactions` (
  `id` int NOT NULL AUTO_INCREMENT,
  `account_from` varchar(10) DEFAULT NULL,
  `account_to` varchar(10) DEFAULT NULL,
  `currency_shortname` varchar(3) DEFAULT NULL,
  `sum` decimal(10,2) DEFAULT NULL,
  `expense_category` varchar(100) DEFAULT NULL,
  `datetime` timestamp NULL DEFAULT NULL,
  `account_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
  );

  CREATE TABLE `flags` (
  `id` int NOT NULL AUTO_INCREMENT,
  `flag` varchar(5) DEFAULT NULL,
  `trans_id` int DEFAULT NULL,
  PRIMARY KEY (`id`)
  );

alter table limits
add CONSTRAINT fk_acc_id FOREIGN KEY (account_id) REFERENCES accounts (id);

alter table transactions
add CONSTRAINT fk_acc_tr_id FOREIGN KEY (account_id) REFERENCES accounts (id);

alter table flags
add CONSTRAINT fk_flg_tr_id FOREIGN KEY (trans_id) REFERENCES transactions (id);

INSERT INTO accounts (bill) VALUES ('0000000001');
INSERT INTO accounts (bill) VALUES ('0000000002');
INSERT INTO accounts (bill) VALUES ('0000000003');

INSERT INTO limits (limit_sum, limit_rem, limit_datetime,
limit_currency_shortname, account_id, expense_category)
VALUES (500.00, 500.00, '2024-03-21 08:10:00', 'USD', 1, 'service');

INSERT INTO transactions (account_from, account_to, currency_shortname, sum, expense_category, datetime, account_id)
VALUES ('0000000001', '0000000002', 'USD', 500.00, 'product', '2024-03-21 10:00:00', 1);

INSERT INTO transactions (account_from, account_to, currency_shortname, sum, expense_category, datetime, account_id)
VALUES ('0000000001', '0000000002', 'USD', 300.00, 'product', '2024-03-21 10:12:00', 1);

INSERT INTO transactions (account_from, account_to, currency_shortname, sum, expense_category, datetime, account_id)
VALUES ('0000000001', '0000000002', 'USD', 400.00, 'product', '2024-03-21 10:14:00', 1);

INSERT INTO transactions (account_from, account_to, currency_shortname, sum, expense_category, datetime, account_id)
VALUES ('0000000001', '0000000002', 'USD', 400.00, 'service', '2024-03-21 10:14:10', 1);

INSERT INTO flags (flag, trans_id) VALUES ('false', 1);
INSERT INTO flags (flag, trans_id) VALUES ('false', 2);
INSERT INTO flags (flag, trans_id) VALUES ('true', 3);
INSERT INTO flags (flag, trans_id) VALUES ('false', 4);