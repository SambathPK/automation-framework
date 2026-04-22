pipeline {
    agent any

    environment {
        GRID_URL = "http://localhost:4444/wd/hub"
    }

    stages {

        stage('Start Grid') {
            steps {
                bat 'docker-compose down || exit 0'
                bat 'docker-compose up -d'
            }
        }

        stage('Wait for Grid') {
            steps {
                bat 'powershell -Command "Start-Sleep -Seconds 15"'
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
                git clone https://github.com/SambathPK/automation-framework.git repo || exit 0
                cd repo
                mvn clean test -DGRID_URL=%GRID_URL%
                """
            }
        }

    }

    post {
        always {
            bat 'docker-compose down || exit 0'
        }
    }
}