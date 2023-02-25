pipeline {
    agent any
    tools {
        maven 'M3'
    }
    stages {
        stage("Build Maven") {
            steps {
                sh 'mvn -B clean package'
            }
        }
        stage("Run Gatling") {
            steps {
                sh 'mvn gatling:test -DSTARTING_USERS=${STARTING_USERS} -DINCREMENT_RATE=${INCREMENT_RATE} -DINCREMENT_TIMES=${INCREMENT_TIMES} -DRAMP_DURATION=${RAMP_DURATION} -DLEVEL_DURATION=${LEVEL_DURATION} -DAPI_BASE_URL=${API_BASE_URL}'
            }
            post {
                always {
                    gatlingArchive()
                }
            }
        }
    }
}