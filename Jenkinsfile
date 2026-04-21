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

        stage('Start Selenium Grid') {
            steps {
                sh 'docker-compose up -d'
            }
        }

        stage('Wait for Grid Ready') {
            steps {
                sh '''
                echo "Waiting for Selenium Grid..."

                for i in {1..15}; do
                  curl -s http://localhost:4444/status | grep ready && echo "Grid is READY" && break
                  sleep 5
                done
                '''
            }
        }

        stage('Run Tests') {
            steps {
                sh """
                docker run --rm \
                  --network=selenium-grid \
                  -e GRID_URL=${GRID_URL} \
                  -v ${WORKSPACE}:/app \
                  -w /app \
                  maven:3.9.6-eclipse-temurin-17 \
                  mvn clean test
                """
            }
        }

        stage('Generate Reports') {
            steps {
                sh 'echo "Attach Allure / Extent reports here"'
            }
        }
    }

    post {
        always {
            stage('Cleanup') {
                sh 'docker-compose down'
            }
        }
    }
}