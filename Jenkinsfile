pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                git 'https://github.com/SambathPK/automation-framework.git'
            }
        }

        stage('Build + Test in Docker') {
            steps {
                sh '''
                docker run --rm \
                --network=selenium-grid-setup_grid \
                -e GRID_URL=$GRID_URL \
                -v $PWD:/app \
                -w /app \
                maven:3.9.6-eclipse-temurin-17 \
                mvn clean test
                '''
            }
        }
    }
}