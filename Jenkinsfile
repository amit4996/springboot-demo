pipeline {
    agent any

    stages {
        stage('Initialize') {
            steps {
                echo 'Checking local tools...'
                sh 'git --version'
                // sh 'node --version' // Uncomment if you use Node.js
                // sh 'python --version' // Uncomment if you use Python
            }
        }
        stage('Build') {
            steps {
                echo 'Building your project...'
                // If you have a build command, put it here, e.g., sh 'npm install'
            }
        }
        stage('Test') {
            steps {
                echo 'Running tests...'
            }
        }
    }
}