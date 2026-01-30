pipeline {
    agent any 

    environment {
        DOCKER_USERNAME = "toannd135"
        DOCKER_HUB_CREDENTIDALS = 'dockerhub-credential'
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
                encho "Cloning source code..."
                check scm

                echo "clone completed"
                sh "ls -la"
            }
        }
        stage('Build Docker Imgae') {
            steps {
                script {
                    sh """
                        docker build -t ${IMAGE_NAME}
                    """
                    echo " Docker image built successfully!"
                }
            }
        }
    }
}