pipeline {
    agent any
    
    stages {
        stage('Fetch Code') {
            steps {
                git 'git@gitlab.com:baap2610337/java-project.git'
            }
        }
        stage('Code Analysis') {
            environment {
                scannerHome = tool 'Sonar'  // Make sure 'Sonar' is configured in Jenkins' global tools
            }
            steps {
                script {
                    withSonarQubeEnv('Sonar') {  // 'Sonar' is the name of your SonarQube server configuration in Jenkins
                        sh "${scannerHome}/bin/sonar-scanner \
                            -Dsonar.projectKey=Java-project \
                            -Dsonar.projectName=Java-project \
                            -Dsonar.projectVersion=1.0 \
                            -Dsonar.sources=src/main/java" // Adjust the project path if needed
                    }
                }
            }
        }
    }
}
