pipeline {
    agent any

    environment {
        SONARQUBE_URL = 'http://localhost:9000/dashboard?id=java-project' // Replace with your SonarQube server and project key
    }

    stages {
        stage('Checkout SCM') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                bat 'mvn clean install'
            }
        }
        stage('SonarQube Analysis') {
            environment {
                scannerHome = tool 'SonarQubeScanner' // Use your SonarQube scanner tool name
            }
            steps {
                withSonarQubeEnv('Sonarqube') { // Use the correct SonarQube installation name
                    bat "${scannerHome}/bin/sonar-scanner"
                }
            }
        }
        stage('Quality Gate') {
            steps {
                script {
                    def qg = waitForQualityGate()
                    if (qg.status != 'OK') {
                        error "Pipeline aborted due to quality gate failure: ${qg.status}"
                    }
                }
            }
        }
    }

    post {
        success {
            echo "Build successful!"
            echo "View SonarQube results at: ${SONARQUBE_URL}"
        }
        failure {
            echo "Build failed!"
        }
        always {
            cleanWs()
        }
    }
}
