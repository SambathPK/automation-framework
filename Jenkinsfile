pipeline {
    agent any

    environment {
        GRID_URL = "http://localhost:4444/wd/hub"
    }

    stages {

        stage('Cleanup Old Grid') {
            steps {
                bat '''
                docker-compose down || exit 0
                docker rm -f selenium-hub chrome firefox 2>nul || exit 0
                '''
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

                for /L %%i in (1,1,20) do (
                    curl -s http://localhost:4444/status > nul
                    if not errorlevel 1 (
                        echo Grid is READY
                        exit /b 0
                    )
                    timeout /t 5 > nul
                )

                echo Grid FAILED
                exit /b 1
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