pipeline {
    agent any

    environment {
        GRID_URL = "http://selenium-hub:4444/wd/hub"
        HOST_WORKSPACE = "/var/lib/docker/volumes/practisee2eflow_jenkins_home/_data/workspace/selenium-grid-pipeline"
    }

    stages {

        stage('Verify Docker Mount') {
            steps {
                sh """
                docker run --rm \
                  -v ${HOST_WORKSPACE}:/app \
                  maven:3.9.6-eclipse-temurin-17 \
                  sh -c "ls -la /app"
                """
            }
        }

        stage('Build + Test in Docker') {
            steps {
                sh """
                docker run --rm \
                  --network=selenium-grid \
                  -e GRID_URL=${GRID_URL} \
                  -v ${HOST_WORKSPACE}:/app \
                  -w /app \
                  maven:3.9.6-eclipse-temurin-17 \
                  mvn clean test
                """
            }
        }
    }
}