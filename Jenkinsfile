pipeline {
    agent any

    environment {
        GRID_URL = "http://selenium-hub:4444/wd/hub"
    }

    stages {

        stage('Build') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }

        stage('Run Tests in Docker') {
            steps {
                sh '''
                docker run --rm \
                --network=selenium-grid-setup_grid \
                -e GRID_URL=$GRID_URL \
                -v $PWD:/app \
                -w /app \
                maven:3.9.6-eclipse-temurin-17 \
                mvn test
                '''
            }
        }
    }
}