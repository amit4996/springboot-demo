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
                // This block securely fetches the credentials from the Jenkins store
                withCredentials([usernamePassword(credentialsId: 'nexus-creds',
                                                  passwordVariable: 'NEXUS_PASSWORD',
                                                  usernameVariable: 'NEXUS_USERNAME')]) {

                    echo "Uploading Artifact to Nexus..."

                    // We pass the credentials directly into the Maven command
                    sh "./mvnw deploy -DskipTests " +
                       "-DaltDeploymentRepository=nexus-releases::default::${env.NEXUS_URL} " +
                       "-Dmaven.wagon.http.auth.username=${NEXUS_USERNAME} " +
                       "-Dmaven.wagon.http.auth.password=${NEXUS_PASSWORD}"
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