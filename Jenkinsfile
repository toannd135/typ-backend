pipeline {
    agent any 

    environment {
        DOCKER_USERNAME = "toannd135"
        DOCKER_HUB_CREDENTIALS = 'dockerhub-credential'
        IMAGE_NAME = "${DOCKER_USERNAME}/backend-app:v7"
    }
    
    stages {
        stage('Agent Information') {
            steps {
                sh "whoami"
                sh "pwd"
                sh "uname -a"
            }
        }

        stage('Clone Repository') {
            steps {
                echo "Cloning source code..."
                checkout scm

                echo "clone completed"
                sh "ls -la"
            }
        }
        stage('Build Docker Image') {
            steps {
                script {
                    sh """
                        docker build -t ${IMAGE_NAME} .
                    """
                    echo " Docker image built successfully!"
                }
            }
        }
        stage('Run Test') {
            steps {
                script {
                    echo "testing.........."
                    sh """
                        docker run --rm ${IMAGE_NAME} mvn test
                    """
                }
            }
        }
    }
}