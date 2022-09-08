package info.novatec.quarkus.utils

import io.quarkus.test.junit.QuarkusTestProfile

/**
 * These [QuarkusTestProfile]s are only needed as for demo purposes the project contains
 * both: End2End tests that use an H2 and those that use a Testcontainers instance.
 * Therefore, different configurations need to be provided in the `application.yml`.
 */
class H2TestProfiles : QuarkusTestProfile {
    override fun getConfigProfile() = "h2-test"
}

class PostgresTestcontainerTestProfiles : QuarkusTestProfile {
    override fun getConfigProfile() = "postgresql-testcontainers-test"
}