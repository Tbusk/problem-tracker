# Oracle Database

This database is used to store the problem, user, and user activity data.

The [schema](sql) directory contains all of the SQL scripts and backup files for the database.

The database will load the scripts automatically.

Two accounts will be created:

- `PROBLEM_TRACKER` to manage the problem tracker schema
- `PROBLEM_TRACKER_APP` to run the app with, with reduced permissions

To connect to the root account:

- Host : `localhost`
- Connection Type: `Service Name`
- Service : `FREEPDB1`
- Port : `1521`
- Driver : `Thin`
- Authentication: `SYSDBA`
- User : `SYS`
- Password : your password from the docker-compose.yaml file environment variable `ORACLE_PWD` in [docker-compose.yaml](../docker-compose.yaml)
- URL : `jdbc:oracle:thin:@//localhost:1521/FREEPDB1`

To connect to and manage the problem tracker database:

- Host : `localhost`
- Connection Type: `Service Name`
- Service : `FREEPDB1`
- Port : `1521`
- Driver : `Thin`
- Authentication: `User & password`
- User : `PROBLEM_TRACKER`
- Password : password found in [01-CRATE_MAIN_USER.sql](sql/01-CREATE_MAIN_USER.sql)
- URL : `jdbc:oracle:thin:@//localhost:1521/FREEPDB1`

One thing to note is your client may connect you by default with transaction mode of manual, meaning all transactions
need to be manually committed (rather than automatically as common in other databases).