# user-management
How to run?
1. git clone https://github.com/mrtong/user-management.git
2. make sure you have maven installed

`mvn clean install`

`java -jar usermanagement-0.0.1-SNAPSHOT.jar`

In postman, run this for get query: the `username/password` is user/password
<img src="https://github.com/mrtong/user-management/blob/master/screenshots/get_query.png" width="70%" height="60%"/>

run this for update: the `username/password` is admin/password
<img src="https://github.com/mrtong/user-management/blob/master/screenshots/update200.png" width="70%" height="60%"/>

This is the screen dump for request body
<img src="https://github.com/mrtong/user-management/blob/master/screenshots/update200_1.png" width="70%" height="60%"/>

if you pass username as user then http status 403(forbidden) will be raised as shown below:
<img src="https://github.com/mrtong/user-management/blob/master/screenshots/update403.png" width="70%" height="60%"/>


