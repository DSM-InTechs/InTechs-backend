# InTechs-backend

[블로그] https://dev-gorany.tistory.com/281

### Shell script 만들었습니당

$ sh execute.sh 

__Shell script 코드__

> cd InTechs-backend

> sed -i '' 's/    secret: ${SECRET_KEY:}/    secret: ${SECRET_KEY:rand}/' ./src/main/resources/application.yml

> rm -rf ./src/test/

> chmod +x ./gradlew

> ./gradlew build

> java -jar ./build/libs/InTechs-backend-0.0.1-SNAPSHOT.jar

### Shell script X
1. cmd 접속 후 cd로 프로젝트 폴더가 위치한 곳으로 이동
2. ./gradlew build로 빌드한다
3. 2를 마치고 dir하면 build라는 디렉토리가 생성되어있다.
4. /build/libs 디렉토리 밑에 .jar파일을 실행한다
  -  __실행시 명령어 java -jar  InTechs-backend-0.0.1-SNAPSHOT.jar__

*만일 Test에서 에러가 난다면 InTechs-backend>src>test 폴더 삭제 후 진행하세용*
