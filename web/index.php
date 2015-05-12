<?php

//$mysql = new mysqli("localhost","admin","ntNbuGnAe7Zm","mysql");
$mysql = new mysqli("localhost","root");

// $db_selected=mysql_select_db('mysql',$mysql);

echo "Hello World!!!<br>";
echo "MySQL Server info:" .$mysql->host_info."<br>";

$createdb="create database if not exists mydb";
if($mysql->query($createdb) === TRUE)
{
    echo "<p>DB create successfully</p>";
} else {
    echo "<p>DB create error</p>";
}

//$db_selected=mysql_select_db('mydb',$mysql);

$createtbl="create table if not exists mydb.myuser (ID int(11) unsigned NOT NULL auto_increment PRIMARY KEY, Host varchar(32) not NULL, User varchar(16) not NULL)";
if($mysql->query($createtbl) === TRUE)
{
    echo "<p>Table create successfully</p>";
} else {
    echo "<p>Table create error<p>";
}
$randuser = "yzyan".rand(1,100);
$insertdata="insert into mydb.myuser (Host,User) values('localhost','".$randuser."')";
if($mysql->query($insertdata) === TRUE)
{
    echo "<p>Insert data successfully</p>";
} else {
    echo "<p>Insert data error</p>";
}

$querydata = "select Host, User from mydb.myuser";
$queryresult = $mysql->query($querydata);

echo '<table class="table table-striped table-bordered table-hover">';
echo "<tr><th>Host</th><th>User</th></tr>";
if($queryresult->num_rows > 0) {
    while($oneuser = $queryresult->fetch_assoc()) {
        echo "<tr><td>";
        echo "Host:".$oneuser["Host"];
        echo "</td><td>";
        echo "User:".$oneuser["User"];
        echo "</td></tr>";
    }
} else {
    echo "<p>0 results</p>";
}
echo "</table>";

$mysql->close();


echo "<p>Bye!</p>";

?>
