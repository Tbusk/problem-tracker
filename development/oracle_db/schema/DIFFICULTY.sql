create table DIFFICULTY
(
    ID   NUMBER(2) generated as identity
        constraint DIFFICULTY_PK
            primary key,
    NAME CHAR(128) not null
)
/

INSERT INTO PROBLEM_TRACKER.DIFFICULTY (ID, NAME) VALUES (4, 'Easy                                                                                                                            ');
INSERT INTO PROBLEM_TRACKER.DIFFICULTY (ID, NAME) VALUES (5, 'Medium                                                                                                                          ');
INSERT INTO PROBLEM_TRACKER.DIFFICULTY (ID, NAME) VALUES (6, 'Hard                                                                                                                            ');
