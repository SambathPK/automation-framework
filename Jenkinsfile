pipeline {
    agent any

    stages {

        stage('Start Grid') {
            steps {
                bat 'docker-compose up -d'
            }
        }

        stage('Wait Grid') {
            steps {
                bat 'powershell Start-Sleep -Seconds 10'
            }
        }

        stage('Run Tests (Isolated Docker)') {
            steps {
                bat """
                docker run --rm ^
                  --network=selenium-grid ^
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