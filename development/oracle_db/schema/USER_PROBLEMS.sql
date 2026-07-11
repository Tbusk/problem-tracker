create table USER_PROBLEMS
(
    ID                      NUMBER(18)   default "PROBLEM_TRACKER"."ISEQ$$_72934".nextval generated as identity
		constraint USER_PROBLEMS_PK
			primary key,
    USER_ID                 NUMBER(12)             not null
        constraint USER_PROBLEMS_USER_ID_FK
            references "USER",
    PROBLEM_ID              NUMBER(8)              not null
        constraint USER_PROBLEMS_PROBLEM_ID_FK
            references PROBLEM,
    SOLVED_ON               TIMESTAMP(6)           not null,
    MINUTES                 NUMBER(3, 2) default 0 not null,
    PROGRAMMING_LANGUAGE_ID NUMBER(4)    default 0 not null
        constraint USER_PROBLEMS_PROGRAMMING_LANGUAGE_ID_FK
            references PROGRAMMING_LANGUAGE
)
/

