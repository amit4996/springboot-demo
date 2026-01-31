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
        stage('Test') {
            steps {
                echo 'Running Unit Tests...'
                sh './mvnw test'
            }
        }
    }
}