create table PROBLEM
(
    ID            NUMBER(9) default "PROBLEM_TRACKER"."ISEQ$$_72907".nextval generated as identity
		constraint PROBLEMS_PK
			primary key,
    NAME          CHAR(128)           not null,
    URL           CHAR(512)           not null,
    PLATFORM_ID   NUMBER(3) default 0 not null
        constraint PROBLEMS_PLATFORM_ID_FK
            references PLATFORM,
    DIFFICULTY_ID NUMBER(2) default 0 not null
        constraint PROBLEM_DIFFICULTY_ID_FK
            references DIFFICULTY
                on delete cascade
)
/

