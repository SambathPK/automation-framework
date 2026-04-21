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

        stage('Wait for Grid (Stable Health Check)') {
            steps {
                powershell '''
                Write-Host "Waiting for Selenium Grid to be ready..."

                $maxRetries = 30
                $delay = 5
                $url = "http://localhost:4444/status"

                for ($i = 1; $i -le $maxRetries; $i++) {

                    try {
                        $response = Invoke-RestMethod -Uri $url -TimeoutSec 5

                        if ($response.value.ready -eq $true) {
                            Write-Host "Grid is READY"
                            exit 0
                        }

                    } catch {
                        Write-Host "Attempt $i : Grid not ready yet..."
                    }

                    Start-Sleep -Seconds $delay
                }

                Write-Host "Grid FAILED to start within timeout"
                exit 1
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