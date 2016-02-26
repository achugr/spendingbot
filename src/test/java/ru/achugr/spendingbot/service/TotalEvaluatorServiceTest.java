package ru.achugr.spendingbot.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.achugr.spendingbot.Application;
import ru.achugr.spendingbot.domain.User;
import ru.achugr.spendingbot.dto.Total;
import ru.achugr.spendingbot.repository.MoneyTransferRepositoryImpl;
import ru.achugr.spendingbot.repository.SessionRepository;
import ru.achugr.spendingbot.testutil.FillDbHelper;
import ru.achugr.spendingbot.testutil.TestUtils;

import java.math.BigDecimal;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.OrderingComparison.comparesEqualTo;

/**
 * author: achugr
 * date: 05.02.16
 */

@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@SpringApplicationConfiguration(classes = Application.class)
public class TotalEvaluatorServiceTest {

    @Autowired
    private TotalEvaluatorService totalEvaluatorService;

    @Autowired
    FillDbHelper fillDbHelper;

    @Autowired
    private MoneyTransferRepositoryImpl moneyTransferRepository;

    @Autowired
    private SessionRepository sessionRepository;


    @Test
    public void testOneUserOneTransfer() {
        User user = fillDbHelper.createUser(1, "achugr");
        fillDbHelper.spendMoney(1L, user.getId(), BigDecimal.valueOf(10));

        Total total = totalEvaluatorService.getTotal(1L);

        assertThat(total.getTotalSum(), comparesEqualTo(BigDecimal.TEN));
    }

    @Test
    public void testOneUserSeveralTransfer() {
        User user = fillDbHelper.createUser(1, "achugr");

        fillDbHelper.spendMoney(1L, 1, BigDecimal.valueOf(10));
        fillDbHelper.spendMoney(1L, 1, BigDecimal.valueOf(99));
        fillDbHelper.spendMoney(1L, 1, BigDecimal.valueOf(100));

        Total total = totalEvaluatorService.getTotal(1L);

        assertThat(total.getTotalSum(), comparesEqualTo(BigDecimal.valueOf(209)));
    }


    @Test
    public void testSeveralUsers() {
        User user1 = fillDbHelper.createUser(1, "achugr");
        User user2 = fillDbHelper.createUser(2, "achugr");
        User user3 = fillDbHelper.createUser(3, "achugr");

        BigDecimal user1spend = BigDecimal.valueOf(10);
        BigDecimal user2spend = BigDecimal.valueOf(99);
        BigDecimal user3spend = BigDecimal.valueOf(6999);

        fillDbHelper.spendMoney(1L, user1.getId(), user1spend);
        fillDbHelper.spendMoney(1L, user2.getId(), user2spend);
        fillDbHelper.spendMoney(1L, user3.getId(), user3spend);

        Total total = totalEvaluatorService.getTotal(1L);
        Map<Integer, BigDecimal> spends = TestUtils.convert(total);

        assertThat(total.getTotalSum(), comparesEqualTo(user1spend.add(user2spend).add(user3spend)));
        assertThat(spends.get(user1.getId()), comparesEqualTo(BigDecimal.valueOf(-2359.33)));
        assertThat(spends.get(user2.getId()), comparesEqualTo(BigDecimal.valueOf(-2270.33)));
        assertThat(spends.get(user3.getId()), comparesEqualTo(BigDecimal.valueOf(4629.67)));

        assertThat(total.getTotalSum(), comparesEqualTo(user1spend.add(user2spend).add(user3spend)));
    }

    @Test
    public void testSeveralUsers2() {
        User user1 = fillDbHelper.createUser(1, "achugr");
        User user2 = fillDbHelper.createUser(2, "achugr");
        User user3 = fillDbHelper.createUser(3, "achugr");

        BigDecimal user1spend = BigDecimal.valueOf(10);
        BigDecimal user2spend = BigDecimal.valueOf(99);
        BigDecimal user3spend = BigDecimal.valueOf(6999);
        BigDecimal user3spend2 = BigDecimal.valueOf(1);

        fillDbHelper.spendMoney(1L, user1.getId(), user1spend);
        fillDbHelper.spendMoney(1L, user2.getId(), user2spend);
        fillDbHelper.spendMoney(1L, user3.getId(), user3spend);
        fillDbHelper.spendMoney(1L, user3.getId(), user3spend2);

        Total total = totalEvaluatorService.getTotal(1L);
        Map<Integer, BigDecimal> spends = TestUtils.convert(total);

        assertThat(total.getTotalSum(), comparesEqualTo(user1spend.add(user2spend).add(user3spend).add(user3spend2)));
        assertThat(spends.get(user1.getId()), comparesEqualTo(BigDecimal.valueOf(-2359.67)));
        assertThat(spends.get(user2.getId()), comparesEqualTo(BigDecimal.valueOf(-2270.67)));
        assertThat(spends.get(user3.getId()), comparesEqualTo(BigDecimal.valueOf(4630.33)));

    }

    @Test
    public void testTwoSessionsTwoUsers() {
        User user1 = fillDbHelper.createUser(1, "achugr");
        User user2 = fillDbHelper.createUser(2, "achugr");

        {
            BigDecimal user1spend = BigDecimal.valueOf(10);
            BigDecimal user2spend = BigDecimal.valueOf(99);
            BigDecimal user2spend2 = BigDecimal.valueOf(1);

            fillDbHelper.spendMoney(1L, user1.getId(), user1spend);
            fillDbHelper.spendMoney(1L, user2.getId(), user2spend);
            fillDbHelper.spendMoney(1L, user2.getId(), user2spend2);

            Total total = totalEvaluatorService.getTotal(1L);
            Map<Integer, BigDecimal> spends = TestUtils.convert(total);

            assertThat(total.getTotalSum(), comparesEqualTo(user1spend.add(user2spend).add(user2spend2)));
            assertThat(spends.get(user1.getId()), comparesEqualTo(BigDecimal.valueOf(-45)));
            assertThat(spends.get(user2.getId()), comparesEqualTo(BigDecimal.valueOf(45)));
        }

        sessionRepository.createNew(1L, "Second session");

        {
            BigDecimal user1spend = BigDecimal.valueOf(100);
            BigDecimal user2spend = BigDecimal.valueOf(999);
            BigDecimal user2spend2 = BigDecimal.valueOf(1);

            fillDbHelper.spendMoney(1L, user1.getId(), user1spend);
            fillDbHelper.spendMoney(1L, user2.getId(), user2spend);
            fillDbHelper.spendMoney(1L, user2.getId(), user2spend2);

            Total total = totalEvaluatorService.getTotal(1L);
            Map<Integer, BigDecimal> spends = TestUtils.convert(total);

            assertThat(total.getTotalSum(), comparesEqualTo(user1spend.add(user2spend).add(user2spend2)));
            assertThat(spends.get(user1.getId()), comparesEqualTo(BigDecimal.valueOf(-450)));
            assertThat(spends.get(user2.getId()), comparesEqualTo(BigDecimal.valueOf(450)));
        }

    }

}
