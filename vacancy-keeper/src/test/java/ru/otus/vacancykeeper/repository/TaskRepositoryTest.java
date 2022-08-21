package ru.otus.vacancykeeper.repository;

import lombok.val;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.vacancykeeper.domain.SUser;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Data Jpa для работы с задачами должен")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class TaskRepositoryTest {

    private static final int EXPECTED_TASK_COUNT = 2;
    private static final int EXPECTED_QUERIES_COUNT = 1;
    private static final long USER_ID = 1;

    @Autowired
    private TaskRepository repository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("Возвращать ожидаемый список задач")
    @Test
    void shouldReturnExpectedTaskList() {
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().clear();
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        val tasks = repository.findAll();
        assertThat(tasks).isNotNull().hasSize(EXPECTED_TASK_COUNT);

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

    @DisplayName("Возвращать список задач пользователя")
    @Test
    void shouldReturnExpectedTasksByUser() {
        SUser user = em.find(SUser.class, USER_ID);
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().clear();
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        val tasks = repository.findTasksByUserOrderByTitle(user);
        assertThat(tasks).isNotNull().hasSize(EXPECTED_TASK_COUNT);

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }


    @DisplayName("Возвращать список задач пользователя по названию")
    @Test
    void shouldReturnExpectedTasksByUserAndTitle() {
        SUser user = em.find(SUser.class, USER_ID);
        SessionFactory sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().clear();
        sessionFactory.getStatistics().setStatisticsEnabled(true);

        val tasks = repository.findTasksByUserAndTitle(user, "spring");
        assertThat(tasks).isNotNull().hasSize(EXPECTED_TASK_COUNT);

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(EXPECTED_QUERIES_COUNT);
    }

}
