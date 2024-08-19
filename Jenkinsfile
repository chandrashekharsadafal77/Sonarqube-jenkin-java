pipeline {
    agent any

    environment {
        MAVEN_HOME = tool 'Maven 3.8.6' // Adjust to the exact name in Jenkins
        PATH = "${MAVEN_HOME}/bin:${env.PATH}"
        SONAR_SCANNER_HOME = tool 'Sonar' // Adjust to the exact name in Jenkins
    }

    stages {
        stage('Checkout') {
            steps {
                // Checkout your code from the Git repository
                git 'git@gitlab.com:baap2610337/java-project.git' // Adjust the repo URL as needed
            }
        }

        stage('Build') {
            steps {
                // Build the Maven project
                sh 'mvn clean package'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') { // Adjust 'SonarQube' to your SonarQube server configuration name
                    // Run the SonarQube analysis
                    sh "mvn sonar:sonar -Dsonar.projectKey=java-project"
                }
            }
        }

        stage('Quality Gate') {
            steps {
                // Wait for the SonarQube Quality Gate result
                timeout(time: 1, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }

    post {
        always {
            // Display the SonarQube dashboard link in the console output
            script {
                def sonarUrl = "http://localhost:9000/dashboard?id=java-project"
                echo "SonarQube analysis completed. Check the results at: ${sonarUrl}"
            }
        }
    }
}
