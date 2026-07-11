create table PLATFORM
(
    ID   NUMBER(3) generated as identity
        constraint PLATFORM_PK
            primary key,
    NAME CHAR(128) not null
        constraint PLATFORM_PK_2
            unique
)
/

INSERT INTO PROBLEM_TRACKER.PLATFORM (ID, NAME) VALUES (21, 'Leetcode                                                                                                                        ');
INSERT INTO PROBLEM_TRACKER.PLATFORM (ID, NAME) VALUES (22, 'HackerRank                                                                                                                      ');
INSERT INTO PROBLEM_TRACKER.PLATFORM (ID, NAME) VALUES (23, 'Algo Monster                                                                                                                    ');
