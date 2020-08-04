BEGIN TRANSACTION;



CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE TYPE role AS ENUM ('Technical Consultant', 'Project Manager', 'Director', 'Chief');
CREATE TYPE unit AS ENUM ('Digital Experience Group', 'Adobe', 'IBM NBU', 'API Management');

DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS field;
DROP TABLE IF EXISTS skill;

CREATE TABLE address (
        id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
        street VARCHAR(50) NOT NULL,
        suite VARCHAR(20),
        city VARCHAR(25) NOT NULL,
        region_code VARCHAR(3) NOT NULL,
        postal_code VARCHAR(5) NOT NULL,
        country_code VARCHAR(2) NOT NULL
        
);

CREATE TABLE employee (
        employee_id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
        address_id uuid NOT NULL,
        firstName VARCHAR(32) NOT NULL,
        lastName VARCHAR(32) NOT NULL,
        contactEmail VARCHAR(32),
        companyEmail VARCHAR(32) NOT NULL UNIQUE,
        birthDate DATE NOT NULL,
        hiredDate DATE NOT NULL,
        emp_role role NOT NULL,
        businessUnit  unit,
        skill_id uuid,
        assignedTo VARCHAR(32),
        
        CONSTRAINT fk_employee_address FOREIGN KEY (address_id) REFERENCES address,
        CONSTRAINT fk_employee_skill FOREIGN KEY (skill_id) REFERENCES skill  --was not able to reference skill_id to skills table as an array or as an array of objects
);

CREATE TABLE field (
        id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
        name VARCHAR(20) NOT NULL,
        type VARCHAR(32) NOT NULL
);

CREATE TABLE skill (
        id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
        field_id uuid NOT NULL,
        experience_in_month INT default 0 NOT NULL,
        summary TEXT,
        
        CONSTRAINT fk_skill_field FOREIGN KEY (field_id) REFERENCES field
);

INSERT INTO field (name, type) VALUES ('JAVA', 'Softeware Development');
INSERT INTO field (name, type) VALUES ('C#', 'Software Development');
INSERT INTO field (name, type) VALUES ('Java Script', 'Web Development');
INSERT INTO field (name, type) VALUES ('JAVA', 'Micro Servicse');

INSERT INTO skill (field_id, experience_in_month, summary) VALUES ('2305754f-1cff-4b0b-98b7-71463572f1a4', 6, 'worked in spring boot app development');
INSERT INTO skill (field_id, experience_in_month, summary) VALUES ('25bf534c-694a-44c2-bf54-68065b7ba56b', 12, 'Currently working on responsive wep page development');
INSERT INTO skill (field_id, experience_in_month, summary) VALUES ('25bf534c-694a-44c2-bf54-68065b7ba56b', 20, 'worked with multi-threading environment');
INSERT INTO skill (field_id, experience_in_month, summary) VALUES ('a88e9a36-6432-478f-9ff0-790c1a01d986', 60, 'worked in api development');      

INSERT INTO employee(address_id, firstname, lastname, contactemail, companyemail, birthdate, hireddate, emp_role, businessunit, skill_id, assignedto)
VALUES('a03fea22-f02e-48d4-91bf-d2639938a6e9','James', 'Wright', 'j@gmail.com', 'jw@mycompany.org', '1933-02-02', '1933-02-02', 'Project Manager', 'Adobe', '12720c2c-a6a8-42ab-bfe2-1a7ea3f7350a', 'ali'); 
INSERT INTO employee(address_id, firstname, lastname, contactemail, companyemail, birthdate, hireddate, emp_role, businessunit, skill_id, assignedto)
VALUES('e2332fe9-1432-4dcf-9c06-042260271294','John', 'Smith', 'jSmith@myMail.com', 'jSmith@mycompany.org', '1953-02-02', '1993-02-02', 'Director', 'API Management', 'cee01bd3-55da-4558-bddc-f268b325ef06', 'Jane Smith'); 


INSERT INTO address (street, suite, city, region_code, postal_code, country_code) VALUES ('abc street', 'a19', 'toledo', 'OH', 45666, 'US');
INSERT INTO address (street, suite, city, region_code, postal_code, country_code) VALUES ('17th street', 'suite 2', 'Detroit', 'MI', 11111, 'US');

select * from address;
select * from employee;
select * from field;
select * from skill;



COMMIT TRANSACTION;