# springbootapp

H2ConsoleAutoConfiguration 에서 h2-console을 셋팅하며, h2datasource 만 @Primary 어노테이션을 줘야 함.

Hsqldb 를 서버모드로 실행
java -cp ./hsqldb-2.5.2.jar org.hsqldb.Server -database.0 mydb -dbname.0 xdb

관리앱 수행
java -cp ./hsqldb-2.5.2.jar org.hsqldb.util.DatabaseManagerSwing

접속 url
jdbc:hsqldb:hsql://localhost:9001/xdb

JPA 는 persistennt context 에서 관리되다가 transaction 완료 시점에 flush 된다 (db에 반영)
