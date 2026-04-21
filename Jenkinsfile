pipeline {
    agent any

    environment {
        GRID_URL = "http://host.docker.internal:4444/wd/hub"
        GRID_STATUS_URL = "http://localhost:4444/status"
    }

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    stages {

        stage('Cleanup Old Grid') {
            steps {
                bat '''
                echo Cleaning up old containers...

                docker-compose down --remove-orphans || exit 0
                docker container prune -f || exit 0
                '''
            }
        }

        stage('Start Selenium Grid') {
            steps {
                bat '''
                echo Starting Selenium Grid...

                docker-compose up -d
                '''
            }
        }

        stage('Wait for Grid Ready') {
            steps {
                bat '''
                powershell -Command ^
                for ($i=0; $i -lt 10; $i++) { ^
                    try { ^
                        Invoke-WebRequest http://localhost:4444/status ^
                        if ($?) { exit 0 } ^
                    } catch {} ^
                    Start-Sleep -Seconds 5 ^
                }
                '''
            }
        }

        stage('Run Tests (Maven + Docker)') {
            steps {
                bat '''
                echo Running tests inside Maven container...

                docker run --rm ^
                  -e GRID_URL=%GRID_URL% ^
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
            bat '''
            echo Cleaning up Selenium Grid...

            docker-compose down --remove-orphans || exit 0
            '''
        }

        success {
            echo "Pipeline completed successfully!"
        }

        failure {
            echo "Pipeline failed. Check logs above."
        }
    }
}