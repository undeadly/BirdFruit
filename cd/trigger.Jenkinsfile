pipeline {
    parameters {
        booleanParam(defaultValue: false, description: 'Should a release branch be created?', name: 'CREATE_RELEASE_BRANCH')
    }
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
                    echo "RELEASE_BUILD=${CREATE_RELEASE_BRANCH}" > spinnaker.properties
		        '''
            }
        }
    }
}