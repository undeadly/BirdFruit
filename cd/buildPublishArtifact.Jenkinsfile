@Library("lookout-common") _

pipeline {
    parameters {
        string(name: 'VERSION_TAG', defaultValue: 'main', description: '''The tag to apply.''')
    }
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
        //Should checkout from the tag if this is a release build.
        stage('Checkout From Tag') {
            when {
                expression {
                    return env.VERSION_TAG != 'main'
                }
            }
            steps {
                git(credentialsId: 'cd/cloudbees/staging/git-credentials', url: 'git@source.corp.lookout.com:cory-roy/BirdFruit.git', branch: ${VERSION_TAG})
            }
        }
        stage('Build Artifact') {
            steps {
                sh ''' #!/bin/bash -xe
                       # Fix permissions for current folder so the docker user can access it
                       chmod -R o+rw .

                       # Docker auth
                       export ACCOUNT=022103483154
                       $(aws ecr get-login --no-include-email --region us-west-2 --registry-ids ${ACCOUNT})

                       # sha hash can be found in the latest build here
                       # https://ci.corp.lookout.com/job/android-platform-build-container
                       # docker image with Java 17
                       dockerHash=ef90fb8cb669256e7f89a0d847fcdc90900de4b3136ad2c2d7f23567a4b12f81

                       docker run \\
                           -e "BUILD_NUMBER=${BUILD_NUMBER}" \\
                           --rm -v "$(pwd):/home/jenkins/application" \\
                           022103483154.dkr.ecr.us-west-2.amazonaws.com/android-platform:latest@sha256:$dockerHash \\
                           sh -c "./gradlew assembleDebug"'''
            }
        }

        stage('Archive Artifact') {
            steps {
                rtServer (
                    id: 'lookout-artifactory',
                    url: 'http://artifactory.prod.lkt.is/artifactory'
                )
                rtUpload (
                    serverId: 'lookout-artifactory',
                    spec: '''{
                          "files": [
                            {
                              "pattern": "app/build/outputs/apk/debug/*.apk",
                              "target": "lookout-apk-local/com/demo/birdfruit/"
                            }
                         ]
                    }'''
                )
            }
        }
    }

    post {
        always {
            step($class: 'ArtifactArchiver', artifacts: 'app/build/outputs/apk/debug/*.apk', allowEmptyArchive: true)
        }
    }
}