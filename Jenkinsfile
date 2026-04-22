pipeline {
    agent any

    environment {
        GRID_URL = "http://localhost:4444/wd/hub"
    }

    stages {

        stage('Start Selenium Grid') {
            steps {
                bat 'docker-compose up -d'
            }
        }

        stage('Wait for Grid') {
            steps {
                bat 'powershell Start-Sleep -Seconds 10'
            }
        }

        stage('Verify Grid') {
            steps {
                bat 'curl http://localhost:4444/status'
            }
        }

        stage('Run Tests') {
            steps {
                bat """
                docker run --rm ^
                  --network=selenium-grid ^
                  -e GRID_URL=%GRID_URL% ^
                  maven:3.9.6-eclipse-temurin-17 ^
                  sh -c "git clone https://github.com/SambathPK/automation-framework.git && cd automation-framework && mvn clean test"
                """
            }
        }

    }

//     post {
//         always {
//             bat 'docker-compose down'
//         }
//     }
  }