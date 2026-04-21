pipeline {
    agent any

    environment {
        GRID_URL = "http://localhost:4444/status"
    }

    stages {

        stage('Cleanup Old Grid') {
            steps {
                bat '''
                docker-compose down --remove-orphans || exit 0
                '''
            }
        }

        stage('Start Grid') {
            steps {
                bat 'docker-compose up -d'
            }
        }

        stage('Wait for Grid (Stable Check)') {
            steps {
                powershell '''
                $url = "http://localhost:4444/status"
                $maxRetries = 30
                $i = 0

                Write-Host "Waiting for Selenium Grid..."

                do {
                    try {
                        $response = Invoke-RestMethod -Uri $url -TimeoutSec 5

                        if ($response.value.ready -eq $true) {
                            Write-Host "Grid is READY"
                            exit 0
                        }

                    } catch {
                        Write-Host "Grid not ready yet..."
                    }

                    Start-Sleep -Seconds 5
                    $i++
                } while ($i -lt $maxRetries)

                Write-Host "Grid FAILED to start"
                exit 1
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
            bat 'docker-compose down --remove-orphans'
        }
    }
}