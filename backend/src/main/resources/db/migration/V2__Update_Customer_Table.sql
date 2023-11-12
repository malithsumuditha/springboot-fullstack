ALTER TABLE student
    ADD COLUMN email VARCHAR(255) NOT NULL,
    ADD CONSTRAINT UK_customer_email UNIQUE (email);
