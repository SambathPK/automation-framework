pipeline {
    agent any

    environment {
        GRID_URL = "http://localhost:4444/wd/hub"
    }

    stages {

        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Start Grid') {
            steps {
                bat 'docker-compose up -d'
            }
        }

        stage('Wait for Grid') {
            steps {
                bat '''
                echo Waiting for Selenium Grid...

                for /L %%i in (1,1,15) do (
                    curl -s http://localhost:4444/status | findstr "ready"
                    if not errorlevel 1 goto ready
                    timeout /t 5
                )

                :ready
                echo Grid is READY
                '''
            }
        }

        stage('Run Tests') {
            steps {
                bat '''
                docker run --rm ^
                  -e GRID_URL=http://host.docker.internal:4444/wd/hub ^
                  -v %cd%:/app ^
                  -w /app ^
                  maven:3.9.6-eclipse-temurin-17 ^
                  mvn clean test
                '''
            }
        }
    }

    post {
        always {
            bat 'docker-compose down'
        }
    }
}