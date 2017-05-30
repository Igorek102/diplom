
import org.junit.Test;
import ru.igorek.core.api.DBApi;
import ru.igorek.core.entities.TestEntity;
import ru.igorek.core.utils.HibernateUtil;

/**
 *
 * @author Игорек
 */
public class TestHiber {
    private static DBApi dBApi = new DBApi();
    
    @Test
    public void testAddEntity(){
        TestEntity testEntity = new TestEntity();
        testEntity.setId(System.currentTimeMillis());
        dBApi.addEntity(testEntity);
    }
}
