package ru.skillbox.diplom.group33.social.service.config.postgres;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Profile;
import ru.yandex.qatools.embed.postgresql.EmbeddedPostgres;
import ru.yandex.qatools.embed.postgresql.PostgresExecutable;
import ru.yandex.qatools.embed.postgresql.PostgresProcess;
import ru.yandex.qatools.embed.postgresql.PostgresStarter;
import ru.yandex.qatools.embed.postgresql.config.AbstractPostgresConfig;
import ru.yandex.qatools.embed.postgresql.config.PostgresConfig;
import ru.yandex.qatools.embed.postgresql.distribution.Version;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Log4j2
@Configuration
@Profile("test-db")
public class EmbeddedPostgresConfig {

    @Bean(destroyMethod = "stop")
    public PostgresProcess postgresProcess() throws IOException {
        log.info("Starting embedded Postgres");

        String tempDir = System.getProperty("java.io.tmpdir");
        String dataDir = tempDir + "/test_db";
        String binariesDir = System.getProperty("java.io.tmpdir") + "/postgres_binaries";

        PostgresConfig postgresConfig = new PostgresConfig(
                Version.V11_1,
                new AbstractPostgresConfig.Net("localhost", 5432),
                new AbstractPostgresConfig.Storage("test_db", dataDir),
                new AbstractPostgresConfig.Timeout(60_000),
                new AbstractPostgresConfig.Credentials("postgres", "postgres")
        );
        postgresConfig.getAdditionalInitDbParams().addAll(List.of("--nosync", "--locale=en_US.UTF-8"));
        PostgresStarter<PostgresExecutable, PostgresProcess> runtime =
                PostgresStarter.getInstance(EmbeddedPostgres.cachedRuntimeConfig(Paths.get(binariesDir)));
        PostgresExecutable exec = runtime.prepare(postgresConfig);
        return exec.start();
    }

    @Bean
    @DependsOn("postgresProcess")
    DataSource dataSource(PostgresProcess postgresProcess) {
        PostgresConfig postgresConfig = postgresProcess.getConfig();
        HikariConfig config = new HikariConfig();
        config.setUsername(postgresConfig.credentials().username());
        config.setPassword(postgresConfig.credentials().password());
        config.setJdbcUrl("jdbc:postgresql://localhost:" + postgresConfig.net().port() + "/" + postgresConfig.storage().dbName());

        return new HikariDataSource(config);
    }
}
