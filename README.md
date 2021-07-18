# springbootapp

H2ConsoleAutoConfiguration 에서 h2-console을 셋팅하며, h2datasource 만 @Primary 어노테이션을 줘야 함.

Hsqldb 를 서버모드로 실행
java -cp ./hsqldb-2.5.2.jar org.hsqldb.Server -database.0 mydb -dbname.0 xdb

관리앱 수행
java -cp ./hsqldb-2.5.2.jar org.hsqldb.util.DatabaseManagerSwing

접속 url
jdbc:hsqldb:hsql://localhost:9001/xdb

--------------------
JPA 는 persistennt context 에서 관리되다가 transaction 완료 시점에 flush 된다 (db에 반영) 그러므로 save 메소드를 이용하면 entity 가 insert 되거나 udpate 된다. (persistent context 에 반영되고 결국 db에 반영)

-------------
Oracle 은 jpa 사용시 아래와 같이 sequence 사용할것.
@Id 
@GeneratedValue(generator="ServiceIdSeq", strategy=GenerationType.SEQUENCE) 
@SequenceGenerator(name="ServiceIdSeq", sequenceName="SERVICE_ID_SEQ")
private long id;

CREATE SEQUENCE SERVICE_ID_SEQ INCREMENT BY 1;
------------------------


