pipeline {
    triggers {
        githubPush()
    }
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
        stage('Trigger Spinnaker') {
            steps {
                sh '''#!/bin/bash -xe
		        '''
            }
        }
    }
}