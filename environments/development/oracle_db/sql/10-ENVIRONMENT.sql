ALTER SESSION SET CONTAINER = "FREEPDB1";

CREATE SEQUENCE PROBLEM_TRACKER.ENVIRONMENT_SEQ
    MINVALUE 1
    MAXVALUE 99
    START WITH 1
    INCREMENT BY 1
    CACHE 5;

CREATE TABLE PROBLEM_TRACKER.ENVIRONMENT (
    ID NUMBER(2,0) DEFAULT PROBLEM_TRACKER.ENVIRONMENT_SEQ.NEXTVAL NOT NULL PRIMARY KEY,
    NAME VARCHAR2(128) NOT NULL UNIQUE,
    CONSTRAINT ENVIRONMENT_NAME_MIN_LENGTH CHECK (LENGTH(NAME) >= 2),
    CONSTRAINT ENVIRONMENT_ID_MIN_VALUE CHECK (ID > 0)
);

INSERT INTO PROBLEM_TRACKER.ENVIRONMENT (NAME) VALUES ('Pen and Paper'),
                                                      ('Whiteboard'),
                                                      ('Basic IDE'),
                                                      ('Full IDE'),
                                                      ('AI Assisted'),
                                                      ('Online Editor'),
                                                      ('Terminal');