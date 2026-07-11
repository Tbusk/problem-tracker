create table PROGRAMMING_LANGUAGE
(
    ID   NUMBER(4) generated as identity
        constraint PROGRAMMING_LANGUAGE_PK
            primary key,
    NAME CHAR(128) not null
        constraint PROGRAMMING_LANGUAGE_PK_2
            unique
)
/

INSERT INTO PROBLEM_TRACKER.PROGRAMMING_LANGUAGE (ID, NAME) VALUES (1, 'Java                                                                                                                            ');
INSERT INTO PROBLEM_TRACKER.PROGRAMMING_LANGUAGE (ID, NAME) VALUES (2, 'JavaScript                                                                                                                      ');
INSERT INTO PROBLEM_TRACKER.PROGRAMMING_LANGUAGE (ID, NAME) VALUES (3, 'TypeScript                                                                                                                      ');
INSERT INTO PROBLEM_TRACKER.PROGRAMMING_LANGUAGE (ID, NAME) VALUES (4, 'Python                                                                                                                          ');
