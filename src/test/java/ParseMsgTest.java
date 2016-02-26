import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * author: achugr
 * date: 30.01.16
 */
public class ParseMsgTest {

    @Test
    public void testPaidMsg(){
        Pattern p = Pattern.compile("/paid\\s*(\\d+)");
        Matcher m = p.matcher("/paid 100");

        assertThat(m.find(), is(equalTo(true)));
        assertThat(Integer.parseInt(m.group(1)), is(equalTo(100)));
    }
}
