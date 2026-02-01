pipeline {
    agent any

    stages {
        stage('Initialize') {
            steps {
                echo 'Checking Environment...'
                sh 'java -version'
                sh 'git --version'
            }
        }
        stage('Build') {
            steps {
                echo 'Building Spring Boot Application...'
                // Using the wrapper to compile and package into a JAR
                sh './mvnw clean package -DskipTests'
            }
        }
        stage('Deploy to Nexus') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'nexus-creds',
                                                  passwordVariable: 'NEXUS_PWD',
                                                  usernameVariable: 'NEXUS_USR')]) {

                    echo "Deploying via Maven with inline credentials..."

                    // We inject the credentials directly into the URL string.
                    // This bypasses the need for any <server> blocks or -Dauth properties.
                    sh "./mvnw deploy -DskipTests -DaltDeploymentRepository=nexus-creds::default::http://${NEXUS_USR}:${NEXUS_PWD}@localhost:8081/repository/my-releases/"
                }
            }
        }
        stage('Test') {
            steps {
                echo 'Running Unit Tests...'
                sh './mvnw test'
            }
        }
        stage('SonarQube Analysis') {
            steps {
                // This block injects your Sonar server URL and credentials
                withSonarQubeEnv('SonarServer') {
                // We use 'verify' to ensure tests run and coverage is captured
                sh './mvnw sonar:sonar'
                }
            }
        }
        stage("Quality Gate") {
            steps {
                // Prevents the pipeline from hanging forever if the server is slow
                timeout(time: 1, unit: 'HOURS') {
                    // Jenkins waits here for SonarQube to send a 'Success' webhook
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }

    post {
        always{
            junit '**/target/surefire-reports/*.xml'
        }
    }
}