import groovy.sql.Sql

/**
 * Created by jaehun on 2015-08-10.
 */

def mysql_db = [url : "jdbc:mysql://localhost:3306/fm_schema?useUnicode=true&characterEncoding=UTF-8",
                user: 'fmdev', password: 'admin', driver: 'com.mysql.jdbc.Driver']
def mysql = Sql.newInstance(mysql_db.url, mysql_db.user, mysql_db.password, mysql_db.driver)

def postgresql_db = [url : "jdbc:postgresql://localhost:5432/fm_schema",
                     user: 'fmdev', password: 'admin', driver: 'org.postgresql.Driver']
def postgresql = Sql.newInstance(postgresql_db.url, postgresql_db.user, postgresql_db.password, postgresql_db.driver)
println "DB connection ready"

//mysql.eachRow("SELECT sprite_img_path FROM character_resource") { row -> println row.sprite_img_path }
def cr_rows = mysql.rows("SELECT * FROM notices")

/*
def insertCounts = postgresql.withBatch { stmt ->
    cr_rows.each { row ->
        stmt.addBatch("INSERT INTO notices VALUES($row.notice_id, $row.title, $row.date)")
    }
}

println "Insert Count: " + insertCounts
*/

cr_rows.each { row ->
    postgresql.executeInsert("INSERT INTO notices VALUES($row.notice_id, $row.title, $row.date)")
}
