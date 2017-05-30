
import org.junit.Test;
import ru.igorek.core.utils.HibernateUtil;

/**
 *
 * @author Игорек
 */
public class TestHiber {
    @Test
    public void tst(){
        HibernateUtil.getSessionFactory();
    }
}
