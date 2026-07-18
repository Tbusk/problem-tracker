# Oracle Database

This database is used to store the problem data.

The [schema](schema) directory contains all of the SQL scripts and backup files for the database.

In order to fully set it up yourself, you will need to create a user like I did (`PROBLEM_TRACKER`), grant it necessary
privileges, and then change to that schema.

After you do that, you can run the SQL scripts in the [schema](schema) directory to set up the database.

To connect to the database (if you are unfamiliar with Oracle DB), use the following properties:

- Host : `localhost`
- Service : `FREEPDB1`
- Port : `1521`
- Driver : `Thin`
- Authentication: `SYSDBA`
- User : `SYS`
- Password : your password
- URL : `jdbc:oracle:thin:@//localhost:1521/FREEPDB1`

One thing to note is your client may connect you by default with transaction mode of manual, meaning all transactions
need to be manually committed (rather than automatically as common in other databases).