INSERT INTO lector (name, degree, salary) VALUES
                                              ('John Doe', 'ASSISTANT', 50000),
                                              ('Jane Smith', 'ASSOCIATE_PROFESSOR', 70000),
                                              ('Emily Johnson', 'PROFESSOR', 90000),
                                              ('Michael Brown', 'ASSISTANT', 55000),
                                              ('Chris Davis', 'PROFESSOR', 95000);

INSERT INTO department (name, head_of_department_id) VALUES
                                                         ('Physics', 3),
                                                         ('Mathematics', 5),
                                                         ('Chemistry', 4);

INSERT INTO department_lector (department_id, lector_id) VALUES
                                                             (1, 1),
                                                             (1, 3),
                                                             (2, 2),
                                                             (2, 5),
                                                             (3, 4);
