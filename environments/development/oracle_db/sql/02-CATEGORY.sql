ALTER SESSION SET CONTAINER = "FREEPDB1";

CREATE SEQUENCE PROBLEM_TRACKER.CATEGORY_SEQ
    MINVALUE 1
    MAXVALUE 999
    START WITH 1
    INCREMENT BY 1
    CACHE 25;

CREATE TABLE PROBLEM_TRACKER.CATEGORY
(
    ID   NUMBER(3) DEFAULT PROBLEM_TRACKER.CATEGORY_SEQ.NEXTVAL
        CONSTRAINT CATEGORY_PK
            PRIMARY KEY,
    NAME VARCHAR2(128) NOT NULL
        CONSTRAINT CATEGORY_UK
            UNIQUE,
    CONSTRAINT CATEGORY_ID_MIN_VALUE CHECK (ID > 0),
    CONSTRAINT CATEGORY_NAME_MIN_LENGTH CHECK (LENGTH(NAME) >= 2)
);

INSERT INTO PROBLEM_TRACKER.CATEGORY (NAME)
VALUES ('Array'),
       ('String'),
       ('Hash Table'),
       ('Math'),
       ('Dynamic Programming'),
       ('Sorting'),
       ('Greedy'),
       ('Depth-First Search'),
       ('Binary Search'),
       ('Database'),
       ('Bit Manipulation'),
       ('Matrix'),
       ('Tree'),
       ('Prefix Sum'),
       ('Breadth-First Search'),
       ('Two Pointers'),
       ('Heap (Priority Queue)'),
       ('Simulation'),
       ('Counting'),
       ('Graph Theory'),
       ('Binary Tree'),
       ('Stack'),
       ('Sliding Window'),
       ('Enumeration'),
       ('Design'),
       ('Backtracking'),
       ('Number Theory'),
       ('Union-Find'),
       ('Linked List'),
       ('Segment Tree'),
       ('Ordered Set'),
       ('Monotonic Stack'),
       ('Divide and Conquer'),
       ('Combinatorics'),
       ('Trie'),
       ('Queue'),
       ('Bitmask'),
       ('Recursion'),
       ('Geometry'),
       ('Binary Indexed Tree'),
       ('Hash Function'),
       ('Memoization'),
       ('Binary Search Tree'),
       ('Shortest Path'),
       ('Topological Sort'),
       ('String Matching'),
       ('Rolling Hash'),
       ('Game Theory'),
       ('Monotonic Queue'),
       ('Interactive'),
       ('Data Stream'),
       ('Brainteaser'),
       ('Doubly-Linked List'),
       ('Merge Sort'),
       ('Randomized'),
       ('Counting Sort'),
       ('Iterator'),
       ('Concurrency'),
       ('Quickselect'),
       ('Suffix Array'),
       ('Sweep Line'),
       ('Probability and Statistics'),
       ('Minimum Spanning Tree'),
       ('Bucket Sort'),
       ('Shell'),
       ('Reservoir Sampling'),
       ('Eulerian Circuit'),
       ('Radix Sort'),
       ('Strongly Connected Component'),
       ('Rejection Sampling'),
       ('Biconnected Component');
