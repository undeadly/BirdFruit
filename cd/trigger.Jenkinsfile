pipeline {
    parameters {
        booleanParam(name: 'RELEASE_BUILD', defaultValue: false, description: 'Should a release branch be created?')
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
                cleanWs(notFailBuild: true)
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