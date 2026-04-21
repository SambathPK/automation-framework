pipeline {
    agent any

    environment {
        GRID_URL = "http://localhost:4444/wd/hub"
    }

    stages {

        stage('Start Grid') {
            steps {
                bat 'docker-compose up -d'
            }
        }

        stage('Wait Grid') {
            steps {
                bat 'timeout /t 10'
            }
        }

        stage('Run Tests (Clean Isolation)') {
            steps {
                bat """
                docker run --rm ^
                  --network=selenium-grid ^
                  -e GRID_URL=${GRID_URL} ^
                  maven:3.9.6-eclipse-temurin-17 ^
                  sh -c "git clone https://github.com/SambathPK/automation-framework.git && cd automation-framework && mvn clean test"
                """
            }
        }

        stage('Cleanup') {
            steps {
                bat 'docker-compose down'
            }
        }
    }
}