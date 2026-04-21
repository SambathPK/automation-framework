pipeline {
    agent any

    environment {
        GRID_URL = "http://host.docker.internal:4444/wd/hub"
        GRID_STATUS = "http://localhost:4444/status"
    }

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    stages {

        stage('Cleanup Workspace & Containers') {
            steps {
                bat '''
                echo Cleaning workspace and old containers...

                taskkill /F /IM java.exe || exit 0
                rmdir /s /q target || exit 0

                docker-compose down --remove-orphans -v || exit 0
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

        stage('Wait for Grid (Stable Check)') {
            steps {
                powershell '''
                Write-Host "Waiting for Selenium Grid..."

                $url = "http://localhost:4444/status"
                $maxRetries = 30
                $sleep = 5

                for ($i = 1; $i -le $maxRetries; $i++) {
                    try {
                        $response = Invoke-RestMethod -Uri $url -TimeoutSec 5

                        if ($response.value.ready -eq $true) {
                            Write-Host "Grid is READY"
                            exit 0
                        }
                    }
                    catch {
                        Write-Host "Attempt $i : Grid not ready..."
                    }

                    Start-Sleep -Seconds $sleep
                }

                Write-Host "Grid FAILED to start"
                exit 1
                '''
            }
        }

        stage('Run Tests (Isolated Maven Container)') {
            steps {
                bat '''
                echo Running tests...

                docker run --rm ^
                  -e GRID_URL=http://host.docker.internal:4444/wd/hub ^
                  -v %cd%:/source ^
                  -w /source ^
                  maven:3.9.6-eclipse-temurin-17 ^
                  bash -c "mvn clean test"
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
            echo "BUILD SUCCESS 🚀"
        }

        failure {
            echo "BUILD FAILED ❌ - Check logs"
        }
    }
}