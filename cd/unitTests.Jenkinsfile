pipeline {
    agent {
        node {
            label 'docker.ubuntu18.mx.4xlarge'
        }
    }
    options {
        timeout(time: 45, unit: 'MINUTES')
        buildDiscarder(logRotator(daysToKeepStr: '30'))
    }
    stages {
        stage('Build') {
            steps {
                sh '''#!/bin/bash -xe
                      ./gradlew build
		        '''
            }
        }
    }
}
