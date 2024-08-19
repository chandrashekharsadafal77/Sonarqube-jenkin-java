pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                script {
                    bat 'mvn clean install'
                }
            }
        }

        stage('SonarQube Analysis') {
            environment {
                SONARQUBE = 'SonarQube' // Ensure this name matches the configuration in Jenkins
            }
            steps {
                script {
                    withSonarQubeEnv(SONARQUBE) {
                        bat 'mvn sonar:sonar -Dsonar.projectKey=java-project -Dsonar.projectName=Java-project -Dsonar.projectVersion=1.0 -Dsonar.sources=src/main/java'
                    }
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
        always {
            cleanWs()
        }
    }
}
