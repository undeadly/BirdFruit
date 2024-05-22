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
                sh '''#!/bin/bash -xe
                       which java
                       java -version
		        '''
            }
        }
    }
}