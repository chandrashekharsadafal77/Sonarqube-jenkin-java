pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Checkout code from GitLab
                git url: 'git@gitlab.com:baap2610337/java-project.git', branch: 'main', credentialsId: 'gitlab-ssh-key'
            }
        }

        stage('Build') {
            steps {
                // Clean and build the Maven project using Windows batch commands
                script {
                    bat 'mvn clean install'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                // Perform SonarQube analysis
                script {
                    withSonarQubeEnv('Sonarqube') {
                        bat 'mvn sonar:sonar'
                    }
                }
            }
        }

        stage('Quality Gate') {
            steps {
                // Wait for SonarQube analysis to complete and check quality gate status
                script {
                    timeout(time: 1, unit: 'HOURS') {
                        waitForQualityGate()
                    }
                }
            }
        }
    }

    post {
        always {
            // Clean up workspace
            cleanWs()
        }
        success {
            echo 'Build and analysis completed successfully.'
        }
        failure {
            echo 'Build or analysis failed.'
        }
    }
}
