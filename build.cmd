cd rw-spring-boot-starter-parent
CALL mvn clean install

cd ../rw-spring-boot-starter
CALL mvn clean verify

cd ../rw-starter-test
CALL mvn clean verify
