@Library("lookout-common") _

pipeline {
    agent {
        node {
            label 'docker.ubuntu18.mx.4xlarge'
        }
    }
    tools {
        jdk "java-17-openjdk-amd64"
    }
    options {
        timeout(time: 45, unit: 'MINUTES')
        buildDiscarder(logRotator(daysToKeepStr: '30'))
    }
    stages {
        stage('Build') {
            steps {
                sh ''' #!/bin/bash -xe
                       # Fix permissions for current folder so the docker user can access it
                       chmod -R o+rw .

                       # Docker auth
                       export ACCOUNT=022103483154
                       $(aws ecr get-login --no-include-email --region us-west-2 --registry-ids ${ACCOUNT})

                       # sha hash can be found in the latest build here
                       # https://ci.corp.lookout.com/job/android-platform-build-container
                       # docker image with Java 11
                       dockerHash=ef90fb8cb669256e7f89a0d847fcdc90900de4b3136ad2c2d7f23567a4b12f81
                       docker run \\
                           -e "BUILD_NUMBER=${BUILD_NUMBER}" \\
                           --rm -v $(pwd):/home/jenkins/application \\
                           022103483154.dkr.ecr.us-west-2.amazonaws.com/android-platform:latest@sha256:$dockerHash \\
                           sh -c "./gradlew build"'''
            }
        }
    }
}
