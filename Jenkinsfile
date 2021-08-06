pipeline {
  agent any
  stages {
    stage('Source') {
      steps {
        git(url: 'https://github.com/sogongjeon/sogongjeon_server', branch: 'master')
      }
    }

    stage('Build') {
      steps {
        sh '''./4_log.sh
'''
      }
    }

  }
}