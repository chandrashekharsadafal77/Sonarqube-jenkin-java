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
                SONARQUBE = 'Sonarqube' // Name of the SonarQube server configuration in Jenkins
            }
            steps {
                script {
                    withSonarQubeEnv(SONARQUBE) {
                        bat 'mvn sonar:sonar'
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
        success {
            script {
                def sonarQubeUrl = 'http://localhost:9000' // Replace with your SonarQube server URL
                def projectKey = 'java-project' // Replace with your SonarQube project key
                def projectUrl = "${sonarQubeUrl}/projects/overview?id=${projectKey}"
                echo "SonarQube Project URL: ${projectUrl}"

                // Email notification
                emailext (
                    subject: "Build Successful: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                    body: """
                        The build was successful. You can view the SonarQube report at the following link:
                        ${projectUrl}
                    """,
                    to: 'chandrashekharsadafal777@gmail.com'
                )
            }
        }

        always {
            cleanWs()
        }
    }
}
