create table PROBLEM_CATEGORY
(
    PROBLEM_ID  NUMBER(9) not null
        constraint PROBLEMS_CATEGORY_PROBLEMS_ID_FK
            references PROBLEM
                on delete cascade,
    CATEGORY_ID NUMBER(3) not null
        constraint PROBLEMS_CATEGORY_CATEGORY_ID_FK
            references CATEGORY
                on delete cascade,
    constraint PROBLEMS_CATEGORY_PK
        primary key (PROBLEM_ID, CATEGORY_ID)
)
/

