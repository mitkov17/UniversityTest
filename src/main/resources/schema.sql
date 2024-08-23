CREATE TABLE lector (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255),
                        degree VARCHAR(255),
                        salary DOUBLE
);

CREATE TABLE department (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255),
                            head_of_department_id BIGINT,
                            FOREIGN KEY (head_of_department_id) REFERENCES lector(id)
);

CREATE TABLE department_lector (
                                   department_id BIGINT NOT NULL,
                                   lector_id BIGINT NOT NULL,
                                   FOREIGN KEY (department_id) REFERENCES department(id),
                                   FOREIGN KEY (lector_id) REFERENCES lector(id)
);
