cd InTechs-backend
sed -i '' 's/ secret: ${SECRET_KEY:}/ secret: ${SECRET_KEY:rand}/' ./src/main/resources/application.yml
rm -rf ./src/test/
chmod +x ./gradlew
./gradlew build
java -jar ./build/libs/InTechs-backend-0.0.1-SNAPSHOT.jar
