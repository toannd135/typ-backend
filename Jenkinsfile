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
                sh "whoami; pwd; uname -a"
            }
        }

        stage('Clone Repository') {
            steps {
                echo "Cloning source code..."
                checkout scm 
                echo "Clone completed"
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${IMAGE_NAME} ."
                echo "Docker image built successfully!"
            }
        }

        stage('Run Test') {
            steps {
                echo "Testing.........."
                // Luôn dùng --rm để dọn dẹp container sau khi test
                sh "docker run --rm ${IMAGE_NAME} mvn test"
            }
        }
    }
}