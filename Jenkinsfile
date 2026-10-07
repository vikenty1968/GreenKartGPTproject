pipeline {
    agent any
    tools{
        maven 'Maven3'
    }

    stages {
        stage('Test') {
            steps {
                sh 'mvn test -DgridUrl=http://selenium-hub:4444 -Dsurefire.suiteXmlFiles=src/test/resources/regression.xml'
            }
        }
    }
    post{
         always {
                junit 'target/surefire-reports/TEST-*.xml'
                archiveArtifacts artifacts: 'reports/ExtentReport.html',
                                 allowEmptyArchive: true
                      archiveArtifacts artifacts: 'screenshots/**/*',
                                         allowEmptyArchive: true
            }
    }
}