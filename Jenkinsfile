pipeline {
    agent { label 'java' }
    stages{
        stage('Download project from GitHub') {
            steps{
                echo "=====${STAGE_NAME}====="
                cleanWs()
                sh 'git clone https://github.com/mvolkov82/feedcreator.git'
                echo "Download completed"
            }
        }
        stage('Build project'){
            steps{
                echo "=====${STAGE_NAME}====="
                dir('feedcreator'){
                    sh 'docker run --rm --name my-maven-project -v maven-repo:/root/.m2 -v "$(pwd)":/usr/src/mymaven -w /usr/src/mymaven maven:3.3-jdk-8 mvn clean install'
                }
            }
        }
        stage('Deploy artifact and Dockerfile to S3'){
            steps{
                echo "=====${STAGE_NAME}====="
                dir('feedcreator'){
                    sh 'docker run --rm -e AWS_ACCESS_KEY_ID=${S3_KEY_ID} -e AWS_SECRET_ACCESS_KEY=${S3_KEY_PASSWORD} -v $(pwd):/aws amazon/aws-cli s3 cp Dockerfile s3://malvolkov02'
                }
                dir('feedcreator/target'){
                    sh 'docker run --rm -e AWS_ACCESS_KEY_ID=${S3_KEY_ID} -e AWS_SECRET_ACCESS_KEY=${S3_KEY_PASSWORD} -v $(pwd):/aws amazon/aws-cli s3 cp okeandra.jar s3://malvolkov02'
                }
            }
        }
        stage('PROD. Download artifact and Dockerfile from S3'){
            agent { label 'prod' }
            steps{
                echo "=====${STAGE_NAME}====="
                dir('/opt/java_project'){
                    sh 'docker run --rm -e AWS_ACCESS_KEY_ID=${S3_KEY_ID} -e AWS_SECRET_ACCESS_KEY=${S3_KEY_PASSWORD} -v /opt/java_project:/aws amazon/aws-cli s3 cp s3://malvolkov02/Dockerfile .'
                    sh 'docker run --rm -e AWS_ACCESS_KEY_ID=${S3_KEY_ID} -e AWS_SECRET_ACCESS_KEY=${S3_KEY_PASSWORD} -v /opt/java_project:/aws amazon/aws-cli s3 cp s3://malvolkov02/okeandra.jar .'
                }
            }
        }
        stage('PROD. Creating image and running container'){
            agent { label 'prod' }
            steps{
                echo "=====${STAGE_NAME}====="
                dir('/opt/java_project'){
                    sh 'docker stop $(docker ps -a -q)'
                    sh 'docker build -t okeandra_app .'
                    sh 'docker run -d -p 80:8080 --env-file ./env.list --restart unless-stopped okeandra_app'
                }
            }
        }
    }
}