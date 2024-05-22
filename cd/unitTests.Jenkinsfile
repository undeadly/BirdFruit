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
        stage('Checkout Scm') {
            steps {
                git(credentialsId: '5d03110a-c16c-4a45-94db-27c32bc483b2',
                    url: 'git@source.corp.lookout.com:cory-roy/BirdFruit.git')
            }
        }
        stage('Build') {
            steps {
                sh '''#!/bin/bash -xe
                      ./gradlew build    
		   '''
            }
        }
    }
}
