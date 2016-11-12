web: npm install
web: npm install -g gulp-cli
web: docker-compose -f src/main/docker/mysql.yml up -d
web: ./gradlew -Pprod clean bootRepackage
