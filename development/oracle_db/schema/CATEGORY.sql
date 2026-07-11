create table CATEGORY
(
    ID   NUMBER(3) default "PROBLEM_TRACKER"."ISEQ$$_72918".nextval generated as identity
		constraint CATEGORY_PK
			primary key,
    NAME CHAR(128) default not null
        constraint CATEGORY_UK
            unique
)
/

INSERT INTO PROBLEM_TRACKER.CATEGORY (ID, NAME) VALUES (32, 'String                                                                                                                          ');
INSERT INTO PROBLEM_TRACKER.CATEGORY (ID, NAME) VALUES (33, 'Set                                                                                                                             ');
INSERT INTO PROBLEM_TRACKER.CATEGORY (ID, NAME) VALUES (34, 'HashMap                                                                                                                         ');
INSERT INTO PROBLEM_TRACKER.CATEGORY (ID, NAME) VALUES (35, 'Queue                                                                                                                           ');
INSERT INTO PROBLEM_TRACKER.CATEGORY (ID, NAME) VALUES (36, 'Stack                                                                                                                           ');
INSERT INTO PROBLEM_TRACKER.CATEGORY (ID, NAME) VALUES (37, 'Binary Search                                                                                                                   ');
INSERT INTO PROBLEM_TRACKER.CATEGORY (ID, NAME) VALUES (38, 'Array                                                                                                                           ');
INSERT INTO PROBLEM_TRACKER.CATEGORY (ID, NAME) VALUES (39, 'Sorting                                                                                                                         ');
INSERT INTO PROBLEM_TRACKER.CATEGORY (ID, NAME) VALUES (40, 'Linked List                                                                                                                     ');
