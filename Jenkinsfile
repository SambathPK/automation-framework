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

        stage('Wait for Grid') {
            steps {
                sh '''
                for i in {1..15}; do
                  curl -s http://localhost:4444/status | grep ready && break
                  echo "Waiting..."
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
    }

    post {
        always {
            sh 'docker-compose down'
        }
    }
}