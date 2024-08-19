pipeline {
    agent any

    environment {
        // Define environment variables for SonarQube
        SONARQUBE_SERVER = 'Sonarqube' // Name of SonarQube installation configured in Jenkins
        SONARQUBE_URL = 'http://localhost:9000' // URL of your SonarQube server
        SONARQUBE_PROJECT_KEY = 'java-project' // Project key in SonarQube
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout the code from Git
                checkout scm
            }
        }

        stage('Build') {
            steps {
                // Build the project
                bat 'mvn clean install'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                script {
                    // Run SonarQube analysis
                    withSonarQubeEnv(SONARQUBE_SERVER) {
                        bat 'mvn sonar:sonar'
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                script {
                    // Wait for SonarQube analysis result
                    def qg = waitForQualityGate()
                    if (qg.status != 'OK') {
                        error "Quality gate check failed: ${qg.status}"
                    }
                }
            }
        }
    }

    post {
        success {
            // Notify with a link to SonarQube project upon successful build
            echo "Build successful. Check SonarQube project for details."
            script {
                def projectLink = "${SONARQUBE_URL}/dashboard?id=${SONARQUBE_PROJECT_KEY}"
                currentBuild.description = "SonarQube Project: <a href='${projectLink}'>${SONARQUBE_PROJECT_KEY}</a>"
            }
        }

        always {
            // Clean up workspace
            cleanWs()
        }
    }
}
