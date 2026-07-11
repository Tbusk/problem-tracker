create table "USER"
(
    ID            NUMBER(12) default "PROBLEM_TRACKER"."ISEQ$$_72928".nextval generated as identity
		constraint USER_PK
			primary key,
    EMAIL_ADDRESS CHAR(256)                not null
        constraint USER_PK_2
            unique,
    CREATED_ON    TIMESTAMP(6)             not null,
    PASSWORD_HASH CHAR(128)  default not null,
    ENABLED       BOOLEAN    default TRUE,
    LOCKED        BOOLEAN    default FALSE not null
)
/

