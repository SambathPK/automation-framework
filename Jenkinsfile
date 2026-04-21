pipeline {
    agent any

    environment {
        GRID_URL = "http://selenium-hub:4444/wd/hub"
    }

    stages {

        stage('Debug Workspace') {
            steps {
                sh 'pwd'
                sh 'ls -la'
            }
        }

        stage('Build + Test in Docker') {
            steps {
                sh """
                docker run --rm \
                --network=selenium-grid \
                -e GRID_URL=${GRID_URL} \
                -v ${WORKSPACE}:/app \
                -w /app \
                maven:3.9.6-eclipse-temurin-17 \
                mvn clean test
                """
            }
        }
    }
}