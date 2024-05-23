pipeline {
    parameters {
        booleanParam(defaultValue: true, description: 'Should a release branch be created?', name: 'RELEASE_BUILD')
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
                    echo "RELEASE_BUILD=${RELEASE_BUILD}" > spinnaker.properties
		        '''
            }
        }
    }

    post {
        always {
            step($class: 'ArtifactArchiver', artifacts: 'spinnaker.properties', allowEmptyArchive: false)
        }
    }
}