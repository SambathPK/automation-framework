pipeline {
    agent any

    environment {
        GRID_URL = "http://localhost:4444/wd/hub"
        REPORT_PATH = "Output/Akku_2.0/Report/Extent/Akku-Tenant-Quality-Health-Report.html"
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
                bat 'powershell -Command "Start-Sleep -Seconds 20"'
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

        stage('Archive Reports') {
            steps {
                bat """
                echo Archiving reports...
                dir repo\\Output\\Akku_2.0\\Report\\Extent
                """
            }
        }
    }

    post {

        success {
            emailext (
                to: 'sambath351@gmail.com',
                subject: "✅ BUILD SUCCESS - Selenium Grid Tests",
                body: """
                <h3>Build Successful</h3>
                <p>All automation tests passed successfully.</p>
                <p>Attached is the execution report.</p>
                """,
                attachmentsPattern: 'repo/Output/Akku_2.0/Report/Extent/*.html',
                mimeType: 'text/html'
            )
        }

        failure {
            emailext (
                to: '@gmail.com',
                subject: "❌ BUILD FAILED - Selenium Grid Tests",
                body: """
                <h3>Build Failed</h3>
                <p>Check Jenkins console for details.</p>
                <p>Report attached for debugging.</p>
                """,
                attachmentsPattern: 'repo/Output/Akku_2.0/Report/Extent/*.html',
                mimeType: 'text/html'
            )
        }

        always {
            bat 'docker-compose down || exit 0'
        }
    }
}